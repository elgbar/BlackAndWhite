package no.kh498.bnw.hexagon.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import no.kh498.bnw.BnW;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;

import java.util.List;

/**
 * @author kheba
 */
public class OutlineRenderer {

    private final ShapeRenderer lineRenderer;

    public OutlineRenderer() {
        this.lineRenderer = new ShapeRenderer();
    }

    public void drawOutline(final Hexagon<HexagonData> hex) {
        final List<Point> points = hex.getPoints();
        final Color inverse = HexUtil.getData(hex).color.inverse();
        for (int i = 1; i < 6; i++) {
            final Vector2 start = toVector(points.get(i - 1));
            final Vector2 end = toVector(points.get(i));
            drawLine(start, end, inverse);
        }

        //the last line is between point 5 and 0
        final Vector2 start = toVector(points.get(0));
        final Vector2 end = toVector(points.get(5));
        drawLine(start, end, inverse);
    }

    private Vector2 toVector(final Point p) {
        return new Vector2((float) p.getCoordinateX(), (float) p.getCoordinateY());
    }

    private void drawLine(final Vector2 start, final Vector2 end, final Color color) {
//        Gdx.gl.glLineWidth(2.0F);
        this.lineRenderer.setProjectionMatrix(BnW.getCamera().combined);
        this.lineRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.lineRenderer.setColor(color);
        this.lineRenderer.line(start, end);
        this.lineRenderer.end();
//        Gdx.gl.glLineWidth(1.0F);
    }
}
