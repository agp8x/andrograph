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
    private VertexInfo vertexStyle;
    private EdgeInfo edgeStyle;
    private DeletionZone deletionZone;

    public void setInsertionMode(boolean insertionMode) {
        this.insertionMode = insertionMode;
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
        //Android studio tells me variable creation/allocation during onDraw is bad
        dragging = new Dragging();
        vertexStyle = new VertexInfo();
        edgeStyle = new EdgeInfo();

        setOnTouchListener(new GraphOnTouchListener());

        //TODO: setup DeletionZone from attrs
        if (true) {
            deletionZone = null;
        } else {
            deletionZone = new DeletionZone();
            deletionZone.buttomRight = coordinate2view(new Coordinate(0, 0));
            deletionZone.topLeft = deletionZone.buttomRight;

        }
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
        Graph<V, E> g = controller.getGraph();
        for (V v : g.vertexSet()) {
            drawVertex(canvas, v);
        }
        for (E edge : g.edgeSet()) {
            drawEdge(canvas, edge, g.getEdgeSource(edge), g.getEdgeTarget(edge));
        }
    }

    private void drawVertex(Canvas canvas, V v) {
        //gather style info
        if (v.equals(dragging.object)) {
            vertexStyle.xy = dragging.xy;
            vertexStyle.paint = controller.getSelectedPaint(v);
        } else {
            vertexStyle.xy = coordinate2view(controller.getPosition(v));
            vertexStyle.paint = controller.getVertexPaint(v);
        }
        vertexStyle.xyLabel = coordinate2view(controller.getLabelOffset(v));
        vertexStyle.label = controller.getLabel(v);
        //draw
        canvas.drawCircle(
                vertexStyle.xy.first,
                vertexStyle.xy.second,
                controller.getRadius(v),
                vertexStyle.paint);
        if (vertexStyle.label != null) {
            canvas.drawText(
                    vertexStyle.label,
                    vertexStyle.xy.first + vertexStyle.xyLabel.first,
                    vertexStyle.xy.second + vertexStyle.xyLabel.second,
                    controller.getLabelPaint(v));
        }
    }

    private void drawEdge(Canvas canvas, E edge, V edgeSource, V edgeTarget) {
        edgeStyle.xy1 = vertex2view(edgeSource);
        edgeStyle.xy2 = vertex2view(edgeTarget);
        //draw
        canvas.drawLine(
                edgeStyle.xy1.first,
                edgeStyle.xy1.second,
                edgeStyle.xy2.first,
                edgeStyle.xy2.second,
                controller.getEdgePaint(edge));
    }

    protected Pair<Float, Float> vertex2view(V vertex) {
        if (vertex.equals(dragging.object)) {
            return dragging.xy;
        }
        return coordinate2view(controller.getPosition(vertex));
    }

    protected Pair<Float, Float> coordinate2view(Coordinate coordinate) {
        return new Pair<>(
                (float) (coordinate.getX() * contentWidth),
                (float) (coordinate.getY() * contentHeight));
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

    private class VertexInfo {
        Pair<Float, Float> xy;
        Paint paint;
        Pair<Float, Float> xyLabel;
        String label;
    }

    private class EdgeInfo {
        Pair<Float, Float> xy1;
        Pair<Float, Float> xy2;
    }

    private class DeletionZone {
        Pair<Float, Float> topLeft;
        Pair<Float, Float> buttomRight;

        boolean contains(Pair<Float, Float> other) {
            return topLeft.first < other.first && buttomRight.first > other.first &&
                    topLeft.second < other.second && buttomRight.second > other.second;
        }
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
                if (deletionZone != null && deletionZone.contains(dragging.xy)) {
                    update = controller.removeVertex(dragging.object);
                } else {
                    controller.update(dragging.old, pair2coordinate(dragging.xy));
                    dragging.object = null;
                    update = true;
                }
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
            if (controller.allowMotion()) {
                dragging.object = obj;
                dragging.old = controller.getPosition(obj);
                dragging.xy = coordinate2view(dragging.old);
                return true;
            }
            return false;
        }

        private boolean insertNewVertex(MotionEvent motionEvent) {
            return controller.addVertex(event2coordinate(motionEvent));
        }
    }

}

