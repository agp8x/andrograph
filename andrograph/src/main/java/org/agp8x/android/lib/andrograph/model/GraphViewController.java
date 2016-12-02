package org.agp8x.android.lib.andrograph.model;

import android.graphics.Paint;

import org.agp8x.android.lib.andrograph.model.defaults.DefaultEdgePaintProvider;
import org.agp8x.android.lib.andrograph.model.defaults.DefaultPermissionPolicy;
import org.agp8x.android.lib.andrograph.model.defaults.DefaultVertexPaintProvider;
import org.agp8x.android.lib.andrograph.model.defaults.MapPositionProvider;
import org.agp8x.android.lib.andrograph.model.defaults.StringVertexFactory;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashMap;

/**
 * Orchestrate all providers and factories
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */

public class GraphViewController<V, E extends DefaultEdge> implements PositionProvider<V>, EdgePaintProvider<E>, VertexPaintProvider<V>, PermissionPolicy<V, E> {
    private final EdgePaintProvider<E> edgePaintProvider;
    private final VertexFactory<V> vertexFactory;
    private final Graph<V, E> graph;
    private final PositionProvider<V> positionProvider;
    private final VertexPaintProvider<V> vertexPaintProvider;
    private final PermissionPolicy<V, E> permissionPolicy;

    /**
     * initialize with all-default providers, factories and an empty graph
     */
    public GraphViewController() {
        edgePaintProvider = new DefaultEdgePaintProvider<>();
        vertexFactory = new StringVertexFactory<>();
        graph = new SimpleGraph<>((Class<E>) DefaultEdge.class);
        positionProvider = new MapPositionProvider<V>(new HashMap<V, Coordinate>(), new Coordinate());
        vertexPaintProvider = new DefaultVertexPaintProvider<>();
        permissionPolicy = new DefaultPermissionPolicy<V, E>();
    }

    /**
     * Initialize with default factories and providers, and custom {@link Graph} and {@link VertexFactory}
     *
     * @param graph
     * @param vertexFactory
     */
    public GraphViewController(Graph<V, E> graph, VertexFactory<V> vertexFactory) {
        this.graph = graph;
        this.vertexFactory = vertexFactory;
        edgePaintProvider = new DefaultEdgePaintProvider<>();
        positionProvider = new MapPositionProvider<>(new HashMap<V, Coordinate>(), new Coordinate());
        vertexPaintProvider = new DefaultVertexPaintProvider<>();
        permissionPolicy = new DefaultPermissionPolicy<>();
    }

    /**
     * Use custom {@link GraphViewController} for providers
     *
     * @param controller
     * @param graph
     * @param vertexFactory
     */
    public GraphViewController(GraphViewController controller, Graph<V, E> graph, VertexFactory<V> vertexFactory) {
        this.vertexFactory = vertexFactory;
        this.graph = graph;
        edgePaintProvider = controller;
        positionProvider = controller;
        vertexPaintProvider = controller;
        permissionPolicy = controller;
    }

    /**
     * PASS ALL THE PROVIDERS!
     *
     * @param graph               {@link Graph} instance
     * @param positionProvider
     * @param edgePaintProvider
     * @param vertexPaintProvider
     * @param vertexFactory
     * @param permissionPolicy
     */
    public GraphViewController(Graph<V, E> graph, PositionProvider<V> positionProvider, EdgePaintProvider<E> edgePaintProvider, VertexPaintProvider<V> vertexPaintProvider, VertexFactory<V> vertexFactory, PermissionPolicy<V, E> permissionPolicy) {
        this.graph = graph;
        this.positionProvider = positionProvider;
        this.edgePaintProvider = edgePaintProvider;
        this.vertexPaintProvider = vertexPaintProvider;
        this.vertexFactory = vertexFactory;
        this.permissionPolicy = permissionPolicy;
    }

    @Override
    public Coordinate getPosition(V vertex) {
        return positionProvider.getPosition(vertex);
    }

    @Override
    public boolean update(Coordinate old, Coordinate updated) {
        return allowMotion() && positionProvider.update(old, updated);
    }

    @Override
    public V getSelected(Coordinate action) {
        return positionProvider.getSelected(action);
    }

    @Override
    public boolean setPosition(V vertex, Coordinate coordinate) {
        return allowMotion(vertex, coordinate) && positionProvider.setPosition(vertex, coordinate);
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

    public boolean addVertex(Coordinate coordinate) {
        if (vertexFactory == null || !allowVertexInsertion()) {
            return false;
        }
        V vertex = vertexFactory.createVertex();
        graph.addVertex(vertex);
        return positionProvider.setPosition(vertex, coordinate);
    }

    public boolean removeVertex(V vertex) {
        return allowVertexDeletion(vertex) && graph.removeVertex(vertex);
    }

    public boolean addEdge(V source, V target) {
        if (allowEdgeDeletion(source, target)) {
            try {
                return null != graph.addEdge(source, target);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        return false;
    }

    public boolean removeEdge(V source, V target) {
        if (allowEdgeInsertion(source, target)) {
            return null != graph.removeEdge(source, target);
        }
        return false;
    }

    public boolean addOrRemoveEdge(V source, V target) {
        if (graph.containsEdge(source, target)) {
            return removeEdge(source, target);
        } else {
            return addEdge(source, target);
        }
    }

    @Override
    public boolean allowVertexInsertion() {
        return permissionPolicy.allowVertexInsertion();
    }

    @Override
    public boolean allowVertexDeletion(V vertex) {
        return permissionPolicy.allowVertexDeletion(vertex);
    }

    @Override
    public boolean allowMotion() {
        return permissionPolicy.allowMotion();
    }

    @Override
    public boolean allowMotion(V vertex, Coordinate coordinate) {
        return permissionPolicy.allowMotion(vertex, coordinate);
    }

    @Override
    public boolean allowEdgeInsertion(V source, V target) {
        return permissionPolicy.allowEdgeInsertion(source, target);
    }

    @Override
    public boolean allowEdgeDeletion(V source, V target) {
        return permissionPolicy.allowEdgeDeletion(source, target);
    }
}
