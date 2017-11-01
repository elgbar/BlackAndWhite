package no.kh498.bnw.hexagon;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

/**
 * @author karl henrik
 */
public class HexagonData extends DefaultSatelliteData {

    public HexColor color = HexColor.GRAY;
    public HexType type = HexType.FLAT;


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