package org.agp8x.android.lib.andrograph.model.defaults;

import android.graphics.Color;
import android.graphics.Paint;

import org.agp8x.android.lib.andrograph.model.EdgePaintProvider;

/**
 * fast choices of default styling
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */
public class DefaultEdgePaintProvider<E> implements EdgePaintProvider<E> {
    Paint fallback;

    public DefaultEdgePaintProvider() {
        fallback = new Paint();
        fallback.setColor(Color.BLACK);
        fallback.setAntiAlias(true);
    }

    @Override
    public Paint getEdgePaint(E edge) {
        return fallback;
    }
}
