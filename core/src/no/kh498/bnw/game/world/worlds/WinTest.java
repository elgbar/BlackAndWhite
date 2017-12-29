package no.kh498.bnw.game.world.worlds;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

/**
 * @author karl henrik
 */
public class WinTest extends World {

    @Override
    protected void finalizeGridBuilder(final HexagonalGridBuilder<HexagonData> builder) {
        builder.setOrientation(HexagonOrientation.POINTY_TOP);
    }

    @Override
    protected void finalizeWorld() {
        boolean set = false;
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons(this.grid)) {
            final HexagonData data = HexUtil.getData(hexagon);
            if (hexagon.getCubeCoordinate().getGridX() % 2 == 0 && !set) {
                data.color = HexColor.BLACK;
                set = true;
            }
            else {
                data.color = HexColor.WHITE;
            }
            data.type = HexType.FLAT;
            hexagon.setSatelliteData(data);
        }
    }

    @Override
    protected int getGridRadius() {
        return DEFAULT_GRID_RADIUS;
    }
}
