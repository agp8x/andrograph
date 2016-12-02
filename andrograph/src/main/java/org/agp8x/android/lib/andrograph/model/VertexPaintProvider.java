package org.agp8x.android.lib.andrograph.model;

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

    String getLabel(V vertex);

    Paint getLabelPaint(V vertex);

    Coordinate getLabelOffset(V vertex);
}

