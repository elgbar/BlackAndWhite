package no.kh498.bnw.game;

import no.kh498.bnw.hexagon.HexColor;

import java.util.HashMap;

/**
 * @author karl henrik
 */
public class PlayerHandler {


    private final HashMap<HexColor, Player> players = new HashMap<>();

    public void addPlayer(final HexColor color) {
        if (this.players.containsKey(color)) {
            throw new IllegalArgumentException("This color has already been registered");
        }
        this.players.put(color, new Player(color));
    }

    public Player getPlayer(final HexColor color) {
        return this.players.get(color);
    }


}
