package no.kh498.bnw.game;

import no.kh498.bnw.BnW;
import no.kh498.bnw.hexagon.HexUtil;
import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.Hexagon;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author karl henrik
 */
public class PlayerHandler {


    private final ArrayList<Player> players = new ArrayList<>();
    private int currPlayerIndex;

    private static final int MIN_MOVES = 3;
    private int movesLeft = -1;

    //hashcode of the current world
    private int worldHash;
    private HashSet<Hexagon<HexagonData>> highlighted;

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
        this.currPlayerIndex++;
        if (this.currPlayerIndex == this.players.size()) {
            this.currPlayerIndex = 0;
        }
        this.movesLeft = calculateMoves();
        calculateHighlightedHexes();
    }

    public boolean canReach(final Hexagon<HexagonData> hexagon) {
        final HexColor currColor = getCurrentPlayer().color;

        if (HexUtil.getData(hexagon).color == currColor) {
            return true;
        }
        else if (this.movesLeft < Player.ATTACK_COST) {
            return false;
        }
        for (final Hexagon<HexagonData> hex : BnW.getGame().getGrid().getNeighborsOf(hexagon)) {
            if (HexUtil.getData(hex).color == currColor) {
                return true;
            }
        }
        return false;
    }

    public void makeMove(final Hexagon<HexagonData> hexagon) {
        if (!canReach(hexagon)) {
            return;
        }

        final HexagonData data = HexUtil.getData(hexagon);
        final int check = data.hashCode();

        final int rem = getCurrentPlayer().makeMove(data);
        if (check != data.hashCode()) {
            this.movesLeft -= rem;
            if (this.movesLeft <= 0) {
                endTurn();
            }
        }

        //update the highlighted map
        calculateHighlightedHexes();
    }

    public int getMovesLeft() {
        if (this.movesLeft == -1) {
            this.movesLeft = calculateMoves();
        }
        return this.movesLeft;
    }

    private int calculateMoves() {
        int sum = 0;
//        int hexes = 0;
        final HexColor plrColor = getCurrentPlayer().color;

        for (final Hexagon<HexagonData> hex : HexUtil.getHexagons()) {
            final HexagonData data = HexUtil.getData(hex);
            if (data.color == plrColor) {
//                hexes++;
                sum += data.type.level / 2;
            }
        }

        final int suggestedMoves = sum;

        if (suggestedMoves < PlayerHandler.MIN_MOVES) {
            return PlayerHandler.MIN_MOVES;
        }
        return suggestedMoves;
    }

    private void calculateHighlightedHexes() {
        final HashSet<Hexagon<HexagonData>> highlighted = new HashSet<>();

        final HexColor color = getCurrentPlayer().color;
        for (final Hexagon<HexagonData> lhex : HexUtil.getHexagons()) {
            final HexagonData data = HexUtil.getData(lhex);
            if (data.color == color && !highlighted.contains(lhex)) {
                highlighted.add(lhex);
            }
        }
        if (this.getMovesLeft() >= Player.ATTACK_COST) {
            this.highlighted = HexUtil.adjacentHexagons(highlighted);
        }
        else {
            this.highlighted = highlighted;
        }
    }

    public HashSet<Hexagon<HexagonData>> getHighlighted() {
        //if the world changes the highlighted must be regenerated
        //TODO find a better way of doing this
        final int newHash = BnW.getGame().getWorld().hashCode();
        if (this.highlighted == null || this.worldHash != newHash) {
            calculateHighlightedHexes();
            this.worldHash = newHash;
        }
        return this.highlighted;
    }
}
