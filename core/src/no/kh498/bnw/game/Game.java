package no.kh498.bnw.game;

import no.kh498.bnw.game.world.World;
import no.kh498.bnw.game.world.WorldHandler;
import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.HexagonalGrid;

/**
 * @author karl henrik
 */
public class Game {

    private final PlayerHandler playerHandler;
    private final WorldHandler worldHandler;

    public Game() {
        this.playerHandler = new PlayerHandler();
        this.playerHandler.addPlayer(HexColor.WHITE);
        this.playerHandler.addPlayer(HexColor.BLACK);

        this.worldHandler = new WorldHandler();
    }

    public PlayerHandler getPlayerHandler() {
        return this.playerHandler;
    }

    public WorldHandler getWorldHandler() {
        return this.worldHandler;
    }

    public void endTurn() {
        this.playerHandler.endTurn();
    }

    public Player getCurrentPlayer() {
        return this.playerHandler.getCurrentPlayer();
    }

    public World getWorld() {
        return this.worldHandler.getWorld();
    }


    public HexagonalGrid<HexagonData> getGrid() {
        return getWorld().getGrid();
    }
}