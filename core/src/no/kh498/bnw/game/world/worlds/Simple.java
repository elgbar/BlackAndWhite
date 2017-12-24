package no.kh498.bnw.game.world.worlds;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.hexagon.HexUtil;
import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

/**
 * @author karl henrik
 */
public class Simple extends World {

    @Override
    protected HexagonalGrid<HexagonData> finalizeGridBuilder(final HexagonalGridBuilder<HexagonData> builder) {
        return builder.build();
    }

    @Override
    protected void finalizeWorld() {
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons(this.grid)) {
            final HexagonData data = HexUtil.getData(hexagon);
            data.type = HexType.JEWEL;
            if (hexagon.getCubeCoordinate().getGridX() == 1) {
                data.color = HexColor.DARK_GRAY;
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
}
