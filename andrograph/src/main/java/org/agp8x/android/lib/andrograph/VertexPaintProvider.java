package org.agp8x.android.lib.andrograph;

import android.graphics.Paint;

/** Define style of vertices
 * @author clemensk
 *         <p>
 *         30.11.16.
 */
public interface VertexPaintProvider<V> {
    Paint getVertexPaint(V vertex);

    Paint getSelectedPaint(V vertex);

    int getRadius(V vertex);
}

