package org.agp8x.android.lib.andrograph.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import org.agp8x.android.lib.andrograph.R;
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
    protected Dragging dragging;
    private int contentWidth = -1;
    private int contentHeight = -1;
    private boolean insertionMode = true;
    protected VertexInfo vertexStyle;
    protected EdgeInfo edgeStyle;
    protected DeletionZone deletionZone;

    public void setInsertionMode(boolean insertionMode) {
        this.insertionMode = insertionMode;
    }

    public boolean isInsertionMode() {
        return insertionMode;
    }

    public void setDeletionZone(DeletionZone deletionZone) {
        this.deletionZone = deletionZone;
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

    protected void init(AttributeSet attrs, int defStyle) {
        //Android studio tells me variable creation/allocation during onDraw is bad
        dragging = new Dragging();
        vertexStyle = new VertexInfo();
        edgeStyle = new EdgeInfo();

        setOnTouchListener(new GraphOnTouchListener());

        final TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.GraphView, defStyle, 0);
        if (attributes.hasValue(R.styleable.GraphView_deletionZoneBottom) &&
                attributes.hasValue(R.styleable.GraphView_deletionZoneRight) &&
                attributes.hasValue(R.styleable.GraphView_deletionZoneTop) &&
                attributes.hasValue(R.styleable.GraphView_deletionZoneLeft)
                ) {
            deletionZone = new DeletionZone();
            float x1 = attributes.getFloat(R.styleable.GraphView_deletionZoneRight, 0);
            float y1 = attributes.getFloat(R.styleable.GraphView_deletionZoneBottom, 0);
            float x2 = attributes.getFloat(R.styleable.GraphView_deletionZoneLeft, 0);
            float y2 = attributes.getFloat(R.styleable.GraphView_deletionZoneTop, 0);
            deletionZone.borders = new Pair<>(new Coordinate(x1, y1), new Coordinate(x2, y2));
            deletionZone.paint = new Paint();
            deletionZone.paint.setColor(attributes.getColor(R.styleable.GraphView_deletionZoneColor, Color.RED));
            deletionZone.paint.setAlpha(attributes.getInt(R.styleable.GraphView_deletionZoneAlpha, 128));
        } else {
            deletionZone = null;
        }
        attributes.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (controller == null) {
            return;
        }

        if (contentHeight < 0) {
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            contentHeight = getHeight() - paddingTop - paddingBottom;
        }
        if (contentWidth < 0) {
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            contentWidth = getWidth() - paddingLeft - paddingRight;
        }
        if (deletionZone != null && deletionZone.leftTop == null) {
            deletionZone.rightButtom = coordinate2view(deletionZone.borders.first);
            deletionZone.leftTop = coordinate2view(deletionZone.borders.second);
        }

        Graph<V, E> g = controller.getGraph();
        for (V v : g.vertexSet()) {
            drawVertex(canvas, v);
        }
        for (E edge : g.edgeSet()) {
            drawEdge(canvas, edge, g.getEdgeSource(edge), g.getEdgeTarget(edge));
        }
        if (deletionZone != null) {
            drawDeletionZone(canvas);
        }
    }

    protected void drawDeletionZone(Canvas canvas) {
        canvas.drawRect(deletionZone.leftTop.first,
                deletionZone.leftTop.second,
                deletionZone.rightButtom.first,
                deletionZone.rightButtom.second,
                deletionZone.paint);
    }

    protected void drawVertex(Canvas canvas, V v) {
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

    protected void drawEdge(Canvas canvas, E edge, V edgeSource, V edgeTarget) {
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
        protected V object;
        protected Coordinate old;
        protected Pair<Float, Float> xy;
    }

    private class VertexInfo {
        protected Pair<Float, Float> xy;
        protected Paint paint;
        protected Pair<Float, Float> xyLabel;
        protected String label;
    }

    private class EdgeInfo {
        protected Pair<Float, Float> xy1;
        protected Pair<Float, Float> xy2;
    }

    public class DeletionZone {
        protected Pair<Float, Float> leftTop;
        protected Pair<Float, Float> rightButtom;
        protected Paint paint;
        protected Pair<Coordinate, Coordinate> borders;

        boolean contains(Pair<Float, Float> other) {
            return ((leftTop.first < other.first) &&
                    (rightButtom.first > other.first) &&
                    (leftTop.second < other.second) &&
                    (rightButtom.second > other.second)
            );
        }
    }


    protected class GraphOnTouchListener implements OnTouchListener {
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

        protected boolean handleStart(MotionEvent motionEvent) {
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
                    update = updateEdge(obj);
                }
            }
            return update;
        }

        protected boolean handleMove(MotionEvent motionEvent) {
            boolean update = false;
            if (insertionMode) {
                update = updateDraggedVertex(motionEvent);
            }
            return update;
        }

        protected boolean handleStop(MotionEvent motionEvent) {
            boolean update = false;
            if (insertionMode) {
                update = stopDragging(motionEvent);
            }
            return update;
        }

        protected boolean stopDragging(MotionEvent motionEvent) {
            boolean update = false;
            if (dragging.object != null) {
                dragging.xy = event2pair(motionEvent);
                if (deletionZone != null && deletionZone.contains(dragging.xy)) {
                    update = controller.removeVertex(dragging.object);
                } else {
                    update = controller.update(dragging.old, pair2coordinate(dragging.xy));
                }
                dragging.object = null;
            }
            return update;
        }

        protected boolean updateDraggedVertex(MotionEvent motionEvent) {
            boolean update = false;
            if (dragging.object != null) {
                dragging.xy = event2pair(motionEvent);
                update = true;
            }
            return update;
        }

        protected boolean updateEdge(V obj) {
            boolean update = controller.addOrRemoveEdge(dragging.object, obj);
            dragging.object = null;
            return update;
        }

        protected boolean startDragging(V obj) {
            if (controller.allowMotion()) {
                dragging.object = obj;
                dragging.old = controller.getPosition(obj);
                dragging.xy = coordinate2view(dragging.old);
                return true;
            }
            return false;
        }

        protected boolean insertNewVertex(MotionEvent motionEvent) {
            return controller.addVertex(event2coordinate(motionEvent));
        }
    }

}

