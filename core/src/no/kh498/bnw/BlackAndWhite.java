package no.kh498.bnw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlackAndWhite extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.img = HexType.JEWEL.getTexture();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batch.begin();
        this.batch.setColor(HexColor.WHITE.getShade());
        this.batch.draw(this.img, 0, 0);
        this.batch.setColor(HexColor.GRAY.getShade());
        this.batch.draw(this.img, 128, 0);
        this.batch.setColor(HexColor.BLACK.getShade());
        this.batch.draw(this.img, 256, 0);
        this.batch.end();
    }


    @Override
    public void dispose() {
        this.batch.dispose();
        this.img.dispose();
    }
}
