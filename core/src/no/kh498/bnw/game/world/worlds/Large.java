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
        this.gridRadius = 31;
        builder.setGridHeight(this.gridRadius);
        builder.setGridWidth(this.gridRadius);
        builder.setRadius(10);
    }

    @Override
    protected void finalizeWorld() {
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons(this.grid)) {
            final HexagonData data = HexUtil.getData(hexagon);
            if (hexagon.getCubeCoordinate().getGridX() % 2 == 0) {
                data.color = HexColor.DARK_GRAY;
            }
            else {
                data.color = HexColor.WHITE;
            }
            data.type = HexType.FLAT;
            hexagon.setSatelliteData(data);
        }
    }
}
