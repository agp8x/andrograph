package org.agp8x.android.lib.andrograph;

import android.graphics.Paint;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Created by clemensk on 30.11.16.
 */

public class GraphViewController<V, E extends DefaultEdge> implements PositionProvider<V> {
    private final EdgePaintProvider<E> edgePaintProvider;
    private Graph<V, E> graph;
    private PositionProvider<V> positionProvider;

    public GraphViewController(Graph<V, E> graph, PositionProvider<V> positionProvider, EdgePaintProvider<E> edgePaintProvider) {
        this.graph = graph;
        this.positionProvider = positionProvider;
        this.edgePaintProvider = edgePaintProvider;
        //TODO: add vertexPaintProvider
    }

    @Override
    public Coordinate getPosition(V vertex) {
        return positionProvider.getPosition(vertex);
    }

    @Override
    public void update(Coordinate old, Coordinate updated) {
        positionProvider.update(old, updated);
    }

    @Override
    public V getSelected(Coordinate action) {
        return positionProvider.getSelected(action);
    }

    public Graph<V, E> getGraph() {
        return graph;
    }

    public Paint getEdgePaint(E edge) {
        return edgePaintProvider.get(edge);
    }
}
