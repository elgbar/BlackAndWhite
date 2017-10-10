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
    public static Orientation pointy =
        new Orientation(Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0, Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0,
                        2.0 / 3.0, 0.5);
    public static Orientation flat =
        new Orientation(3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0), 2.0 / 3.0, 0.0, -1.0 / 3.0,
                        Math.sqrt(3.0) / 3.0, 0.0);

    public static Point hexToPixel(final Layout layout, final Hex h) {
        final Orientation M = layout.orientation;
        final Point size = layout.size;
        final Point origin = layout.origin;
        final double x = (M.f0 * h.q + M.f1 * h.r) * size.x;
        final double y = (M.f2 * h.q + M.f3 * h.r) * size.y;
        return new Point(x + origin.x, y + origin.y);
    }


    public static FractionalHex pixelToHex(final Layout layout, final Point p) {
        final Orientation M = layout.orientation;
        final Point size = layout.size;
        final Point origin = layout.origin;
        final Point pt = new Point((p.x - origin.x) / size.x, (p.y - origin.y) / size.y);
        final double q = M.b0 * pt.x + M.b1 * pt.y;
        final double r = M.b2 * pt.x + M.b3 * pt.y;
        return new FractionalHex(q, r, -q - r);
    }


    public static Point hexCornerOffset(final Layout layout, final int corner) {
        final Point size = layout.size;
        final double angle = 2.0 * Math.PI * (layout.orientation.start_angle - corner) / 6;
        return new Point(size.x * Math.cos(angle), size.y * Math.sin(angle));
    }


    public static ArrayList<Point> polygonCorners(final Layout layout, final Hex h) {
        final ArrayList<Point> corners = new ArrayList<Point>() {{
        }};
        final Point center = Layout.hexToPixel(layout, h);
        for (int i = 0; i < 6; i++) {
            final Point offset = Layout.hexCornerOffset(layout, i);
            corners.add(new Point(center.x + offset.x, center.y + offset.y));
        }
        return corners;
    }

}
