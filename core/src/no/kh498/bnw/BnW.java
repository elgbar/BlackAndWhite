package no.kh498.bnw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import no.kh498.bnw.game.Game;
import no.kh498.bnw.hexagon.HexUtil;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.hexagon.InputListener;
import no.kh498.bnw.hexagon.renderer.OutlineRenderer;
import no.kh498.bnw.hexagon.renderer.VerticesRenderer;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;

public class BnW extends ApplicationAdapter {


    public static boolean printDebug;
    public static boolean printHelp;

    private BitmapFont font;
    private static Game game;

    public static Game getGame() { return game;}


    private static PolygonSpriteBatch polyBatch;
    private static OrthographicCamera camera;

    private VerticesRenderer verticesRenderer;
    private OutlineRenderer outlineRenderer;

    public static void updateResolution(final int width, final int height) {
        camera.setToOrtho(true, width, height);
        polyBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void create() {
        game = new Game();

        /* Other */
        camera = new OrthographicCamera();
        polyBatch = new PolygonSpriteBatch();
        updateResolution(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.verticesRenderer = new VerticesRenderer(camera);
        this.outlineRenderer = new OutlineRenderer(camera);

        this.font = new BitmapFont(true);

        Gdx.input.setInputProcessor(new InputListener());
    }

    @Override
    public void render() {
        final Color color = game.getCurrentPlayer().color.shade(0.75f);
        Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //noinspection unchecked
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons()) {
            final HexagonData data = HexUtil.getData(hexagon);
            data.type.render(this.verticesRenderer, data.color, hexagon);
        }
        this.verticesRenderer.flush();

        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons()) {
            this.outlineRenderer.drawOutline(hexagon);
        }

        String hexInfo = "";
        final Hexagon<HexagonData> hex = HexUtil.getHexagon(Gdx.input.getX(), Gdx.input.getY());
        if (hex != null) {
            final HexagonData data = HexUtil.getData(hex);
            hexInfo = data.color + " level " + data.type.level;
            if (printDebug) {
                final CubeCoordinate coord = hex.getCubeCoordinate();
                hexInfo += " Cube coord: " + coord.getGridX() + ", " + coord.getGridY() + ", " + coord.getGridZ();
            }
        }

        polyBatch.begin();
        this.font.draw(polyBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0,
                       Gdx.graphics.getHeight() - this.font.getLineHeight());
        this.font.draw(polyBatch, "Player: " + game.getCurrentPlayer().color + " Moves left: " +
                                  game.getPlayerHandler().getMovesLeft(), 0,
                       Gdx.graphics.getHeight() - this.font.getLineHeight() * 2);
        this.font.draw(polyBatch, "Hex info: " + hexInfo, 0, Gdx.graphics.getHeight() - this.font.getLineHeight() * 3);

        if (printDebug) {
            this.font
                .draw(polyBatch, "Hex info: " + hexInfo, 0, Gdx.graphics.getHeight() - this.font.getLineHeight() * 3);

        }
        polyBatch.end();
    }

    @Override
    public void dispose() {
    }
}
