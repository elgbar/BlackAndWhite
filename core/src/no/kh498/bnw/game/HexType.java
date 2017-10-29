package no.kh498.bnw.game;

import com.badlogic.gdx.graphics.Texture;

import java.io.File;

/**
 * @author karl henrik
 */
public enum HexType {

    FLAT("flatHexagon.png"),
    DIAMOND("diamondHexagon.png"),
    JEWEL("jewelHexagon.png"),
    CUBE("cubeHexagon.png");


    public static final int TEXTURE_SIZE = 128;

    private final Texture texture;

    HexType(final String spriteName) {
        this.texture = new Texture("hex" + File.separator + spriteName);
    }

    public Texture getTexture() {
        return this.texture;
    }
}
