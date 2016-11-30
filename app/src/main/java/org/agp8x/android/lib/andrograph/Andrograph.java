package org.agp8x.android.lib.andrograph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.agp8x.android.lib.andrograph.test.TestData;

public class Andrograph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrograph);
        TextView tv= (TextView) findViewById(R.id.textview);
        tv.setText(TestData.graphToDot(TestData.getStringDefaultEdgeSimpleGraph()));
    }
}
