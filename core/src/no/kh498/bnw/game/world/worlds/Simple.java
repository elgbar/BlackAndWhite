package no.kh498.bnw.game.world.worlds;

import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.HexType;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.Hexagon;
import rx.Observable;

/**
 * @author karl henrik
 */
public class Simple extends World {

    @Override
    public void load() {
        super.load();

        final Observable<Hexagon<HexagonData>> hexagons = getGrid().getHexagons();
        hexagons.forEach(hexagon -> {
            final HexagonData data = HexagonData.getData(hexagon);
            data.type = HexType.JEWEL;
            if (hexagon.getCubeCoordinate().getGridX() == 1) {
                data.color = HexColor.BLACK;
            }
            else if (hexagon.getCubeCoordinate().getGridX() == 3) {
                data.color = HexColor.WHITE;
            }
            else {
                data.type = HexType.HALF;
            }
            hexagon.setSatelliteData(data);
        });
    }
}
