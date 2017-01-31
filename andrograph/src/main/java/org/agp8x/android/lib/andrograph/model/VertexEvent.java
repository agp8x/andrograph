package org.agp8x.android.lib.andrograph.model;


import android.support.annotation.Nullable;

/**
 * Provides an hook into the selection of vertices to consume it
 *
 * @author clemensk
 */
public interface VertexEvent<V> {
    /**
     * consume a vertex selection event
     *
     * @param vertex selected vertex
     * @return true if event was consumed, else false
     */
    boolean vertexSelected(@Nullable V vertex);
}
