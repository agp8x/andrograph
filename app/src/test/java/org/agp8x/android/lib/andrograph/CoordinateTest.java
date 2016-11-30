package org.agp8x.android.lib.andrograph;

import org.junit.Test;

/**
 * Created by clemensk on 30.11.16.
 */
public class CoordinateTest {
    @Test
    public void intersects() throws Exception {
        Coordinate c1 = new Coordinate(0.5,0.5);
        Coordinate c2= new Coordinate(0.5,.51);
        System.out.println(c1.intersects(c2));
    }

}