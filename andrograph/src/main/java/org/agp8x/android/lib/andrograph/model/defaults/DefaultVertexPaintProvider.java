package org.agp8x.android.lib.andrograph.model.defaults;

import android.graphics.Color;
import android.graphics.Paint;

import org.agp8x.android.lib.andrograph.model.VertexPaintProvider;

/**
 * fast choices of default styling
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */
public class DefaultVertexPaintProvider<V> implements VertexPaintProvider<V> {
    Paint fallback;
    Paint selected;

    public DefaultVertexPaintProvider() {
        fallback = new Paint();
        fallback.setColor(Color.RED);
        fallback.setStrokeWidth(5);
        fallback.setAntiAlias(true);

        selected = new Paint(fallback);
        selected.setColor(Color.GREEN);
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
}
