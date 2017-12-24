package no.kh498.bnw.game.world.worlds;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.hexagon.HexUtil;
import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

/**
 * @author kheba
 */
public class Obstacles extends World {

    private final static int GRID_RADIUS = 9;

    @Override
    protected HexagonalGrid<HexagonData> finalizeGridBuilder(final HexagonalGridBuilder<HexagonData> builder) {
        builder.setGridHeight(GRID_RADIUS);
        builder.setGridWidth(GRID_RADIUS);
        return builder.build();
    }

    @Override
    protected void finalizeWorld() {
        final int centre = (int) Math.floor(GRID_RADIUS / 2);
//        System.out.println("centre: " + centre);
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons(this.grid)) {
            final HexagonData data = HexUtil.getData(hexagon);
            final CubeCoordinate coord = hexagon.getCubeCoordinate();

            if (coord.getGridX() == centre && Math.abs(coord.getGridZ()) % 2 != 0) {
                data.type = HexType.ASYMMETRICAL;
            }
            else if (coord.getGridX() == 2 && coord.getGridZ() == 3) {
                data.type = HexType.DIAMOND;
                data.color = HexColor.DARK_GRAY;
            }
            else if (coord.getGridX() == 6 && coord.getGridZ() == 1) {
                data.type = HexType.DIAMOND;
                data.color = HexColor.WHITE;
            }
            hexagon.setSatelliteData(data);
        }
    }
}
