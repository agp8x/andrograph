package org.agp8x.android.lib.andrograph;

/**
 * Created by clemensk on 30.11.16.
 */
public interface PositionProvider<V> {
    Coordinate getPosition(V vertex);
    void update(Coordinate old, Coordinate updated);
    V getSelected(Coordinate action);
}
