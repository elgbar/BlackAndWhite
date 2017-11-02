package no.kh498.bnw.hexagon;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

/**
 * @author karl henrik
 */
public class HexagonData extends DefaultSatelliteData {


    public static float DIM = 0.75f;
    public static float BRIGHT = 0.9f;
    public static float SELECTED = 0.1f;

    /**
     * The color of the hexagon
     */
    public HexColor color = HexColor.GRAY;
    /**
     * The type of the hexagon
     */
    public HexType type = HexType.FLAT;
    /**
     * Modifier of how bright the hex should be
     */
    public float brightness = BRIGHT;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HexagonData)) {
            return false;
        }

        final HexagonData data = (HexagonData) o;

        return this.color == data.color && this.type == data.type;
    }

    @Override
    public int hashCode() {
        int result = this.color.hashCode();
        result = 31 * result + this.type.hashCode();
        return result;
    }
}
