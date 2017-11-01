package no.kh498.bnw.game;

/**
 * @author karl henrik
 */
public class Game {

    private final PlayerHandler playerHandler;

    public Game() {
        this.playerHandler = new PlayerHandler();
        this.playerHandler.addPlayer(HexColor.WHITE);
        this.playerHandler.addPlayer(HexColor.BLACK);
    }

    public PlayerHandler getPlayerHandler() {
        return this.playerHandler;
    }

    public void endTurn() {
        this.playerHandler.endTurn();
    }

    public Player getCurrentPlayer() {
        return this.playerHandler.getCurrentPlayer();
    }
}
