package org.agp8x.android.lib.andrograph;

import android.graphics.Paint;

/**
 * Created by clemensk on 30.11.16.
 */

public interface EdgePaintProvider<E> {
    Paint get(E edge);
}
