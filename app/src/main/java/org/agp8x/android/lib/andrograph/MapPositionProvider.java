package org.agp8x.android.lib.andrograph;

import java.util.Map;

/**
 * Store {@link Coordinate}s for Vertices in a Map. Might be inefficient
 *
 * @author clemensk
 *         <p>
 *         30.11.16.
 */

public class MapPositionProvider<V> implements PositionProvider<V> {
    private final Coordinate fallback;
    private Map<V, Coordinate> positionMap;

    public MapPositionProvider(Map<V, Coordinate> positionMap, Coordinate fallback) {
        this.positionMap = positionMap;
        this.fallback = fallback;
    }

    @Override
    public Coordinate getPosition(V vertex) {
        if (!positionMap.containsKey(vertex)) {
            System.out.println("not in map " + fallback);
            positionMap.put(vertex, fallback);
        }
        return positionMap.get(vertex);
    }

    @Override
    public void update(Coordinate old, Coordinate updated) {
        if (positionMap.containsValue(old)) {
            V key = null;
            for (Map.Entry<V, Coordinate> entry : positionMap.entrySet()) {
                if (entry.getValue().equals(old)) {
                    key = entry.getKey();
                }
            }
            if (key != null) {
                positionMap.put(key, updated);
            }
        }
    }

    @Override
    public V getSelected(Coordinate action) {
        for (Map.Entry<V, Coordinate> entry : positionMap.entrySet()) {
            if (action.intersects(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void setPosition(V vertex, Coordinate position) {
        positionMap.put(vertex, position);
    }
}
