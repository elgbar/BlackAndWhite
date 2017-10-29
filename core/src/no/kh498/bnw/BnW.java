package no.kh498.bnw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import org.codetome.hexameter.core.api.*;
import rx.Observable;

import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;

public class BnW extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture img;

    private static BnW instance;

    public static BnW getInst() {
        return instance;
    }

    private static final int GRID_HEIGHT = 5;
    private static final int GRID_WIDTH = 5;
    private static final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
    private static final HexagonOrientation ORIENTATION = FLAT_TOP;
    private static final double RADIUS = 50;

    private HexagonalGrid grid;
    private HexagonalGridCalculator calc;

    private PolygonSprite polySprite;
    private PolygonSpriteBatch polyBatch;
    private OrthographicCamera camera;
    private Vector2 touch;

    @Override
    public void create() {
        instance = this;
//        this.batch = new SpriteBatch();
//        this.img = HexType.HALF.getTexture();

        // ...
        final HexagonalGridBuilder builder =
            new HexagonalGridBuilder().setGridHeight(GRID_HEIGHT).setGridWidth(GRID_WIDTH).setGridLayout(GRID_LAYOUT)
                                      .setOrientation(ORIENTATION).setRadius(RADIUS);

        this.grid = builder.build();
        this.calc = builder.buildCalculatorFor(this.grid);


        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.touch = new Vector2();
//        Gdx.input.setInputProcessor(new GestureDetector(this));

        this.polyBatch = new PolygonSpriteBatch(); // To assign at the beginning

        this.polyBatch.setProjectionMatrix(this.camera.combined);


//        final float[] vertices = new float[] {10, 10, 100, 10, 200, 200, 10, 100};


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        this.polyBatch.begin();

        final Observable<Hexagon<?>> hexagons = this.grid.getHexagons();
        hexagons.forEach(hexagon -> {
            HexType.HALF.render(this.polyBatch, HexColor.GRAY, hexagon);

//            this.polySprite.draw(this.polyBatch);
        });


        this.polyBatch.end();

//        this.shapeRenderer.begin();


//        this.grid.ge this.board.render(this.batch);

//        this.batch.setColor(HexColor.WHITE.getShade());
//        this.batch.draw(this.img, 0, 0);
//        this.batch.setColor(HexColor.GRAY.getShade());
//        this.batch.draw(this.img, 128, 0);
//        this.batch.setColor(HexColor.BLACK.getShade());
//        this.batch.draw(this.img, 256, 0);
//        this.shapeRenderer.end();
//        this.batch.setColor(HexColor.RESET.getShade());
    }


    @Override
    public void dispose() {
        this.batch.dispose();
        this.img.dispose();
    }
}
