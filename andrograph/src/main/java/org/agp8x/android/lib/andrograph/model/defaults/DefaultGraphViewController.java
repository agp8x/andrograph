package org.agp8x.android.lib.andrograph.model.defaults;

import android.graphics.Paint;
import android.support.annotation.Nullable;

import org.agp8x.android.lib.andrograph.model.Coordinate;
import org.agp8x.android.lib.andrograph.model.EdgeEvent;
import org.agp8x.android.lib.andrograph.model.EdgePaintProvider;
import org.agp8x.android.lib.andrograph.model.GraphViewController;
import org.agp8x.android.lib.andrograph.model.PermissionPolicy;
import org.agp8x.android.lib.andrograph.model.PositionProvider;
import org.agp8x.android.lib.andrograph.model.VertexEvent;
import org.agp8x.android.lib.andrograph.model.VertexPaintProvider;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Orchestrate all providers and factories
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */

public class DefaultGraphViewController<V, E extends DefaultEdge> implements GraphViewController<V, E> {
    protected final EdgePaintProvider<E> edgePaintProvider;
    protected final Supplier<V> vertexFactory;
    protected final Graph<V, E> graph;
    protected final PositionProvider<V> positionProvider;
    protected final VertexPaintProvider<V> vertexPaintProvider;
    protected final PermissionPolicy<V, E> permissionPolicy;
    protected EdgeEvent<V, E> edgeEventHandler;
    protected VertexEvent<V> vertexEventHandler;

    /**
     * initialize with all-default providers, factories and an empty graph
     */
    public DefaultGraphViewController() {
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
    public DefaultGraphViewController(Graph<V, E> graph, Supplier<V> vertexFactory) {
        this.graph = graph;
        this.vertexFactory = vertexFactory;
        edgePaintProvider = new DefaultEdgePaintProvider<>();
        positionProvider = new MapPositionProvider<>(new HashMap<V, Coordinate>(), new Coordinate());
        vertexPaintProvider = new DefaultVertexPaintProvider<>();
        permissionPolicy = new DefaultPermissionPolicy<>();
    }

    /**
     * Use custom {@link GraphViewController} for providers
     *  @param controller
     * @param graph
     * @param vertexFactory
     */
    public DefaultGraphViewController(
            GraphViewController<V, E> controller,
            Graph<V, E> graph,
            Supplier<V> vertexFactory) {
        this.vertexFactory = vertexFactory;
        this.graph = graph;
        edgePaintProvider = controller;
        positionProvider = controller;
        vertexPaintProvider = controller;
        permissionPolicy = controller;
    }

    /**
     * PASS ALL THE PROVIDERS!
     *  @param graph               {@link Graph} instance
     * @param positionProvider
     * @param edgePaintProvider
     * @param vertexPaintProvider
     * @param vertexFactory
     * @param permissionPolicy
     */
    public DefaultGraphViewController(
            Graph<V, E> graph,
            PositionProvider<V> positionProvider,
            EdgePaintProvider<E> edgePaintProvider,
            VertexPaintProvider<V> vertexPaintProvider,
            Supplier<V> vertexFactory,
            PermissionPolicy<V, E> permissionPolicy) {
        this.graph = graph;
        this.positionProvider = positionProvider;
        this.edgePaintProvider = edgePaintProvider;
        this.vertexPaintProvider = vertexPaintProvider;
        this.vertexFactory = vertexFactory;
        this.permissionPolicy = permissionPolicy;
    }

    @Override
    public void setVertexEventHandler(@Nullable VertexEvent handler) {
        vertexEventHandler = handler;
    }

    @Override
    public void setEdgeEventHandler(@Nullable EdgeEvent handler) {
        edgeEventHandler = handler;
    }

    @Override
    public Coordinate getPosition(V vertex) {
        return positionProvider.getPosition(vertex);
    }

    @Override
    public boolean update(Coordinate old, Coordinate updated) {
        V vertex = positionProvider.getSelected(old);
        return allowMotion(vertex, updated) && positionProvider.setPosition(vertex, updated);
    }

    @Override
    public V getSelected(Coordinate action) {
        V selected = positionProvider.getSelected(action);
        if (!graph.containsVertex(selected)) {
            selected = null;
        }
        if (vertexEventHandler != null) {
            if (vertexEventHandler.vertexSelected(selected)) {
                selected = null;
            }
        }
        return selected;
    }

    @Override
    public boolean setPosition(V vertex, Coordinate coordinate) {
        return allowMotion(vertex, coordinate) && positionProvider.setPosition(vertex, coordinate);
    }

    @Override
    public void remove(V vertex) {
        positionProvider.remove(vertex);
    }

    @Override
    public Graph<V, E> getGraph() {
        return graph;
    }

    @Override
    public Paint getEdgePaint(E edge) {
        return edgePaintProvider.getEdgePaint(edge);
    }

    @Override
    public Paint getVertexPaint(V vertex) {
        return vertexPaintProvider.getVertexPaint(vertex);
    }

    @Override
    public Paint getSelectedPaint(V vertex) {
        return vertexPaintProvider.getSelectedPaint(vertex);
    }

    @Override
    public int getRadius(V vertex) {
        return vertexPaintProvider.getRadius(vertex);
    }

    @Override
    public String getLabel(V vertex) {
        return vertexPaintProvider.getLabel(vertex);
    }

    @Override
    public Paint getLabelPaint(V vertex) {
        return vertexPaintProvider.getLabelPaint(vertex);
    }

    @Override
    public Coordinate getLabelOffset(V vertex) {
        return vertexPaintProvider.getLabelOffset(vertex);
    }

    @Override
    public boolean addVertex(Coordinate coordinate) {
        if (vertexFactory == null || !allowVertexInsertion()) {
            return false;
        }
        V vertex = vertexFactory.get();
        graph.addVertex(vertex);
        return positionProvider.setPosition(vertex, coordinate);
    }

    @Override
    public boolean removeVertex(V vertex) {
        remove(vertex);
        return allowVertexDeletion(vertex) && graph.removeVertex(vertex);
    }

    protected boolean addEdge(V source, V target) {
        if (allowEdgeInsertion(source, target)) {
            try {
                return null != graph.addEdge(source, target);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        return false;
    }

    protected boolean removeEdge(V source, V target) {
        return allowEdgeDeletion(source, target) && null != graph.removeEdge(source, target);
    }

    @Override
    public boolean addOrRemoveEdge(V source, V target) {
        if (edgeEventHandler != null) {
            if (edgeEventHandler.edgeSelected(source, target, graph.getEdge(source, target))) {
                return true;
            }
        }
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
