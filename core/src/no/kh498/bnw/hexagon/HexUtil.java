package no.kh498.bnw.hexagon;

import no.kh498.bnw.BnW;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.ArrayList;

/**
 * @author kheba
 */
public class HexUtil {

    public static HexagonData getData(final Hexagon<HexagonData> hexagon) {
        return hexagon.getSatelliteData().orElse(new HexagonData());
    }

    public static Hexagon<HexagonData> getHexagon(final int x, final int y) {
        return BnW.getGame().getGrid().getByPixelCoordinate(x, y).orElse(null);
    }

    private static ArrayList<Hexagon<HexagonData>> hexs;
    private static int worldHash;

    public static ArrayList<Hexagon<HexagonData>> getHexagons(final HexagonalGrid<HexagonData> grid) {
        if (hexs == null) {
            hexs = new ArrayList<>();
            final rx.Observable<Hexagon<HexagonData>> hexagons = grid.getHexagons();
            hexagons.forEach(hexs::add);
        }
        return hexs;
    }

    public static ArrayList<Hexagon<HexagonData>> getHexagons() {
        if (hexs == null || BnW.getGame().getWorld().hashCode() != worldHash) {
            System.out.println("regenerating the grid");
            hexs = getHexagons(BnW.getGame().getGrid());
            worldHash = BnW.getGame().getWorld().hashCode();
        }
        return hexs;
    }

}
