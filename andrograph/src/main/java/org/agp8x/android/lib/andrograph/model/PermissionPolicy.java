package org.agp8x.android.lib.andrograph.model;

import org.jgrapht.graph.DefaultEdge;

/**
 * Allow actions on graph
 *
 * @author clemensk on 02.12.16.
 */

public interface PermissionPolicy<V, E extends DefaultEdge> {
    boolean allowVertexInsertion();

    boolean allowVertexDeletion(V vertex);

    boolean allowMotion();

    boolean allowMotion(V vertex, Coordinate coordinate);

    boolean allowEdgeInsertion(V source, V target);

    boolean allowEdgeDeletion(V source, V target);
}
