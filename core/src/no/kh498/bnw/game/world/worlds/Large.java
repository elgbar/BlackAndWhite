package no.kh498.bnw.game.world.worlds;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

/**
 * @author karl henrik
 */
public class Large extends World {

    @Override
    protected void finalizeGridBuilder(final HexagonalGridBuilder<HexagonData> builder) {
        this.gridRadius = 11;
        builder.setGridHeight(this.gridRadius);
        builder.setGridWidth(this.gridRadius);
        builder.setRadius(30);
    }

    @Override
    protected void finalizeWorld() {
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons(this.grid)) {
            final HexagonData data = HexUtil.getData(hexagon);
            if (hexagon.getCubeCoordinate().getGridZ() % 4 == 0) {
                data.color = HexColor.WHITE;
                data.type = HexType.TRIANGULAR;
            }
            else {
                data.color = HexColor.BLACK;
            }
            if (hexagon.getCubeCoordinate().getGridY() % 3 == 0 || hexagon.getCubeCoordinate().getGridZ() % 3 == 0) {
                data.type = HexType.TRIANGULAR;
                data.color = HexColor.GRAY;
            }

            hexagon.setSatelliteData(data);
        }
    }
}
