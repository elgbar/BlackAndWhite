package no.kh498.bnw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import no.kh498.bnw.game.HexagonData;
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

    private static final int GRID_RADIUS = 9;
    private static final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
    private static final HexagonOrientation ORIENTATION = FLAT_TOP;
    private static final double RADIUS = 30;

    private HexagonalGrid grid;
    private HexagonalGridCalculator calc;

    private PolygonSprite polySprite;
    private PolygonSpriteBatch polyBatch;
    private OrthographicCamera camera;

    private Renderer renderer;

    private static final HexagonData DEFAULT_DATA = new HexagonData();

    @Override
    public void create() {
        instance = this;

        /* Hexagon */
        final HexagonalGridBuilder builder =
            new HexagonalGridBuilder().setGridHeight(GRID_RADIUS).setGridWidth(GRID_RADIUS).setGridLayout(GRID_LAYOUT)
                                      .setOrientation(ORIENTATION).setRadius(RADIUS);

        this.grid = builder.build();
        this.calc = builder.buildCalculatorFor(this.grid);

//        final Observable<Hexagon<HexagonData>> hexagons = this.grid.getHexagons();
//        hexagons.forEach(hexagon -> {
//            hexagon.setSatelliteData(new HexagonData());
//        });


        /* Other */


        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.renderer = new Renderer(this.camera);

        this.polyBatch = new PolygonSpriteBatch(); // To assign at the beginning
        this.polyBatch.setProjectionMatrix(this.camera.combined);

        this.font = new BitmapFont();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //noinspection unchecked
        final Observable<Hexagon<HexagonData>> hexagons = this.grid.getHexagons();
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
