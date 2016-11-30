package org.agp8x.android.lib.andrograph;

import org.agp8x.android.lib.andrograph.test.TestData;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JGraphTTest {
    @Test
    public void generate_graph() throws Exception {
        SimpleGraph<String, DefaultEdge> g = TestData.getStringDefaultEdgeSimpleGraph();

        System.out.println(TestData.graphToDot(g));
    }

}