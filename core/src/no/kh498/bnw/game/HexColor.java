package no.kh498.bnw.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * @author karl henrik
 */
@SuppressWarnings("GwtInconsistentSerializableClass")
public enum HexColor {

    RESET(1f, 1f, 1f),
    WHITE(1f, 1f, 1f),
    GRAY(0.4f, 0.4f, 0.4f),
    BLACK(0.15f, 0.15f, 0.15f);

    private final Color shade;
//    private final TextureRegion textureRegion;

    private final HashMap<Float, TextureRegion> textureCache = new HashMap<>();

    HexColor(final float r, final float g, final float b) {
        this.shade = new Color(r, g, b, 1f);
//        this.textureRegion = new TextureRegion(shade(1f));
    }

    /**
     * Get a shade of a certain color, the method is lazy so if a percent is called once the shade is cached
     *
     * @param percent
     *     The percent of light to get (num 0-1)
     *
     * @return A shade of a color
     */
    public TextureRegion shade(final float percent) {
        if (!this.textureCache.containsKey(percent)) {
            final Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pix.setColor(this.shade.r * percent, this.shade.g * percent, this.shade.b * percent, 1f);
            pix.fill();
            final TextureRegion texReg = new TextureRegion(new Texture(pix));
            this.textureCache.put(percent, texReg);
            return texReg;
        }
        else {
            return this.textureCache.get(percent);
        }
    }

    public Color getShade() {
        return this.shade;
    }
}
