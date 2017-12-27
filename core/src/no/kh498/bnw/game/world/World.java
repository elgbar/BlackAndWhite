package no.kh498.bnw.game.world;

import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

/**
 * @author karl henrik
 */
public abstract class World {


    protected int gridRadius = DEFAULT_GRID_RADIUS;

    private static final int DEFAULT_GRID_RADIUS = 7;
    private static final HexagonalGridLayout DEFAULT_GRID_LAYOUT = HexagonalGridLayout.HEXAGONAL;
    private static final HexagonOrientation DEFAULT_ORIENTATION = HexagonOrientation.FLAT_TOP;
    private static final double DEFAULT_RADIUS = 40;


    protected HexagonalGrid<HexagonData> grid;

    protected World() {

    }

    public HexagonalGrid<HexagonData> getGrid() {
        if (this.grid == null) {
            throw new WorldNotLoadedException();
        }
        return this.grid;
    }


    public void load() {
        final HexagonalGridBuilder<HexagonData> builder = defaultGridBuilder();
        finalizeGridBuilder(builder);
        this.grid = builder.build();
        finalizeWorld();
    }

    /**
     * @param builder
     *     The builder to finalize
     */
    protected abstract void finalizeGridBuilder(HexagonalGridBuilder<HexagonData> builder);

    /**
     * Make the world unique by changing the hexes in the world
     */
    protected abstract void finalizeWorld();

    /**
     * @return A default grid
     */
    private HexagonalGridBuilder<HexagonData> defaultGridBuilder() {
        final HexagonalGridBuilder<HexagonData> builder = new HexagonalGridBuilder<>();
        builder.setGridHeight(DEFAULT_GRID_RADIUS);
        builder.setGridWidth(DEFAULT_GRID_RADIUS);
        builder.setGridLayout(DEFAULT_GRID_LAYOUT);
        builder.setOrientation(DEFAULT_ORIENTATION);
        builder.setRadius(DEFAULT_RADIUS);
        return builder;
    }

    public void unload() {
        this.grid = null;
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
