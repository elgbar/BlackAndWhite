package no.kh498.bnw.hex;

import no.kh498.bnw.hex.util.Point;

import java.util.ArrayList;

/**
 * @author karl henrik
 */
public class Layout {

    public Layout(final Orientation orientation, final Point size, final Point origin) {
        this.orientation = orientation;
        this.size = size;
        this.origin = origin;
    }

    public final Orientation orientation;
    public final Point size;
    public final Point origin;

    public final static Orientation POINTY =
        new Orientation(Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0, Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0,
                        2.0 / 3.0, 0.5);
    public final static Orientation FLAT =
        new Orientation(3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0), 2.0 / 3.0, 0.0, -1.0 / 3.0,
                        Math.sqrt(3.0) / 3.0, 0.0);

    /**
     * @param layout
     *     The layout used
     * @param hex
     *     The hex to get the center from
     *
     * @return The pixel point of the hexagon {@code test}
     */
    public static Point hexToPixel(final Layout layout, final Hex hex) {
        final Orientation M = layout.orientation;
        final Point size = layout.size;
        final Point origin = layout.origin;
        final float x = (M.f0 * hex.q + M.f1 * hex.r) * size.x;
        final float y = (M.f2 * hex.q + M.f3 * hex.r) * size.y;
        return new Point(x + origin.x, y + origin.y);
    }


    /**
     * @param layout
     *     The layout used
     * @param p
     *     The screen location to convert
     *
     * @return The Hexagon at the player's position.
     */
    public static FractionalHex pixelToFracHex(final Layout layout, final Point p) {
        final Orientation M = layout.orientation;
        final Point size = layout.size;
        final Point origin = layout.origin;
        final Point pt = new Point((p.x - origin.x) / size.x, (p.y - origin.y) / size.y);
        final double q = M.b0 * pt.x + M.b1 * pt.y;
        final double r = M.b2 * pt.x + M.b3 * pt.y;
        return new FractionalHex(q, r, -q - r);
    }

    /**
     * @param layout
     *     The layout used
     * @param p
     *     The screen location to convert
     *
     * @return The Hexagon at the player's position.
     */
    public static Hex pixelToHex(final Layout layout, final Point p) {
        final Orientation M = layout.orientation;
        final Point size = layout.size;
        final Point origin = layout.origin;
        final Point pt = new Point((p.x - origin.x) / size.x, (p.y - origin.y) / size.y);
        final double q = M.b0 * pt.x + M.b1 * pt.y;
        final double r = M.b2 * pt.x + M.b3 * pt.y;
        return FractionalHex.hexRound(new FractionalHex(q, r, -q - r));
    }


    public static Point hexCornerOffset(final Layout layout, final int corner) {
        final Point size = layout.size;
        final double angle = 2.0 * Math.PI * (layout.orientation.start_angle - corner) / 6;
        return new Point(size.x * Math.cos(angle), size.y * Math.sin(angle));
    }


    public static ArrayList<Point> polygonCorners(final Layout layout, final Hex h) {
        final ArrayList<Point> corners = new ArrayList<>();
        final Point center = Layout.hexToPixel(layout, h);
        for (int i = 0; i < 6; i++) {
            final Point offset = Layout.hexCornerOffset(layout, i);
            corners.add(new Point(center.x + offset.x, center.y + offset.y));
        }
        return corners;
    }

    public static Point hexCenterFromPoint(final Layout layout, final Point point) {
        return Layout.hexToPixel(layout, Layout.pixelToHex(layout, point));
    }
}
