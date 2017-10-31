package no.kh498.bnw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import no.kh498.bnw.game.*;
import org.codetome.hexameter.core.api.*;
import rx.Observable;

import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;

public class BnW extends ApplicationAdapter {

    private static BnW instance;
    private BitmapFont font;

    public static BnW getInst() {
        return instance;
    }

    public static HexagonalGrid getGrid() {
        return grid;
    }

    public static HexagonalGridCalculator getCalc() {
        return calc;
    }

    private static final int GRID_RADIUS = 1;
    private static final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
    private static final HexagonOrientation ORIENTATION = FLAT_TOP;
    private static final double RADIUS = 400;

    private static HexagonalGrid<HexagonData> grid;
    private static HexagonalGridCalculator<HexagonData> calc;

    private static PolygonSpriteBatch polyBatch;
    private static OrthographicCamera camera;

    private Renderer renderer;

    //TODO remove, only used for testing
    public static HexType type = HexType.CUBE;
    public static HexColor color = HexColor.WHITE;

    public static final HexagonData DEFAULT_DATA = new HexagonData();

    public static void updateResolution(final int width, final int height) {
        camera.setToOrtho(true, width, height);
        polyBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void create() {
        instance = this;

        /* Hexagon */
        final HexagonalGridBuilder<HexagonData> builder = new HexagonalGridBuilder<>();
        builder.setGridHeight(GRID_RADIUS);
        builder.setGridWidth(GRID_RADIUS);
        builder.setGridLayout(GRID_LAYOUT);
        builder.setOrientation(ORIENTATION);
        builder.setRadius(RADIUS);

        grid = builder.build();

        //noinspection unchecked
        calc = builder.buildCalculatorFor(grid);

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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //noinspection unchecked
        final Observable<Hexagon<HexagonData>> hexagons = grid.getHexagons();
        hexagons.forEach(hexagon -> {
            final HexagonData data = hexagon.getSatelliteData().orElse(DEFAULT_DATA);
            data.type.render(this.renderer, data.color, hexagon);
        });
        this.renderer.flush();

        polyBatch.begin();
        this.font.draw(polyBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0,
                       Gdx.graphics.getHeight() - this.font.getLineHeight());
        this.font.draw(polyBatch, "Type: " + type.name() + " Color: " + color.name(), 0,
                       Gdx.graphics.getHeight() - this.font.getLineHeight() * 2);
        this.font.draw(polyBatch, "Res: " + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight(), 0,
                       Gdx.graphics.getHeight() - this.font.getLineHeight() * 3);
        polyBatch.end();
    }

    @Override
    public void dispose() {
    }
}
