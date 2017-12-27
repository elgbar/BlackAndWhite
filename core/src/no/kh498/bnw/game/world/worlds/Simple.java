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
public class Simple extends World {

    @Override
    protected void finalizeGridBuilder(final HexagonalGridBuilder<HexagonData> builder) {
        this.gridRadius = 5;
        builder.setGridHeight(this.gridRadius);
        builder.setGridWidth(this.gridRadius);
    }

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
}
