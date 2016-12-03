package org.agp8x.android.lib.andrograph.model;

import android.graphics.Paint;

/**
 * define style of edges
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */

public interface EdgePaintProvider<E> {
    /**
     * Get {@link Paint} for an Edge
     *
     * @param edge Edge
     * @return Paint for edge
     */
    Paint getEdgePaint(E edge);
}
