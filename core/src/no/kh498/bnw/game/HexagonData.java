package no.kh498.bnw.game;

import org.codetome.hexameter.core.api.contract.SatelliteData;

/**
 * @author karl henrik
 */
public class HexagonData implements SatelliteData {

    public HexColor color = HexColor.GRAY;
    public HexType type = HexType.CUBE;

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public void setPassable(final boolean passable) {

    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public void setOpaque(final boolean opaque) {

    }

    @Override
    public double getMovementCost() {
        return 0;
    }

    @Override
    public void setMovementCost(final double movementCost) {

    }
}
