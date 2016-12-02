package org.agp8x.android.lib.andrograph.test;

import android.support.annotation.NonNull;

import org.agp8x.android.lib.andrograph.model.Coordinate;
import org.jgrapht.Graph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.VertexNameProvider;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/** Helper to create a dummy graph
 * @author  clemensk
 *
 * 30.11.16.
 */

public class TestData {

    @NonNull
    public static SimpleGraph<String, DefaultEdge> getStringDefaultEdgeSimpleGraph() {
        SimpleGraph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        String v1 = "a";
        String v2 = "b";
        String v3 = "c";
        String v4 = "d";
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v1, v3);
        g.addEdge(v1, v4);
        return g;
    }
    public static Map<String, Coordinate> getStringDefaultEdgeSimpleGraphPositions(){
        Map<String,Coordinate> map= new HashMap<>();
        map.put("a", new Coordinate(0.25,0.25));
        map.put("b", new Coordinate(0.5,0.5));
        map.put("c", new Coordinate(0.75,0.75));
        return map;
    }

    public static String graphToDot(Graph<String, DefaultEdge> graph) {
        VertexNameProvider<String> idProvider = new VertexNameProvider<String>() {
            @Override
            public String getVertexName(String vertex) {
                return vertex;
            }
        };
        DOTExporter<String, DefaultEdge> dote = new DOTExporter<>(idProvider, null, null);
        StringWriter stringWriter = new StringWriter();
        dote.export(stringWriter, graph);
        return stringWriter.toString();
    }
}
