package no.kh498.bnw.game;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;

import java.util.List;

/**
 * @author karl henrik
 */
@SuppressWarnings("GwtInconsistentSerializableClass")
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

    CUBE(new Surface(6, 5, 0, 0.83f),
         new Surface(6, 0, 1, 0.83f),
         new Surface(6, 5, 4, 0.63f),
         new Surface(6, 4, 3, 0.63f),
         new Surface(6, 1, 2, 1f),
         new Surface(6, 3, 2, 1f));
   //@formatter:on

    private final Surface[] surfaces;

    HexType(final Surface... surfaces) {
        this.surfaces = surfaces;
    }


    public void render(final PolygonSpriteBatch batch, final HexColor color, final Hexagon<?> hexagon) {

//        final TextureRegion textureRegion = color.shade(1f);
//        final float[] vertices = toRenderPoints(points);
//        for (int i = 0; i < points.size(); i++) {
//            final Point point = points.get(i);
//            System.out.print("point " + i + " [" + point.getCoordinateX() + ", " + point.getCoordinateY() + "] ");
//        }
//        System.out.println();
//        System.out.println(Arrays.toString(vertices));


        final Point center = Point.fromPosition(hexagon.getCenterX(), hexagon.getCenterY());

        final List<Point> points = hexagon.getPoints();
        points.add(center);

        for (final Surface sur : this.surfaces) {
            sur.render(batch, color, points);
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

        Surface(final int v1, final int v2, final int v3, final float shade) {

            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
            this.shade = shade;
        }

        void appendFloatArray(final Point p, final FloatArray arr) {
            arr.addAll((float) p.getCoordinateX(), (float) p.getCoordinateY());
        }

        void render(final PolygonSpriteBatch batch, final HexColor color, final List<Point> points) {

            final FloatArray floatArray = new FloatArray();

            appendFloatArray(points.get(this.v1), floatArray);
            appendFloatArray(points.get(this.v2), floatArray);
            appendFloatArray(points.get(this.v3), floatArray);

            final float[] vertices = floatArray.toArray();

            final EarClippingTriangulator triangulator = new EarClippingTriangulator();
            final ShortArray triangleIndices = triangulator.computeTriangles(vertices);
            final TextureRegion region = color.shade(this.shade);

            final PolygonRegion polyReg = new PolygonRegion(region, vertices, triangleIndices.toArray());

            new PolygonSprite(polyReg).draw(batch);
        }
    }
}
