package org.agp8x.android.lib.andrograph;

import android.graphics.Paint;

/**define style of edges
 * @author clemensk
 *         <p>
 *         30.11.16.
 */

public interface EdgePaintProvider<E> {
    Paint getEdgePaint(E edge);
}
