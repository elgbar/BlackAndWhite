package no.kh498.bnw.hexagon.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import no.kh498.bnw.BnW;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author kheba
 */
public class OutlineRenderer implements Disposable {

    private final ShapeRenderer lineRenderer;

    public OutlineRenderer() {
        this.lineRenderer = new ShapeRenderer(1000);
    }

    public void drawGrid() {
        drawGrid(BnW.getGameHandler().getGrid());
    }

    public void drawGrid(final HexagonalGrid<HexagonData> grid) {
        this.lineRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.lineRenderer.setProjectionMatrix(BnW.getCamera().combined);

        final HashMap<Vector2, HashSet<Vector2>> drawnEdges = new HashMap<>();

        for (final Hexagon<HexagonData> hex : HexUtil.getHexagons(grid)) {
            final Vector2[] edges = getEdges(hex);

            this.lineRenderer.setColor(HexUtil.getData(hex).color.inverse());

            for (int i = 0; i < edges.length; i++) {
                final Vector2 edge = edges[i];
                drawnEdges.putIfAbsent(edge, new HashSet<>());
                final HashSet<Vector2> connected = drawnEdges.get(edge);

                //get the next edge this edge is connected to
                final Vector2 edgeTo = edges[(i == edges.length - 1) ? 0 : i + 1];
                if (!connected.contains(edgeTo)) {
                    connected.add(edgeTo);
                    this.lineRenderer.line(edge, edgeTo);
                }
            }
        }

        this.lineRenderer.end();
    }

    public Vector2[] getEdges(final Hexagon<HexagonData> hex) {
        final List<Point> points = hex.getPoints();
        final Vector2[] edges = new Vector2[6];
        for (int i = 0; i < 6; i++) {
            edges[i] = toVector(points.get(i));
        }
        return edges;
    }

    private Vector2 toVector(final Point p) {
        return new Vector2((float) p.getCoordinateX(), (float) p.getCoordinateY());
    }

    public void drawLine(final Vector2 start, final Vector2 end, final Color color) {
        this.lineRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.lineRenderer.setColor(color);
        this.lineRenderer.line(start, end);
        this.lineRenderer.end();
    }

    @Override
    public void dispose() {
        this.lineRenderer.dispose();
    }
}
