package org.agp8x.android.lib.andrograph;

import android.graphics.Paint;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.graph.DefaultEdge;

/**
 * Orchestrate all providers and factories
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */

public class GraphViewController<V, E extends DefaultEdge> implements PositionProvider<V>, EdgePaintProvider<E>, VertexPaintProvider<V> {
    private final EdgePaintProvider<E> edgePaintProvider;
    private final VertexFactory<V> vertexFactory;
    private Graph<V, E> graph;
    private PositionProvider<V> positionProvider;
    private VertexPaintProvider<V> vertexPaintProvider;

    public GraphViewController(Graph<V, E> graph, PositionProvider<V> positionProvider, EdgePaintProvider<E> edgePaintProvider, VertexPaintProvider<V> vertexPaintProvider, VertexFactory<V> vertexFactory) {
        this.graph = graph;
        this.positionProvider = positionProvider;
        this.edgePaintProvider = edgePaintProvider;
        this.vertexPaintProvider = vertexPaintProvider;
        this.vertexFactory = vertexFactory;
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

    @Override
    public void setPosition(V vertex, Coordinate position) {
        positionProvider.setPosition(vertex, position);
    }

    public Graph<V, E> getGraph() {
        return graph;
    }

    public Paint getEdgePaint(E edge) {
        return edgePaintProvider.getEdgePaint(edge);
    }

    public Paint getVertexPaint(V vertex) {
        return vertexPaintProvider.getVertexPaint(vertex);
    }

    public Paint getSelectedPaint(V vertex) {
        return vertexPaintProvider.getSelectedPaint(vertex);
    }

    public int getRadius(V vertex) {
        return vertexPaintProvider.getRadius(vertex);
    }

    public V addVertex() {
        V vertex = vertexFactory.createVertex();
        graph.addVertex(vertex);
        return vertex;
    }
}
