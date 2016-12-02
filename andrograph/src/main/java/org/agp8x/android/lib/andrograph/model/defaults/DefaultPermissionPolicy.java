package org.agp8x.android.lib.andrograph.model.defaults;

import org.agp8x.android.lib.andrograph.model.Coordinate;
import org.agp8x.android.lib.andrograph.model.PermissionPolicy;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author  clemensk on 02.12.16.
 */

public class DefaultPermissionPolicy<V, E extends DefaultEdge> implements PermissionPolicy<V, E> {
    @Override
    public boolean allowVertexInsertion() {
        return true;
    }

    @Override
    public boolean allowVertexDeletion(V vertex) {
        return true;
    }

    @Override
    public boolean allowMotion() {
        return true;
    }

    @Override
    public boolean allowMotion(V vertex, Coordinate coordinate) {
        return true;
    }

    @Override
    public boolean allowEdgeInsertion(V source, V target) {
        return true;
    }

    @Override
    public boolean allowEdgeDeletion(V source, V target) {
        return true;
    }
}
