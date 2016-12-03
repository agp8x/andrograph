package org.agp8x.android.lib.andrograph.model;

/**
 * Provide and store relative positions of vertices
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */
public interface PositionProvider<V> {
    /**
     * Get {@link Coordinate} of given vertex on screen
     *
     * @param vertex vertex to position
     * @return Coordinate of vertex
     */
    Coordinate getPosition(V vertex);

    /**
     * Move vertex from given {@link Coordinate} to new {@link Coordinate}
     *
     * @param old     previous {@link Coordinate}
     * @param updated new {@link Coordinate}
     * @return whether a vertex was moved
     */
    boolean update(Coordinate old, Coordinate updated);

    /**
     * Get vertex on given {@link Coordinate}
     *
     * @param action {@link Coordinate} to find vertex for
     * @return vertex on given {@link Coordinate} -- {@code null} if no vertex was found
     */
    V getSelected(Coordinate action);

    /**
     * Set/Update {@link Coordinate} of given vertex
     *
     * @param vertex   vertex to position
     * @param position {@link Coordinate}
     * @return whether an update has been performed
     */
    boolean setPosition(V vertex, Coordinate position);
}
