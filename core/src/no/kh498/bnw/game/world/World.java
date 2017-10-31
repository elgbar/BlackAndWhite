package no.kh498.bnw.game.world;

import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.*;

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

    public void load() {
        final HexagonalGridBuilder<HexagonData> builder = new HexagonalGridBuilder<>();
        builder.setGridHeight(DEFAULT_GRID_RADIUS);
        builder.setGridWidth(DEFAULT_GRID_RADIUS);
        builder.setGridLayout(DEFAULT_GRID_LAYOUT);
        builder.setOrientation(DEFAULT_ORIENTATION);
        builder.setRadius(DEFAULT_RADIUS);
        this.grid = builder.build();

        //noinspection unchecked
        this.calc = builder.buildCalculatorFor(this.grid);
    }

    public void unload() {
        this.grid = null;
        this.calc = null;
    }


}
