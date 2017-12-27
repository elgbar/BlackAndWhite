package no.kh498.bnw.util;

import com.badlogic.gdx.Gdx;
import no.kh498.bnw.BnW;
import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author kheba
 */
public class HexUtil {

    private static ArrayList<Hexagon<HexagonData>> hexes = new ArrayList<>();
    private static int worldHash;

    /**
     * @param hexagon
     *     The hexagon to get the data from
     *
     * @return HexagonData of this hexagon
     */
    public static HexagonData getData(final Hexagon<HexagonData> hexagon) {
        return hexagon.getSatelliteData().orElse(new HexagonData());
    }

    /**
     * @param x
     *     screen x
     * @param y
     *     screen y
     *
     * @return Get the hexagon at a given (inaccurate) screen location or {@code null} if nothing is found
     */
    public static Hexagon<HexagonData> getHexagon(final int x, final int y) {
        return BnW.getGame().getGrid().getByPixelCoordinate(x, y).orElse(null);
    }

    /**
     * @return Get the hexagon at the cursor or {@code null} if nothing is found
     */
    public static Hexagon<HexagonData> getCursorHexagon() {
        final int x = Gdx.input.getX() + (int) BnW.getChangedX();
        final int y = Gdx.input.getY() + (int) BnW.getChangedY();
        return getHexagon(x, y);
    }

    /**
     * @param grid
     *     The grid to get all hexagons from
     *
     * @return Get all the hexagons in a grid
     */
    public static ArrayList<Hexagon<HexagonData>> getHexagons(final HexagonalGrid<HexagonData> grid) {
        final ArrayList<Hexagon<HexagonData>> hexes = new ArrayList<>();
        final rx.Observable<Hexagon<HexagonData>> hexagons = grid.getHexagons();
        hexagons.forEach(hexes::add);
        return hexes;
    }

    /**
     * Get the hexagons in a more simple way
     *
     * @return All hexagons from the default game grid.
     */
    public static ArrayList<Hexagon<HexagonData>> getHexagons() {
        if (hexes.size() == 0 || BnW.getGame().getWorld().hashCode() != worldHash) {
            System.out.println("regenerating the grid | old size " + hexes.size());
            hexes = getHexagons(BnW.getGame().getGrid());
            worldHash = BnW.getGame().getWorld().hashCode();
        }
        return hexes;
    }

    /**
     * @param start
     *     The hexagon to start at
     *
     * @return All hexagons with the same color that are connected and their neighbors
     */
    public static HashSet<Hexagon<HexagonData>> connectedAdjacentHexagons(final Hexagon<HexagonData> start) {
        return adjacentHexagons(connectedHexagons(start));

    }

    /**
     * @param start
     *     The hexagon to start at
     *
     * @return All hexagons connected to the start hexagon that has the same color
     */
    public static HashSet<Hexagon<HexagonData>> connectedHexagons(final Hexagon<HexagonData> start) {
        return connectedHexagons(start, getData(start).color, new HashSet<>());
    }

    private static HashSet<Hexagon<HexagonData>> connectedHexagons(final Hexagon<HexagonData> center,
                                                                   final HexColor color,
                                                                   final HashSet<Hexagon<HexagonData>> visited) {
        //only check a hexagon if they have the same color and haven't been visited
        if (visited.contains(center) || getData(center).color != color) {
            return visited;
        }

        //add as visited
        visited.add(center);

        //check each neighbor
        for (final Hexagon<HexagonData> neighbor : BnW.getGame().getGrid().getNeighborsOf(center)) {
            connectedHexagons(neighbor, color, visited);
        }
        return visited;
    }

    /**
     * @param set
     *
     * @return
     */
    public static HashSet<Hexagon<HexagonData>> adjacentHexagons(final Collection<Hexagon<HexagonData>> set) {
        final HashSet<Hexagon<HexagonData>> adjacent = new HashSet<>();
        adjacent.addAll(set);

        for (final Hexagon<HexagonData> hex : set) {
            for (final Hexagon<HexagonData> neighbor : BnW.getGame().getGrid().getNeighborsOf(hex)) {
                if (!adjacent.contains(neighbor)) {
                    adjacent.add(neighbor);
                }
            }
        }
        return adjacent;
    }

}
