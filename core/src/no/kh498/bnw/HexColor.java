package no.kh498.bnw;

import com.badlogic.gdx.graphics.Color;

/**
 * @author karl henrik
 */
public enum HexColor {

    WHITE(1f),
    GRAY(0.4f),
    BLACK(0.15f);

    private Color shade = new Color();

    HexColor(final float shadeValue) {
        this.shade = new Color(shadeValue, shadeValue, shadeValue, 1f);
    }

    public Color getShade() {
        return this.shade;
    }
}
