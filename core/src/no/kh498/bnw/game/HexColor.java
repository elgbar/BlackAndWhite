package no.kh498.bnw.game;

import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;

/**
 * @author karl henrik
 */
@SuppressWarnings("GwtInconsistentSerializableClass")
public enum HexColor {
    WHITE(0.9f),
    GRAY(0.55f),
    BLACK(0.3f),
    BLUE(0, 0.5f, 1),
    ORANGE(1, 0.5f, 0);

    private final Color shade;
    private final HashMap<Float, Color> ColorCache = new HashMap<>();

    HexColor(final float shade) {
        this(shade, shade, shade);
    }

    HexColor(final float r, final float g, final float b) {
        shade = new Color(r, g, b, 1f);
    }

    /**
     * Get a shade of a certain color, the method is lazy so if a percent is called once the shade is cached
     *
     * @param percent
     *     The percent of light to getData (num 0-1)
     *
     * @return A shade of a color
     */
    public Color shade(final float percent) {
        if (!ColorCache.containsKey(percent)) {
            final Color c = new Color(shade.r * percent, shade.g * percent, shade.b * percent, 1f);
            ColorCache.put(percent, c);
            return c;
        }
        else {
            return ColorCache.get(percent);
        }
    }

    public Color getColor() {
        return shade;
    }

    public Color inverse() {
        return new Color(1 - shade.r, 1 - shade.g, 1 - shade.b, 1f);
    }

    @Override
    public String toString() {
        return name().replace("_", " ").toLowerCase();
    }
}
