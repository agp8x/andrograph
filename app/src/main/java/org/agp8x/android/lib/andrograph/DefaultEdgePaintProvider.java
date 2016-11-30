package org.agp8x.android.lib.andrograph;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by clemensk on 30.11.16.
 */
public class DefaultEdgePaintProvider<E> implements EdgePaintProvider<E> {
    Paint fallback;

    public DefaultEdgePaintProvider() {
        fallback = new Paint();
        fallback.setColor(Color.BLACK);
    }

    @Override
    public Paint get(Object edge) {
        return fallback;
    }
}
