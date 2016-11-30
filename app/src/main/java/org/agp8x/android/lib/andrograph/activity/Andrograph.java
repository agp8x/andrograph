package org.agp8x.android.lib.andrograph.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.agp8x.android.lib.andrograph.Coordinate;
import org.agp8x.android.lib.andrograph.DefaultEdgePaintProvider;
import org.agp8x.android.lib.andrograph.EdgePaintProvider;
import org.agp8x.android.lib.andrograph.GraphViewController;
import org.agp8x.android.lib.andrograph.MapPositionProvider;
import org.agp8x.android.lib.andrograph.PositionProvider;
import org.agp8x.android.lib.andrograph.R;
import org.agp8x.android.lib.andrograph.test.TestData;
import org.agp8x.android.lib.andrograph.view.GraphView;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Andrograph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrograph);
        TextView tv = (TextView) findViewById(R.id.textview);
        SimpleGraph<String, DefaultEdge> graph = TestData.getStringDefaultEdgeSimpleGraph();
        tv.setText(TestData.graphToDot(graph));
        GraphView<String, DefaultEdge> gv = (GraphView<String, DefaultEdge>) findViewById(R.id.graphview);
        PositionProvider<String> positionProvider = new MapPositionProvider<>(TestData.getStringDefaultEdgeSimpleGraphPositions(), new Coordinate(0.5, 0.8));
        EdgePaintProvider<DefaultEdge> epp = new DefaultEdgePaintProvider<>();
        GraphViewController<String, DefaultEdge> gvc = new GraphViewController<>(graph, positionProvider, epp);

        gv.setController(gvc);
    }
}
