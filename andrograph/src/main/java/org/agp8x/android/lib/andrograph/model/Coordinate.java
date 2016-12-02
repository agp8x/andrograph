package org.agp8x.android.lib.andrograph.model;

/**
 * {@link Coordinate}s store a position in relative order (0-1.0)
 *
 * @author  clemensk
 *
 * 30.11.16.
 */

public class Coordinate {
    private double x;
    private double y;

    public Coordinate() {
        x = 0;
        y = 0;
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean intersects(Coordinate other) {
        if (this.equals(other)) {
            return true;
        }
        double distance = Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2));
        return distance < 0.15;//TODO: validate; MAGIC NUMBER ALERT!
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (Double.compare(that.x, x) != 0) return false;
        return Double.compare(that.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
