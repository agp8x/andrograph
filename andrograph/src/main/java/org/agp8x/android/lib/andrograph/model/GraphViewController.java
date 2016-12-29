package org.agp8x.android.lib.andrograph.model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Control all actions happening on the {@link org.agp8x.android.lib.andrograph.view.GraphView}
 * <p>
 * Delegation of implemented interfaces into subclasses is recommended for clarity.
 * <p>
 * All methods here are called during onDraw() of the GraphView, so consider keeping this
 * implementations rather lightweight
 *
 * @author clemensk
 *         <p>
 *         on 03.12.16.
 */

public interface GraphViewController<V, E extends DefaultEdge> extends
        PositionProvider<V>,
        EdgePaintProvider<E>,
        VertexPaintProvider<V>,
        PermissionPolicy<V, E> {
    /**
     * Get {@link Graph}
     *
     * @return current Graph
     */
    Graph<V, E> getGraph();

    /**
     * Add new vertex at given {@link Coordinate}
     *
     * @param coordinate {@link Coordinate} to create vertex at
     * @return whether new vertex was created
     */
    boolean addVertex(Coordinate coordinate);

    /**
     * Delete vertex
     *
     * @param vertex Vertex to remove
     * @return whether vertex was deleted
     */
    boolean removeVertex(V vertex);

    /**
     * Add or remove Edge between source and target vertex
     *
     * @param source Vertex
     * @param target Vertex
     * @return whether graph was modified (i.e. success of modification)
     */
    boolean addOrRemoveEdge(V source, V target);
}
