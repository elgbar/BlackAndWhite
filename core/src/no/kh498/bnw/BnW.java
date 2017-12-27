package no.kh498.bnw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import no.kh498.bnw.game.Game;
import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.Player;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.hexagon.renderer.OutlineRenderer;
import no.kh498.bnw.hexagon.renderer.VerticesRenderer;
import no.kh498.bnw.input.InputListener;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.internal.GridData;

import java.util.Collection;

public class BnW extends ApplicationAdapter {


    public static boolean printDebug = false;
    public static boolean printHelp = true;
    public static boolean gameOver = false;

    private static Game game;

    private static float changedX = 0;
    private static float changedY = 0;

    private static PolygonSpriteBatch polyBatch;
    private static OrthographicCamera camera;
    private BitmapFont font;
    private BitmapFont winnerFont;

    private static VerticesRenderer verticesRenderer;
    private OutlineRenderer outlineRenderer;


    private static final String help =
        "Click a highlighted hexagons to make a move.\n" + "It costs 2 movement points to attack and 1 to reinforce\n" +
        "Press 'e' to end turn\n" + "Press 'n' for next map\n";


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

        game = new Game();

        verticesRenderer = new VerticesRenderer();
        this.outlineRenderer = new OutlineRenderer();

        final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/UbuntuMono-R.ttf"));
        final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.flip = true;
        this.winnerFont = generator.generateFont(parameter);
        generator.dispose();

        this.font = new BitmapFont(true);
    }

    @Override
    public void render() {
        final Color color = game.getCurrentPlayer().color.shade(0.75f);
        Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();

        //check the hexagon in the player's mouse
        StringBuilder hexInfo = new StringBuilder();
        final Collection<Hexagon<HexagonData>> highlighted = game.getPlayerHandler().getHighlighted();

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
                    hexInfo.append(String.format("(%.2f, %.2f) ", point.getCoordinateX(), point.getCoordinateY()));
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
        verticesRenderer.flush();

        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons()) {
            this.outlineRenderer.drawOutline(hexagon);
        }

        final GridData gridData = game.getGrid().getGridData();
        final String gridInfo = "Grid data: dimensions: " + gridData.getGridWidth() + ", " + gridData.getGridHeight() +
                                " connectedHexagons: " + (highlighted == null ? 0 : highlighted.size());

        write(gridInfo, hexInfo.toString());
    }


    private void write(final String gridInfo, final String hexInfo) {
        final int height = Gdx.graphics.getHeight();
        final float lineHeight = this.font.getLineHeight();

        //render the text
        polyBatch.begin();
        this.font.draw(polyBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, height - lineHeight);
        this.font.draw(polyBatch, "Player: " + game.getCurrentPlayer().color + " Moves left: " +
                                  game.getPlayerHandler().getMovesLeft(), 0, height - lineHeight * 2);

        final StringBuilder sb = new StringBuilder("Player hex info: ");
        for (final Player player : game.getPlayerHandler().getPlayers()) {
            sb.append(player.color).append(": ").append(player.getHexagons()).append(" ");
        }


        if (printHelp) {
            this.font.draw(polyBatch, help, 0, height - lineHeight * 6);
        }
        else if (printDebug) {
            this.font
                .draw(polyBatch, "Cursor: " + Gdx.input.getX() + ", " + Gdx.input.getY(), 0, height - lineHeight * 6);
            this.font.draw(polyBatch, sb.toString(), 0, height - lineHeight * 5);
            this.font.draw(polyBatch, gridInfo, 0, height - lineHeight * 4);
            this.font.draw(polyBatch, "Hex info: " + hexInfo, 0, height - lineHeight * 3);
        }

        if (gameOver) {
            final HexColor currCol = game.getCurrentPlayer().color;
            this.winnerFont.setColor(currCol.inverse());
            final String winStr = currCol.toString().toLowerCase() + " won!";
            //center the text
            final float x = (Gdx.graphics.getWidth() / 2) - (winStr.length() * this.winnerFont.getSpaceWidth()) / 2;
            this.winnerFont.draw(polyBatch, winStr, x, Gdx.graphics.getHeight() / 2);
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

    public static Game getGame() { return game;}

    public static OrthographicCamera getCamera() {
        return camera;
    }
}
