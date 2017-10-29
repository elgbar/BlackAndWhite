package no.kh498.bnw.hex;

import java.util.ArrayList;

/**
 * @author karl henrik
 */
public class Hex {

    public Hex(final int q, final int r) {
        this(q, r, -q - r);
    }

    public Hex(final int q, final int r, final int s) {
        if (q + r + s != 0) {
            throw new IllegalArgumentException("q + r + s != 0");
        }
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public final int q;
    public final int r;
    public final int s;

    public static Hex add(final Hex a, final Hex b) {
        return new Hex(a.q + b.q, a.r + b.r, a.s + b.s);
    }


    public static Hex subtract(final Hex a, final Hex b) {
        return new Hex(a.q - b.q, a.r - b.r, a.s - b.s);
    }


    public static Hex scale(final Hex a, final int k) {
        return new Hex(a.q * k, a.r * k, a.s * k);
    }


    public static Hex rotateLeft(final Hex a) {
        return new Hex(-a.s, -a.q, -a.r);
    }


    public static Hex rotateRight(final Hex a) {
        return new Hex(-a.r, -a.s, -a.q);
    }

    public static ArrayList<Hex> directions = new ArrayList<Hex>() {{
        add(new Hex(1, 0, -1));
        add(new Hex(1, -1, 0));
        add(new Hex(0, -1, 1));
        add(new Hex(-1, 0, 1));
        add(new Hex(-1, 1, 0));
        add(new Hex(0, 1, -1));
    }};

    public static Hex direction(final int direction) {
        return Hex.directions.get(direction);
    }


    public static Hex neighbor(final Hex hex, final int direction) {
        return Hex.add(hex, Hex.direction(direction));
    }

    public static ArrayList<Hex> diagonals = new ArrayList<Hex>() {{
        add(new Hex(2, -1, -1));
        add(new Hex(1, -2, 1));
        add(new Hex(-1, -1, 2));
        add(new Hex(-2, 1, 1));
        add(new Hex(-1, 2, -1));
        add(new Hex(1, 1, -2));
    }};

    public static Hex diagonalNeighbor(final Hex hex, final int direction) {
        return Hex.add(hex, Hex.diagonals.get(direction));
    }


    public static int length(final Hex hex) {
        return (Math.abs(hex.q) + Math.abs(hex.r) + Math.abs(hex.s)) / 2;
    }


    public static int distance(final Hex a, final Hex b) {
        return Hex.length(Hex.subtract(a, b));
    }

}

