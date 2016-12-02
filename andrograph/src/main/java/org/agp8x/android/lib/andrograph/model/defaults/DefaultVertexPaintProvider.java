package org.agp8x.android.lib.andrograph.model.defaults;

import android.graphics.Color;
import android.graphics.Paint;

import org.agp8x.android.lib.andrograph.model.Coordinate;
import org.agp8x.android.lib.andrograph.model.VertexPaintProvider;

/**
 * fast choices of default styling
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */
public class DefaultVertexPaintProvider<V> implements VertexPaintProvider<V> {
    private Paint fallback;
   private  Paint selected;
    private Paint labelPaint;
    private Coordinate offset;

    public DefaultVertexPaintProvider() {
        fallback = new Paint();
        fallback.setColor(Color.RED);
        fallback.setStrokeWidth(5);
        fallback.setAntiAlias(true);

        selected = new Paint(fallback);
        selected.setColor(Color.GREEN);

        labelPaint = new Paint();
        labelPaint.setColor(Color.YELLOW);
        labelPaint.setAntiAlias(true);
        labelPaint.setTextSize(25);
        labelPaint.setStrokeWidth(2);

        offset = new Coordinate(-0.01,0.01);
    }

    @Override
    public Paint getVertexPaint(V vertex) {
        return fallback;
    }

    @Override
    public Paint getSelectedPaint(V vertex) {
        return selected;
    }

    @Override
    public int getRadius(V vertex) {
        return 25;
    }

    @Override
    public String getLabel(V vertex) {
        return String.valueOf(vertex);
    }

    @Override
    public Paint getLabelPaint(V vertex) {
        return labelPaint;
    }

    @Override
    public Coordinate getLabelOffset(V vertex) {
        return offset;
    }
}
