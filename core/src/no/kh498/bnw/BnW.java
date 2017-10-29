package no.kh498.bnw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.kh498.bnw.game.Board;
import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;

public class BnW extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture img;
    public Board board;

    private static BnW instance;

    public static BnW getInst() {
        return instance;
    }

    @Override
    public void create() {
        instance = this;
        this.batch = new SpriteBatch();
        this.img = HexType.JEWEL.getTexture();
        this.board = new Board(32, 5, 5);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batch.begin();
        this.board.render(this.batch);

//        this.batch.setColor(HexColor.WHITE.getShade());
//        this.batch.draw(this.img, 0, 0);
//        this.batch.setColor(HexColor.GRAY.getShade());
//        this.batch.draw(this.img, 128, 0);
//        this.batch.setColor(HexColor.BLACK.getShade());
//        this.batch.draw(this.img, 256, 0);
        this.batch.end();
        this.batch.setColor(HexColor.RESET.getShade());
    }


    @Override
    public void dispose() {
        this.batch.dispose();
        this.img.dispose();
    }
}
