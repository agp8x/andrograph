package org.agp8x.android.lib.andrograph;

/**
 * Provide and store relative positions of vertices
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */
public interface PositionProvider<V> {
    Coordinate getPosition(V vertex);

    void update(Coordinate old, Coordinate updated);

    V getSelected(Coordinate action);

    void setPosition(V vertex, Coordinate position);
}
