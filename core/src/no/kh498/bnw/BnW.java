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
import no.kh498.bnw.hexagon.renderer.OutlineRenderer;
import no.kh498.bnw.hexagon.renderer.VerticesRenderer;
import no.kh498.bnw.input.InputListener;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.internal.GridData;

import java.util.Collection;

public class BnW extends ApplicationAdapter {


    public static boolean printDebug = false;
    public static boolean printHelp = true;

    private BitmapFont font;
    private static Game game;
    private static InputListener inputListener;

    public static Game getGame() { return game;}

    public static OrthographicCamera getCamera() {
        return camera;
    }

    public static InputListener getInputListener() {
        return inputListener;
    }

    private static PolygonSpriteBatch polyBatch;
    private static OrthographicCamera camera;

    private static VerticesRenderer verticesRenderer;
    private OutlineRenderer outlineRenderer;


    private static final String help =
        "Click a highlighted hexagons to make a move.\n" + "It costs 2 movement points to attack and 1 to reinforce\n" +
        "Press 'e' to end turn\n" + "Press 'n' for next map\n";

    public static void updateResolution(final int width, final int height) {
        camera.setToOrtho(true, width, height);
        polyBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void create() {
        inputListener = new InputListener();
        Gdx.input.setInputProcessor(inputListener);

        /* Other */
        camera = new OrthographicCamera();
        polyBatch = new PolygonSpriteBatch();
        updateResolution(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game = new Game();

        verticesRenderer = new VerticesRenderer();
        this.outlineRenderer = new OutlineRenderer();

        this.font = new BitmapFont(true);
    }

    @Override
    public void render() {
        final Color color = game.getCurrentPlayer().color.shade(0.75f);
        Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();

        //check the hexagon in the player's mouse
        String hexInfo = "";
        final Collection<Hexagon<HexagonData>> highlighted = game.getPlayerHandler().getHighlighted();

        final Hexagon<HexagonData> currHex = HexUtil.getCursorHexagon();
//        HashSet<Hexagon<HexagonData>> highlighted = new HashSet<>();


        if (currHex != null) {
            final HexagonData data = HexUtil.getData(currHex);
            hexInfo = data.color + " level " + data.type.level;
            if (printDebug) {
                final CubeCoordinate coord = currHex.getCubeCoordinate();
                hexInfo += " Cube coord: " + coord.getGridX() + ", " + coord.getGridY() + ", " + coord.getGridZ();
            }
        }

        //Render the hexagons
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons()) {
            final HexagonData data = HexUtil.getData(hexagon);
            data.brightness =
                highlighted.contains(hexagon) && data.type.canChange() ? HexagonData.BRIGHT : HexagonData.DIM;

            if (hexagon.equals(currHex)) {
                data.brightness += HexagonData.SELECTED;
            }

            data.type.render(verticesRenderer, data.color, data.brightness, hexagon);
        }
        verticesRenderer.flush();

        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons()) {
            this.outlineRenderer.drawOutline(hexagon);
        }

        final GridData gridData = game.getGrid().getGridData();
        final String gridInfo = "Grid data: dimensions: " + gridData.getGridWidth() + ", " + gridData.getGridHeight() +
                                " connectedHexagons: " + (highlighted == null ? 0 : highlighted.size());

        write(gridInfo, hexInfo);
    }

    private void write(final String gridInfo, final String hexInfo) {
        final int height = Gdx.graphics.getHeight();
        final float lineHeight = this.font.getLineHeight();

        //render the text
        polyBatch.begin();
        this.font.draw(polyBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, height - lineHeight);
        this.font.draw(polyBatch, "Player: " + game.getCurrentPlayer().color + " Moves left: " +
                                  game.getPlayerHandler().getMovesLeft(), 0, height - lineHeight * 2);

        if (printHelp) {
            this.font.draw(polyBatch, help, 0, height - lineHeight * 6);
        }
        else if (printDebug) {
            this.font.draw(polyBatch, gridInfo, 0, height - lineHeight * 4);
            this.font.draw(polyBatch, "Hex info: " + hexInfo, 0, height - lineHeight * 3);

        }
        polyBatch.end();
    }

    @Override
    public void dispose() {
    }

    public static void flush() {
        verticesRenderer.flush();
    }
}
