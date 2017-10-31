package no.kh498.bnw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import no.kh498.bnw.game.Game;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.game.world.WorldHandler;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.hexagon.InputListener;
import no.kh498.bnw.hexagon.Renderer;
import org.codetome.hexameter.core.api.Hexagon;
import rx.Observable;

public class BnW extends ApplicationAdapter {

    private static BnW instance;
    private BitmapFont font;
    private static Game game;

    public static BnW getInst() {
        return instance;
    }

    public static World getWorld() {
        return worldHandler.getWorld();
    }

    public static WorldHandler getWorldHandler() {
        return worldHandler;
    }

    public static Game getGame() { return game;}

    private static WorldHandler worldHandler;

    private static PolygonSpriteBatch polyBatch;
    private static OrthographicCamera camera;

    private Renderer renderer;

    public static final HexagonData DEFAULT_DATA = new HexagonData();

    public static void updateResolution(final int width, final int height) {
        camera.setToOrtho(true, width, height);
        polyBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void create() {
        instance = this;
        game = new no.kh498.bnw.game.Game();

        worldHandler = new WorldHandler();

        /* Other */
        camera = new OrthographicCamera();
        polyBatch = new PolygonSpriteBatch();
        updateResolution(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.renderer = new Renderer(camera);

        this.font = new BitmapFont(true);

        Gdx.input.setInputProcessor(new InputListener());
    }

    @Override
    public void render() {
        final Color color = game.getCurrentPlayer().color.shade(0.75f);
        Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //noinspection unchecked
        final Observable<Hexagon<HexagonData>> hexagons = worldHandler.getWorld().getGrid().getHexagons();
        hexagons.forEach(hexagon -> {
            final HexagonData data = HexagonData.getData(hexagon);
            data.type.render(this.renderer, data.color, hexagon);
        });
        this.renderer.flush();

        String hexInfo = "";
        final Hexagon<HexagonData> hex = HexagonData.getHexagon(Gdx.input.getX(), Gdx.input.getY());
        if (hex != null) {
            final HexagonData data = HexagonData.getData(hex);
            hexInfo = "Player: " + data.color + " Level: " + data.type.level;
        }

        polyBatch.begin();
        this.font.draw(polyBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0,
                       Gdx.graphics.getHeight() - this.font.getLineHeight());
        this.font.draw(polyBatch, "Player: " + game.getCurrentPlayer().color + " Moves left: " +
                                  game.getPlayerHandler().getMovesLeft(), 0,
                       Gdx.graphics.getHeight() - this.font.getLineHeight() * 2);
        this.font.draw(polyBatch, "Hex info: " + hexInfo, 0, Gdx.graphics.getHeight() - this.font.getLineHeight() * 3);
        polyBatch.end();
    }

    @Override
    public void dispose() {
    }
}
