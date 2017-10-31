package no.kh498.bnw.game;

import com.badlogic.gdx.utils.FloatArray;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;

import java.util.List;


//   2-----1
//  /       \
// 3    6    0
//  \       /
//   4-----5

/**
 * @author karl henrik
 */
@SuppressWarnings({"GwtInconsistentSerializableClass", "NonJREEmulationClassesInClientCode"})
public enum HexType {

    //@formatter:off
    FLAT(new Surface(0, 1, 5, 1.00f),    //   2-----1
         new Surface(1, 4, 5, 1.00f),    //  /|   / |\
         new Surface(1, 2, 4, 1.00f),    // 3 |  /  | 0
         new Surface(2, 3, 4, 1.00f)),   //  \| /   |/
                                                          //   4-----5

    DIAMOND(new Surface(6, 5, 0, 0.63f), //   2-----1
            new Surface(6, 5, 4, 0.73f), //  /  \ /  \
            new Surface(6, 0, 1, 0.73f), // 3----6----0
            new Surface(6, 1, 2, 0.93f), //  \  / \  /
            new Surface(6, 4, 3, 0.93f), //   4-----5
            new Surface(6, 3, 2, 1f)),

    HALF(new Surface(0, 1, 5, 0.78f),    //   2-----1
         new Surface(1, 4, 5, 0.78f),    //  /|   / |\
         new Surface(1, 2, 4, 1.00f),    // 3 |  /  | 0
         new Surface(2, 3, 4, 1.00f)),   //  \| /   |/
                                                          //   4-----5

    CUBE(new Surface(6, 5, 0, 0.68f),    //   2-----1
         new Surface(6, 0, 1, 0.68f),    //  /  \ /  \
         new Surface(6, 5, 4, 0.83f),    // 3----6----0
         new Surface(6, 4, 3, 0.83f),    //  \  / \  /
         new Surface(6, 1, 2, 1.00f),    //   4-----5
         new Surface(6, 3, 2, 1.00f)),

    JEWEL(new Surface(0, 1, 2, 0.63f),   //   2-----1
          new Surface(0, 2, 4, 0.73f),   //  /|‾-__  \
          new Surface(0, 4, 5, 0.83f),   // 3 |    ‾--0
          new Surface(2, 3, 4, 1.00f)),  //  \| _--‾ /
                                                          //   4‾----5
     ASYMMETRICAL(new Surface(0, 4, 5, 0.63f),  //   2-----1
                  new Surface(0, 3, 4, 0.73f),  //  / ‾-__  \
                  new Surface(0, 1, 2, 0.83f),  // 3------‾--0
                  new Surface(0, 2, 3, 1.00f)); //  \  _--‾ /
                                                                 //   4‾----5
   //@formatter:on

    private final Surface[] surfaces;

    HexType(final Surface... surfaces) {
        this.surfaces = surfaces;
    }

    public void render(final Renderer renderer, final HexColor color, final Hexagon<HexagonData> hexagon) {
        final List<Point> points;
        final Point center = Point.fromPosition(hexagon.getCenterX(), hexagon.getCenterY());
        points = hexagon.getPoints();
        points.add(center);

        for (final Surface sur : this.surfaces) {
            sur.render(renderer, color, points);
        }
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

        void printPoint(final List<Point> points) {
            for (final Point p : points) {
                System.out.print(p.getCoordinateX() + ", " + p.getCoordinateY() + " | ");
            }
            System.out.println();
        }

        private static final float[] vertices = new float[6];

        void render(final Renderer renderer, final HexColor color, final List<Point> points) {

            final Point p1 = points.get(this.v1);
            vertices[0] = (float) p1.getCoordinateX();
            vertices[1] = (float) p1.getCoordinateY();

            final Point p2 = points.get(this.v2);
            vertices[2] = (float) p2.getCoordinateX();
            vertices[3] = (float) p2.getCoordinateY();

            final Point p3 = points.get(this.v3);
            vertices[4] = (float) p3.getCoordinateX();
            vertices[5] = (float) p3.getCoordinateY();

            renderer.drawTriangle(color.shade(this.shade).toFloatBits(), vertices);
        }
    }
}
