package no.kh498.bnw.hexagon;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

/**
 * @author karl henrik
 */
public class HexagonData extends DefaultSatelliteData {

    public HexColor color = HexColor.GRAY;
    public HexType type = HexType.CUBE;

    public static HexagonData get(final Hexagon<HexagonData> hexagon) {
        return hexagon.getSatelliteData().orElse(new HexagonData());
    }

}
