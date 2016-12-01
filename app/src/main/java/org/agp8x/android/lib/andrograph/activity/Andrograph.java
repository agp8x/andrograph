package org.agp8x.android.lib.andrograph.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.agp8x.android.lib.andrograph.Coordinate;
import org.agp8x.android.lib.andrograph.DefaultEdgePaintProvider;
import org.agp8x.android.lib.andrograph.StringVertexFactory;
import org.agp8x.android.lib.andrograph.DefaultVertexPaintProvider;
import org.agp8x.android.lib.andrograph.EdgePaintProvider;
import org.agp8x.android.lib.andrograph.GraphViewController;
import org.agp8x.android.lib.andrograph.MapPositionProvider;
import org.agp8x.android.lib.andrograph.PositionProvider;
import org.agp8x.android.lib.andrograph.R;
import org.agp8x.android.lib.andrograph.VertexPaintProvider;
import org.agp8x.android.lib.andrograph.test.TestData;
import org.agp8x.android.lib.andrograph.view.GraphView;
import org.jgrapht.VertexFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * sample activity to work with graphs
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */
public class Andrograph extends AppCompatActivity {

    private SimpleGraph<String, DefaultEdge> graph;
    private TextView tv;
    private GraphView<String, DefaultEdge> gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrograph);
        tv = (TextView) findViewById(R.id.textview);
        graph = TestData.getStringDefaultEdgeSimpleGraph();
        tv.setText(TestData.graphToDot(graph));
        gv = (GraphView<String, DefaultEdge>) findViewById(R.id.graphview);
        PositionProvider<String> positionProvider = new MapPositionProvider<>(TestData.getStringDefaultEdgeSimpleGraphPositions(), new Coordinate(0.5, 0.8));
        EdgePaintProvider<DefaultEdge> epp = new DefaultEdgePaintProvider<>();
        VertexPaintProvider<String> vpp = new DefaultVertexPaintProvider<>();
        VertexFactory<String> vf = new StringVertexFactory<>();
        GraphViewController<String, DefaultEdge> gvc = new GraphViewController<>(graph, positionProvider, epp, vpp, vf);

        gv.setController(gvc);

        final Switch creationSwitch = (Switch) findViewById(R.id.switch1);
        creationSwitch.setChecked(true);
        creationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gv.setInsertionMode(creationSwitch.isChecked());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (graph != null && tv != null) {
            tv.setText(TestData.graphToDot(graph));
        }
        return super.onTouchEvent(event);
    }
}
