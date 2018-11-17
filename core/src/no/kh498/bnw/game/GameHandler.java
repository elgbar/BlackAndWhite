package no.kh498.bnw.game;

import no.kh498.bnw.game.world.World;
import no.kh498.bnw.game.world.WorldHandler;
import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.HexagonalGrid;

/**
 * @author karl henrik
 */
public class GameHandler {

    private final PlayerHandler playerHandler;
    private final WorldHandler worldHandler;

    public GameHandler() {
        worldHandler = new WorldHandler();

        playerHandler = new PlayerHandler();
        playerHandler.addPlayer(HexColor.WHITE, false);
        playerHandler.addPlayer(HexColor.BLACK, true);
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public WorldHandler getWorldHandler() {
        return worldHandler;
    }

    public void endTurn() {
        playerHandler.endTurn();
    }

    public Player getCurrentPlayer() {
        return playerHandler.getCurrentPlayer();
    }

    public World getWorld() {
        return worldHandler.getWorld();
    }

    public HexagonalGrid<HexagonData> getGrid() {
        return getWorld().getGrid();
    }
}
