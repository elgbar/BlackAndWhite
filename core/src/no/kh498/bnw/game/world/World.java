package no.kh498.bnw.game.world;

import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridCalculator;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

/**
 * @author karl henrik
 */
public abstract class World {

    protected static final int DEFAULT_GRID_RADIUS = 5;
    protected static final HexagonalGridLayout DEFAULT_GRID_LAYOUT = HexagonalGridLayout.HEXAGONAL;
    protected static final HexagonOrientation DEFAULT_ORIENTATION = HexagonOrientation.FLAT_TOP;
    protected static final double DEFAULT_RADIUS = 40;

    protected HexagonalGrid<HexagonData> grid;
    protected HexagonalGridCalculator<HexagonData> calc;

    public HexagonalGrid<HexagonData> getGrid() {
        if (this.grid == null) {
            throw new WorldNotLoadedException();
        }
        return this.grid;
    }

    public HexagonalGridCalculator<HexagonData> getCalc() {
        if (this.calc == null) {
            throw new WorldNotLoadedException();
        }
        return this.calc;
    }

    public abstract void load();

    public void unload() {
        this.grid = null;
        this.calc = null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof World)) {
            return false;
        }

        final World world = (World) o;

        return this.grid.equals(world.grid);
    }

    @Override
    public int hashCode() {
        return this.grid.hashCode();
    }
}
