package no.kh498.bnw.hex;

/**
 * @author karl henrik
 */
public class OffsetCoord {

    public OffsetCoord(final int col, final int row) {
        this.col = col;
        this.row = row;
    }

    public final int col;
    public final int row;
    public static int EVEN = 1;
    public static int ODD = -1;

    public static OffsetCoord qOffsetFromCube(final int offset, final Hex h) {
        final int col = h.q;
        final int row = h.r + (h.q + offset * (h.q & 1)) / 2;
        return new OffsetCoord(col, row);
    }


    public static Hex qOffsetToCube(final int offset, final OffsetCoord h) {
        final int q = h.col;
        final int r = h.row - (h.col + offset * (h.col & 1)) / 2;
        final int s = -q - r;
        return new Hex(q, r, s);
    }


    public static OffsetCoord rOffsetFromCube(final int offset, final Hex h) {
        final int col = h.q + (h.r + offset * (h.r & 1)) / 2;
        final int row = h.r;
        return new OffsetCoord(col, row);
    }


    public static Hex rOffsetToCube(final int offset, final OffsetCoord h) {
        final int q = h.col - (h.row + offset * (h.row & 1)) / 2;
        final int r = h.row;
        final int s = -q - r;
        return new Hex(q, r, s);
    }

}
