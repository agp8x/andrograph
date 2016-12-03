package org.agp8x.android.lib.andrograph.model;

import android.graphics.Paint;

/** Define style of vertices
 * @author clemensk
 *         <p>
 *         30.11.16.
 */
public interface VertexPaintProvider<V> {
    /**
     * Get {@link Paint} for a given vertex
     * @param vertex vertex to style
     * @return Paint for vertex
     */
    Paint getVertexPaint(V vertex);

    /**
     * Get {@link Paint} for given selected vertex
     * @param vertex vertex selected for edge insertion/deletion
     * @return Paint for vertex
     */
    Paint getSelectedPaint(V vertex);

    /**
     * Get radius for given vertex
     * @param vertex vertex to draw
     * @return radius
     */
    int getRadius(V vertex);

    /**
     * String representation of vertex to create label.
     *
     * null labels will not be drawn
     * @param vertex vertex to get label for
     * @return label of vertex
     */
    String getLabel(V vertex);

    /**
     * Get {@link Paint} for given vertex's label
     * @param vertex vertex to style label for
     * @return Paint of label
     */
    Paint getLabelPaint(V vertex);

    /**
     * Get offset of vertex's label wrt. vertex's {@link Coordinate}
     * @param vertex vertex to draw
     * @return offset of label
     */
    Coordinate getLabelOffset(V vertex);
}

