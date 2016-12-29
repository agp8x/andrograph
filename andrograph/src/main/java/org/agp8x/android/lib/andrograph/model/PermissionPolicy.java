package org.agp8x.android.lib.andrograph.model;

import org.jgrapht.graph.DefaultEdge;

/**
 * Allow actions on graph
 *
 * @author clemensk on 02.12.16.
 */

public interface PermissionPolicy<V, E extends DefaultEdge> {
    /**
     * Is insertion of new vertices allowed?
     *
     * @return whether new vertices may be created
     */
    boolean allowVertexInsertion();

    /**
     * Is deletion of given vertex allowed?
     *
     * @param vertex vertex to remove
     * @return whether given vertex may be deleted
     */
    boolean allowVertexDeletion(V vertex);

    /**
     * Is moving of vertices on screen generally allowed?
     *
     * @return whether moving of vertices is allowed
     */
    boolean allowMotion();

    /**
     * Is motion of given vertex to given {@link Coordinate} allowed?
     *
     * @param vertex     vertex to move
     * @param coordinate target of motion
     * @return whether moving of given vertex to coordinate is allowed
     */
    boolean allowMotion(V vertex, Coordinate coordinate);

    /**
     * Is creation of edge between vertices source and target allowed?
     *
     * @param source source vertex
     * @param target target vertex
     * @return whether insertion of new edge between given vertices is allowed
     */
    boolean allowEdgeInsertion(V source, V target);

    /**
     * Is deletion of edge between vertices source and target allowed?
     *
     * @param source source vertex
     * @param target target vertex
     * @return whether deletion of new edge between given vertices is allowed
     */
    boolean allowEdgeDeletion(V source, V target);
}
