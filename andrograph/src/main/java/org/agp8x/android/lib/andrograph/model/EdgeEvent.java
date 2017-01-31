package org.agp8x.android.lib.andrograph.model;

import org.jgrapht.graph.DefaultEdge;

/**
 * Provides an hook into the addOrRemoveEdge method of a GraphViewController instance to
 * consume edge update events
 *
 * @author clemensk
 */

public interface EdgeEvent<V, E extends DefaultEdge> {
    /**
     * consume an edge update event
     *
     * @param source source vertex
     * @param target target vertex
     * @param edge   edge to react on, null if edge is not in graph
     * @return true if event was consumed, else false
     */
    boolean edgeSelected(V source, V target, E edge);
}
