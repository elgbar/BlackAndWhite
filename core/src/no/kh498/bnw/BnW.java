package no.kh498.bnw;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import no.kh498.bnw.game.GameHandler;
import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.Player;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.hexagon.renderer.OutlineRenderer;
import no.kh498.bnw.hexagon.renderer.VerticesRenderer;
import no.kh498.bnw.input.InputListener;
import no.kh498.bnw.util.HexUtil;
import no.kh498.bnw.util.StringUtil;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.internal.GridData;

import java.util.Collection;

public class BnW extends Game {


    public static boolean printDebug = false;
    public static boolean printHelp = false;
    public static boolean gameOver = false;
    public static boolean printControls = false;

    private static GameHandler gameHandler;

    private static float changedX = 0;
    private static float changedY = 0;

    private static PolygonSpriteBatch polyBatch;
    private static OrthographicCamera camera;

    private BitmapFont font;
    private Color defaultColor;

    private VerticesRenderer verticesRenderer;
    private OutlineRenderer outlineRenderer;


    //@formatter:off
    private static final int HELP_LINES = 2;
    private static final String HELP =
        "Click a highlighted hexagons to make a move.\n" +
        "It costs 2 movement points to attack and 1 to reinforce\n" ;

    private static final int CONTROLS_LINES = 9;
    private static final String CONTROLS =
        "Press 'E' to end the level.\n" +
        "Press 'R' to reset the level.\n" +
        "Press 'F' to toggle fullscreen.\n" +
        "Press 'N' to switch level.\n" +
        "Press 'F1' to print help.\n" +
        "Press 'F2' to print the controls.\n" +
        "Press 'F3' to print debug information.\n" +
        "Move mouse while clicking to move world around.\n" +
        "Scroll is the same as clicking.\n";
    //@formatter:on

    public static void updateResolution(final int width, final int height) {
        camera.setToOrtho(true, width, height);
        polyBatch.setProjectionMatrix(camera.combined);
        changedX = changedY = 0;
    }


