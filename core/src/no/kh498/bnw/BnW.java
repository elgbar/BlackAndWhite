package no.kh498.bnw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import no.kh498.bnw.game.HexagonData;
import no.kh498.bnw.game.InputListener;
import no.kh498.bnw.game.Renderer;
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

    private static final int GRID_RADIUS = 9;
    private static final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
    private static final HexagonOrientation ORIENTATION = FLAT_TOP;
    private static final double RADIUS = 30;

    private static HexagonalGrid<HexagonData> grid;
    private static HexagonalGridCalculator<HexagonData> calc;

    private PolygonSpriteBatch polyBatch;
    private OrthographicCamera camera;

    private Renderer renderer;

    public static final HexagonData DEFAULT_DATA = new HexagonData();

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
        builder.getHexagonDataStorage();

        grid = builder.build();

        calc = builder.buildCalculatorFor(grid);

        /* Other */
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.renderer = new Renderer(this.camera);

        this.polyBatch = new PolygonSpriteBatch(); // To assign at the beginning
        this.polyBatch.setProjectionMatrix(this.camera.combined);

        this.font = new BitmapFont();

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

        this.polyBatch.begin();
        this.font.draw(this.polyBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, this.font.getLineHeight());
        this.polyBatch.end();
    }

    @Override
    public void dispose() {
    }
}
