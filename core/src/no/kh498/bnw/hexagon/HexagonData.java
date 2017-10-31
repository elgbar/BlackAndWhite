package no.kh498.bnw.hexagon;

import no.kh498.bnw.BnW;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

/**
 * @author karl henrik
 */
public class HexagonData extends DefaultSatelliteData {

    public HexColor color = HexColor.GRAY;
    public HexType type = HexType.CUBE;

    public static HexagonData getData(final Hexagon<HexagonData> hexagon) {
        return hexagon.getSatelliteData().orElse(new HexagonData());
    }

    public static Hexagon<HexagonData> getHexagon(final int x, final int y) {
        return BnW.getWorld().getGrid().getByPixelCoordinate(x, y).orElse(null);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HexagonData)) {
            return false;
        }

        final HexagonData data = (HexagonData) o;

        if (this.color != data.color) {
            return false;
        }
        return this.type == data.type;
    }

    @Override
    public int hashCode() {
        int result = this.color.hashCode();
        result = 31 * result + this.type.hashCode();
        return result;
    }
}