    @Override
    public void create() {
        Gdx.input.setInputProcessor(new InputListener());

        /* Other */
        camera = new OrthographicCamera();
        polyBatch = new PolygonSpriteBatch();
        updateResolution(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        gameHandler = new GameHandler();

        verticesRenderer = new VerticesRenderer();

        this.outlineRenderer = new OutlineRenderer();

        final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/UbuntuMono-R.ttf"));
        final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.flip = true;
        this.font = generator.generateFont(parameter);
        this.defaultColor = this.font.getColor().cpy();
        generator.dispose();

    }

    @Override
    public void render() {
        final Color color = gameHandler.getCurrentPlayer().color.shade(0.75f);
        Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();

        //check the hexagon in the player's mouse
        StringBuilder hexInfo = new StringBuilder();
        final Collection<Hexagon<HexagonData>> highlighted = gameHandler.getPlayerHandler().getHighlighted();

        final Hexagon<HexagonData> currHex = HexUtil.getCursorHexagon();
//        HashSet<Hexagon<HexagonData>> highlighted = new HashSet<>();


        if (currHex != null) {
            final HexagonData data = HexUtil.getData(currHex);
            hexInfo = new StringBuilder(data.color + ", level: " + data.type.level);
            if (printDebug) {
                final CubeCoordinate coord = currHex.getCubeCoordinate();
                hexInfo.append(", Cube coord: ").append(coord.getGridX()).append(", ").append(coord.getGridY())
                       .append(", ").append(coord.getGridZ());
                hexInfo.append(", pixel coord points: ");
                for (final Point point : currHex.getPoints()) {
                    //noinspection NonJREEmulationClassesInClientCode
                    hexInfo.append(String.format("(%.2f | %.2f) ", point.getCoordinateX(), point.getCoordinateY()));
                }

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

        //fake cursor
//        final float cursorX = (Gdx.input.getX() + changedX) /
//                              (camera.combined.getScaleX() * camera.viewportWidth / 2);//Gdx.input.getX() + changedX;
//        final float cursorY = (Gdx.input.getY() + changedY) /
//                              (camera.combined.getScaleY() * camera.viewportHeight / 2);//Gdx.input.getY() + changedY;
//        final float[] vertices = {cursorX, cursorY, //
//                                  cursorX - 5, cursorY + 5, //
//                                  cursorX + 5, cursorY + 5
//        };
//        verticesRenderer.drawTriangle(Color.RED.toFloatBits(), vertices);


        verticesRenderer.flush();

        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons()) {
            this.outlineRenderer.drawOutline(hexagon);
        }

        final GridData gridData = gameHandler.getGrid().getGridData();
        final String gridInfo = "Grid data: dimensions: " + gridData.getGridWidth() + ", " + gridData.getGridHeight() +
                                " connectedHexagons: " + (highlighted == null ? 0 : highlighted.size());

        write(gridInfo, hexInfo.toString());
    }


    private void write(final String gridInfo, final String hexInfo) {
        final int height = Gdx.graphics.getHeight();
        final float lineHeight = this.font.getLineHeight();

        this.font.setColor(this.defaultColor);

        //render the text
        polyBatch.begin();
        this.font.draw(polyBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, height - lineHeight);
        this.font.draw(polyBatch, "Moves left: " + gameHandler.getPlayerHandler().getMovesLeft(), 0,
                       height - lineHeight * 2);
        this.font.draw(polyBatch, "Press 'F2' to print the controls", 0, height - lineHeight * 4);

        final int minPrintLevel = 5;

        final StringBuilder sb = new StringBuilder("Player hex amount: ");
        for (final Player player : gameHandler.getPlayerHandler().getPlayers()) {
            sb.append(player.color).append(": ").append(player.getHexagons()).append(" ");
        }


        if (printHelp) {
            this.font.draw(polyBatch, HELP, 0, height - lineHeight * (minPrintLevel + HELP_LINES));
        }

        if (printControls) {
            this.font.draw(polyBatch, CONTROLS, 0,
                           height - lineHeight * (minPrintLevel + (printHelp ? HELP_LINES + 1 : 0) + CONTROLS_LINES));
        }

        if (printDebug) {
            //this must be the lowest
            this.font.draw(polyBatch, "Hex info: " + hexInfo, 0, lineHeight * 4, Gdx.graphics.getWidth(),
                           Gdx.graphics.getWidth() / 2, true);
            this.font.draw(polyBatch, sb.toString(), 0, lineHeight * 3);
            this.font.draw(polyBatch, gridInfo, 0, lineHeight * 2);
            this.font.draw(polyBatch, "Cursor: " + Gdx.input.getX() + ", " + Gdx.input.getY(), 0, lineHeight);
        }

        if (gameOver) {
            final HexColor currCol = gameHandler.getCurrentPlayer().color;
            this.font.setColor(currCol.inverse());
            final String winStr = StringUtil.toTitleCase(currCol.toString()) + " Won!";
            //center the text
            final float x = (Gdx.graphics.getWidth() / 2) - (winStr.length() * this.font.getSpaceWidth()) / 2;
            this.font.draw(polyBatch, winStr, x, Gdx.graphics.getHeight() / 2);
            this.font.setColor(this.defaultColor);
        }

        polyBatch.end();
    }

    @Override
    public void dispose() {
    }

    public static void flush() {
        verticesRenderer.flush();
    }

    public static void moveCamera(final float deltaX, final float deltaY) {
        changedX += deltaX;
        changedY += deltaY;

        camera.translate(deltaX, deltaY);
    }

    public static float getChangedX() {
        return changedX;
    }

    public static float getChangedY() {
        return changedY;
    }

    public static GameHandler getGameHandler() { return gameHandler;}

    public static OrthographicCamera getCamera() {
        return camera;
    }
}
