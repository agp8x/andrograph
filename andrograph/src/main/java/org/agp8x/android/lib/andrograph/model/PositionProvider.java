package org.agp8x.android.lib.andrograph.model;

/**
 * Provide and store relative positions of vertices
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */
public interface PositionProvider<V> {
    Coordinate getPosition(V vertex);

    boolean update(Coordinate old, Coordinate updated);

    V getSelected(Coordinate action);

    boolean setPosition(V vertex, Coordinate position);
}
