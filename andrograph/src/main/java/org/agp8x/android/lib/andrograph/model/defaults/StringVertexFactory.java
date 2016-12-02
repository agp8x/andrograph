package org.agp8x.android.lib.andrograph.model.defaults;

import org.jgrapht.VertexFactory;

/**
 * create new vertices with an increasing int as label
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */

public class StringVertexFactory<String> implements VertexFactory<String> {
    private int count;

    public StringVertexFactory() {
        count = 0;
    }

    @Override
    public String createVertex() {
        count++;
        return (String) Integer.toString(count);
    }
}
