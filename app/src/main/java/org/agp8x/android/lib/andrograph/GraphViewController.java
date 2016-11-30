package org.agp8x.android.lib.andrograph;

import org.jgrapht.Graph;

/**
 * Created by clemensk on 30.11.16.
 */

public class GraphViewController<V,E> implements PositionProvider<V>{
    private Graph<V,E> graph;
    private PositionProvider<V> positionProvider;

    public GraphViewController(Graph<V, E> graph, PositionProvider<V> positionProvider) {
        this.graph = graph;
        this.positionProvider = positionProvider;
    }

    @Override
    public Coordinate getPosition(V vertex) {
        return positionProvider.getPosition(vertex);
    }

    @Override
    public void update(Coordinate old, Coordinate updated) {
        positionProvider.update(old,updated);
    }

    @Override
    public V getSelected(Coordinate action) {
        return positionProvider.getSelected(action);
    }

    public Graph<V, E> getGraph() {
        return graph;
    }
}
