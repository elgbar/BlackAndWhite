package no.kh498.bnw.game;

import com.badlogic.gdx.utils.FloatArray;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;

import java.util.List;
import java.util.WeakHashMap;

/**
 * @author karl henrik
 */
@SuppressWarnings({"GwtInconsistentSerializableClass", "NonJREEmulationClassesInClientCode"})
public enum HexType {

    //@formatter:off
    FLAT(new Surface(0, 1, 2, 1f),
         new Surface(0, 3, 2, 1f),
         new Surface(0, 3, 5, 1f),
         new Surface(5, 3, 4, 1f)),

    DIAMOND(new Surface(6, 5, 0, 0.63f),
            new Surface(6, 5, 4, 0.73f),
            new Surface(6, 0, 1, 0.73f),
            new Surface(6, 1, 2, 0.93f),
            new Surface(6, 4, 3, 0.93f),
            new Surface(6, 3, 2, 1f)),

    HALF(new Surface(6, 5, 0, 0.83f),
         new Surface(6, 0, 1, 0.83f),
         new Surface(6, 5, 4, 0.83f),
         new Surface(6, 4, 3, 1f),
         new Surface(6, 1, 2, 1f),
         new Surface(6, 3, 2, 1f)),

    CUBE(new Surface(6, 5, 0, 0.63f),
         new Surface(6, 0, 1, 0.63f),
         new Surface(6, 5, 4, 0.83f),
         new Surface(6, 4, 3, 0.83f),
         new Surface(6, 1, 2, 1f),
         new Surface(6, 3, 2, 1f));
   //@formatter:on

    private final Surface[] surfaces;

    HexType(final Surface... surfaces) {
        this.surfaces = surfaces;
    }

    WeakHashMap<Hexagon<HexagonData>, List<Point>> cache = new WeakHashMap<>();

    public void render(final Renderer renderer, final HexColor color, final Hexagon<HexagonData> hexagon) {
        final List<Point> points;
        if (!this.cache.containsKey(hexagon)) {
            final Point center = Point.fromPosition(hexagon.getCenterX(), hexagon.getCenterY());
            points = hexagon.getPoints();
            points.add(center);
            this.cache.put(hexagon, points);
        }
        else {
            points = this.cache.get(hexagon);
        }

        for (final Surface sur : this.surfaces) {
            sur.getVerities(renderer, color, points);
        }
    }


    float[] toRenderPoints(final List<Point> points) {
        final float[] fpoints = new float[points.size() * 2];
        for (int i = 0; i < points.size(); i++) {
            final Point point = points.get(i);
            fpoints[i * 2] = (float) point.getCoordinateX();
            fpoints[i * 2 + 1] = (float) point.getCoordinateY();
        }
        return fpoints;
    }

    private static final class Surface {

        private final int v1;
        private final int v2;
        private final int v3;
        private final float shade;

        /**
         * @param v1
         *     The first of the index vertex in the list
         * @param v2
         *     The second of the index vertex in the list
         * @param v3
         *     The third of the index vertex in the list
         * @param shade
         *     How light the shade of the color should be, 1 is max 0 is min
         */
        Surface(final int v1, final int v2, final int v3, final float shade) {

            if (shade < 0 || shade > 1) {
                throw new IllegalArgumentException("The shade must be between 1 and 0");
            }
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
            this.shade = shade;
        }

        void appendFloatArray(final Point p, final FloatArray arr) {
            arr.addAll((float) p.getCoordinateX(), (float) p.getCoordinateY());
        }

        void getVerities(final Renderer renderer, final HexColor color, final List<Point> points) {

            final FloatArray floatArray = new FloatArray();

            appendFloatArray(points.get(this.v1), floatArray);
            appendFloatArray(points.get(this.v2), floatArray);
            appendFloatArray(points.get(this.v3), floatArray);

            final float[] vertices = floatArray.toArray();

            renderer.drawTriangle(color.shade(this.shade).toFloatBits(), vertices);
        }
    }
}
