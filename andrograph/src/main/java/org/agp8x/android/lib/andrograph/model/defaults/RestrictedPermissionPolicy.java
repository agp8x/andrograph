package org.agp8x.android.lib.andrograph.model.defaults;

import org.agp8x.android.lib.andrograph.model.Coordinate;
import org.agp8x.android.lib.andrograph.model.PermissionPolicy;
import org.jgrapht.graph.DefaultEdge;

/**
 * Created by clemensk on 02.12.16.
 */

public class RestrictedPermissionPolicy<V,E extends DefaultEdge> implements PermissionPolicy<V,E> {
    @Override
    public boolean allowVertexInsertion() {
        return false;
    }

    @Override
    public boolean allowVertexDeletion(V vertex) {
        return false;
    }

    @Override
    public boolean allowMotion() {
        return false;
    }

    @Override
    public boolean allowMotion(V vertex, Coordinate coordinate) {
        return false;
    }

    @Override
    public boolean allowEdgeInsertion(V source, V target) {
        return false;
    }

    @Override
    public boolean allowEdgeDeletion(V source, V target) {
        return false;
    }
}
