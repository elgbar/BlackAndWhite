package no.kh498.bnw.game.world.worlds;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

/**
 * @author karl henrik
 */
public class Beginner extends World {

    @Override
    protected void finalizeGridBuilder(final HexagonalGridBuilder<HexagonData> builder) {
        this.gridRadius = 3;
        builder.setGridHeight(this.gridRadius);
        builder.setGridWidth(this.gridRadius);
        builder.setRadius(90);
    }

    @Override
    protected void finalizeWorld() {
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons(this.grid)) {
            final HexagonData data = HexUtil.getData(hexagon);
            final CubeCoordinate coord = hexagon.getCubeCoordinate();
            final int x = coord.getGridX();
            final int z = coord.getGridZ();
            if (x == 0 && z == 2 || x == 1 && z == 2 || x == 2 && z == 1) {
                data.color = HexColor.WHITE;
            }
            else if (x == 1 && z == 0) {
                data.color = HexColor.DARK_GRAY;
            }
            hexagon.setSatelliteData(data);
        }
    }
}
