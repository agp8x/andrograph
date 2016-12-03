package org.agp8x.android.lib.andrograph.model.defaults;

import org.agp8x.android.lib.andrograph.model.Coordinate;
import org.agp8x.android.lib.andrograph.model.PermissionPolicy;
import org.jgrapht.graph.DefaultEdge;

/**
 * Rather restrictive permission set (allows just viewing, no editing)
 *
 * @author clemensk
 *         <p>
 *         on 02.12.16.
 */

public class RestrictedPermissionPolicy<V, E extends DefaultEdge> implements PermissionPolicy<V, E> {
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
        return allowMotion();
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
