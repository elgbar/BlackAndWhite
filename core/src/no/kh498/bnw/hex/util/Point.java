package no.kh498.bnw.hex.util;

/**
 * @author karl henrik
 */
public class Point {


    public final float x;
    public final float y;

    public Point(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public Point(final double x, final double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public static Point abs(final Point p) {
        return new Point(Math.abs(p.x), Math.abs(p.y));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }

        final Point point = (Point) o;

        return Float.compare(point.x, this.x) == 0 && Float.compare(point.y, this.y) == 0;
    }

    @Override
    public int hashCode() {
        int result = (this.x != +0.0f ? Float.floatToIntBits(this.x) : 0);
        result = 31 * result + (this.y != +0.0f ? Float.floatToIntBits(this.y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + this.x + ", y=" + this.y + '}';
    }
}