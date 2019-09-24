package org.agp8x.android.lib.andrograph.model.defaults;


import java.util.function.Supplier;

/**
 * create new vertices with an increasing int as label
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */

public class StringVertexFactory<String> implements Supplier<String> {
    protected int count;

    public StringVertexFactory() {
        count = 0;
    }

    @Override
    public String get() {
        count++;
        return (String) Integer.toString(count);
    }

}
