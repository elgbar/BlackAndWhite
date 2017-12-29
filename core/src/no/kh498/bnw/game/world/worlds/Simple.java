package no.kh498.bnw.game.world.worlds;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.Hexagon;

/**
 * @author karl henrik
 */
public class Simple extends World {

    @Override
    protected void finalizeWorld() {
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons(this.grid)) {
            final HexagonData data = HexUtil.getData(hexagon);
            data.type = HexType.TRIANGULAR;
            if (hexagon.getCubeCoordinate().getGridX() == 0) {
                data.color = HexColor.BLACK;
            }
            else if (hexagon.getCubeCoordinate().getGridX() == 3) {
                data.color = HexColor.WHITE;
            }
            else {
                data.type = HexType.FLAT;
            }
            hexagon.setSatelliteData(data);
        }
    }

    @Override
    protected int getGridRadius() {
        return 5;
    }
}
