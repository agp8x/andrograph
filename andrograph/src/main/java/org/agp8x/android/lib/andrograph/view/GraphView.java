package org.agp8x.android.lib.andrograph.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import org.agp8x.android.lib.andrograph.model.Coordinate;
import org.agp8x.android.lib.andrograph.model.GraphViewController;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/* * @author  clemensk
 *
 * 30.11.16.
 */
public class GraphView<V, E extends DefaultEdge> extends View {
    private GraphViewController<V, E> controller;
    private Dragging dragging;
    private int contentWidth;
    private int contentHeight;
    private boolean insertionMode = true;

    public void setInsertionMode(boolean insertionMode) {
        this.insertionMode = insertionMode;
        System.out.println(insertionMode);
    }

    public boolean isInsertionMode() {
        return insertionMode;
    }

    public GraphView(Context context) {
        super(context);
        init(null, 0);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void setController(GraphViewController<V, E> controller) {
        this.controller = controller;
        invalidate();
    }

    private void init(AttributeSet attrs, int defStyle) {
        dragging = new Dragging();
        setOnTouchListener(new GraphOnTouchListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (controller == null) {
            return;
        }

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        contentWidth = getWidth() - paddingLeft - paddingRight;
        contentHeight = getHeight() - paddingTop - paddingBottom;
        Coordinate c;
        Pair<Float, Float> xy;
        Graph<V, E> g = controller.getGraph();
        Paint vertexPaint;
        int radius;
        for (V v : g.vertexSet()) {
            if (v.equals(dragging.object)) {
                xy = dragging.xy;
                vertexPaint = controller.getSelectedPaint(v);
            } else {
                c = controller.getPosition(v);
                xy = coordinate2view(c);
                vertexPaint = controller.getVertexPaint(v);
            }
            radius = controller.getRadius(v);
            canvas.drawCircle(xy.first, xy.second, radius, vertexPaint);
            for (E edge : g.edgeSet()) {
                Pair<Float, Float> xy1 = vertex2view(g.getEdgeSource(edge));
                Pair<Float, Float> xy2 = vertex2view(g.getEdgeTarget(edge));
                Paint p = controller.getEdgePaint(edge);
                canvas.drawLine(xy1.first, xy1.second, xy2.first, xy2.second, p);
            }
        }
    }

    protected Pair<Float, Float> vertex2view(V vertex) {
        if (vertex.equals(dragging.object)) {
            return dragging.xy;
        }
        return coordinate2view(controller.getPosition(vertex));
    }

    protected Pair<Float, Float> coordinate2view(Coordinate coordinate) {
        return new Pair<>((float) (coordinate.getX() * contentWidth), (float) (coordinate.getY() * contentHeight));
    }

    protected Pair<Float, Float> event2pair(MotionEvent event) {
        return new Pair<>(event.getX(), event.getY());
    }

    protected Coordinate pair2coordinate(Pair<Float, Float> xy) {
        return new Coordinate(xy.first / contentWidth, xy.second / contentHeight);
    }

    protected Coordinate event2coordinate(MotionEvent event) {
        return new Coordinate(event.getX() / contentWidth, event.getY() / contentHeight);
    }

    private class Dragging {
        V object;
        Coordinate old;
        Pair<Float, Float> xy;
    }


    private class GraphOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean update = false;
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    update = handleStart(motionEvent);
                    break;
                case MotionEvent.ACTION_MOVE:
                    update = handleMove(motionEvent);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    update = handleStop(motionEvent);
                    break;
            }
            if (update) {
                invalidate();
            }
            return true;
        }

        private boolean handleStart(MotionEvent motionEvent) {
            boolean update = false;
            Coordinate action = event2coordinate(motionEvent);
            if (dragging.object == null) {
                V obj = controller.getSelected(action);
                if (obj != null) {
                    update = startDragging(obj);
                } else if (insertionMode) {
                    update = insertNewVertex(motionEvent);
                }
            } else if (!insertionMode) {
                V obj = controller.getSelected(action);
                if (obj != null) {
                    update = insertEdge(obj);
                }
            }
            return update;
        }

        private boolean handleMove(MotionEvent motionEvent) {
            boolean update = false;
            if (insertionMode) {
                update = updateDraggedVertex(motionEvent);
            }
            return update;
        }

        private boolean handleStop(MotionEvent motionEvent) {
            boolean update = false;
            if (insertionMode) {
                update = stopDragging(motionEvent);
            }
            return update;
        }

        private boolean stopDragging(MotionEvent motionEvent) {
            boolean update = false;
            if (dragging.object != null) {
                dragging.xy = event2pair(motionEvent);
                controller.update(dragging.old, pair2coordinate(dragging.xy));
                dragging.object = null;
                update = true;
            }
            return update;
        }

        private boolean updateDraggedVertex(MotionEvent motionEvent) {
            boolean update = false;
            if (dragging.object != null) {
                dragging.xy = event2pair(motionEvent);
                update = true;
            }
            return update;
        }

        private boolean insertEdge(V obj) {
            boolean update = controller.addOrRemoveEdge(dragging.object, obj);
            dragging.object = null;
            return update;
        }

        private boolean startDragging(V obj) {
            dragging.object = obj;
            dragging.old = controller.getPosition(obj);
            dragging.xy = coordinate2view(dragging.old);
            return true;
        }

        private boolean insertNewVertex(MotionEvent motionEvent) {
            return controller.addVertex(event2coordinate(motionEvent));
        }
    }

}

