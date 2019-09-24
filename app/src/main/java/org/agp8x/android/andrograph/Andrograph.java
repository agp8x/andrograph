package org.agp8x.android.andrograph;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.agp8x.android.lib.andrograph.model.Coordinate;
import org.agp8x.android.lib.andrograph.model.EdgePaintProvider;
import org.agp8x.android.lib.andrograph.model.GraphViewController;
import org.agp8x.android.lib.andrograph.model.PermissionPolicy;
import org.agp8x.android.lib.andrograph.model.PositionProvider;
import org.agp8x.android.lib.andrograph.model.VertexPaintProvider;
import org.agp8x.android.lib.andrograph.model.defaults.DefaultEdgePaintProvider;
import org.agp8x.android.lib.andrograph.model.defaults.DefaultGraphViewController;
import org.agp8x.android.lib.andrograph.model.defaults.DefaultPermissionPolicy;
import org.agp8x.android.lib.andrograph.model.defaults.DefaultVertexPaintProvider;
import org.agp8x.android.lib.andrograph.model.defaults.MapPositionProvider;
import org.agp8x.android.lib.andrograph.model.defaults.RestrictedPermissionPolicy;
import org.agp8x.android.lib.andrograph.model.defaults.StringVertexFactory;
import org.agp8x.android.lib.andrograph.test.TestData;
import org.agp8x.android.lib.andrograph.view.GraphView;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.function.Supplier;

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
        Supplier<String> vf = new StringVertexFactory<>();
        PermissionPolicy<String, DefaultEdge> pp = new DefaultPermissionPolicy<>();
        //pp=new RestrictedPermissionPolicy<>();
        GraphViewController<String, DefaultEdge> gvc = new DefaultGraphViewController<>(graph, positionProvider, epp, vpp, vf, pp);

        gv.setController(gvc);

        final Switch creationSwitch = (Switch) findViewById(R.id.switch1);
        creationSwitch.setChecked(true);
        creationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gv.setInsertionMode(creationSwitch.isChecked());
                creationSwitch.setChecked(gv.isInsertionMode());
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
