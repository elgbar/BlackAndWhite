package no.kh498.bnw.game;

import no.kh498.bnw.BnW;
import no.kh498.bnw.hexagon.HexColor;
import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.Hexagon;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author karl henrik
 */
public class PlayerHandler {


    private final ArrayList<Player> players = new ArrayList<>();
    private int currPlayerIndex;

    private static final int DEFAULT_MOVES = 3;
    private int movesLeft = DEFAULT_MOVES;

    void addPlayer(final HexColor color) {
        for (final Player player : this.players) {
            if (player.color == color) {
                throw new IllegalArgumentException(
                    "The color " + color + " has already been registered as the color of a player");
            }
        }
        this.players.add(new Player(color));
    }


    public Player getCurrentPlayer() {
        return this.players.get(this.currPlayerIndex);
    }

    public void endTurn() {
        this.movesLeft = DEFAULT_MOVES;
        this.currPlayerIndex++;
        if (this.currPlayerIndex == this.players.size()) {
            this.currPlayerIndex = 0;
        }
    }

    public boolean canReach(final Hexagon<HexagonData> hexagon) {

        final HexColor currColor = getCurrentPlayer().color;
        
        if (HexagonData.getData(hexagon).color == currColor) {
            return true;
        }

        final Collection<Hexagon<HexagonData>> neighbors = BnW.getWorld().getGrid().getNeighborsOf(hexagon);
        for (final Hexagon<HexagonData> hex : neighbors) {
            if (HexagonData.getData(hex).color == currColor) {
                return true;
            }
        }
        return false;
    }

    public void makeMove(final Hexagon<HexagonData> hexagon) {

        if (!canReach(hexagon)) {
            return;
        }

        final HexagonData data = HexagonData.getData(hexagon);
        final int check = data.hashCode();

        getCurrentPlayer().makeMove(data);

        if (check != data.hashCode()) {
            this.movesLeft--;
            if (this.movesLeft <= 0) {
                endTurn();
            }
        }
        hexagon.setSatelliteData(data);
    }

    public int getMovesLeft() {
        return this.movesLeft;
    }
}
