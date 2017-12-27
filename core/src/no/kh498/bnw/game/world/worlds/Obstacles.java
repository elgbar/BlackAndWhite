package no.kh498.bnw.game.world.worlds;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

/**
 * @author kheba
 */
public class Obstacles extends World {

    @Override
    protected void finalizeGridBuilder(final HexagonalGridBuilder<HexagonData> builder) {

    }

    @Override
    protected void finalizeWorld() {
        final int centre = (int) Math.floor(this.gridRadius / 2);
//        System.out.println("centre: " + centre);
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons(this.grid)) {
            final HexagonData data = HexUtil.getData(hexagon);
            final CubeCoordinate coord = hexagon.getCubeCoordinate();

            if (coord.getGridX() == centre && Math.abs(coord.getGridZ()) % 2 == 0) {
                data.type = HexType.ASYMMETRICAL;
            }
            else if (coord.getGridX() == 1 && coord.getGridZ() == 3) {
                data.type = HexType.DIAMOND;
                data.color = HexColor.BLACK;
            }
            else if (coord.getGridX() == 5 && coord.getGridZ() == 1) {
                data.type = HexType.DIAMOND;
                data.color = HexColor.WHITE;
            }
            hexagon.setSatelliteData(data);
        }
    }
}
