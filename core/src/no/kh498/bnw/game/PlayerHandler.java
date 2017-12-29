package no.kh498.bnw.game;

import no.kh498.bnw.BnW;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.Hexagon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author karl henrik
 */
public class PlayerHandler {


    private final ArrayList<Player> players = new ArrayList<>();
    private int currPlayerIndex;

    private static final int MIN_MOVES = 1;
    private int movesLeft = -1;

    //hashcode of the current world
    private int worldHash;
    private HashSet<Hexagon<HexagonData>> highlighted;

    /**
     * Add a player to the game
     *
     * @param color
     *     The color of the player
     */
    void addPlayer(final HexColor color) {
        for (final Player player : this.players) {
            if (player.color == color) {
                throw new IllegalArgumentException(
                    "The color " + color + " has already been registered as the color of a player");
            }
        }
        this.players.add(new Player(color));
    }

    /**
     * @param color
     *     Color of the player to get
     *
     * @return The player object associated with the {@code color} or {@code null} if no player is found
     */
    Player getPlayer(final HexColor color) {
        for (final Player player : this.players) {
            if (player.color == color) {
                return player;
            }
        }
        return null;
    }

    public List<Player> getPlayers() {
        return this.players;
    }


    public Player getCurrentPlayer() {
        return this.players.get(this.currPlayerIndex);
    }

    /**
     * End the turn for the current player
     */
    public void endTurn() {
        this.currPlayerIndex++;
        if (this.currPlayerIndex == this.players.size()) {
            this.currPlayerIndex = 0;
        }
        this.movesLeft = calculateMoves();
        calculateHighlightedHexes();
    }

    /**
     * @param hexagon
     *     The hexagon to check
     *
     * @return If the current player can reach {@code hexagon}
     */
    private boolean canReach(final Hexagon<HexagonData> hexagon) {
        final HexColor currColor = getCurrentPlayer().color;

        if (HexUtil.getData(hexagon).color == currColor) {
            return true;
        }
        else if (this.movesLeft < Player.ATTACK_COST) {
            return false;
        }
        for (final Hexagon<HexagonData> hex : BnW.getGameHandler().getGrid().getNeighborsOf(hexagon)) {
            if (HexUtil.getData(hex).color == currColor) {
                return true;
            }
        }
        return false;
    }

    /**
     * Make the current player make a move, aka attack or reinforce
     *
     * @param hexagon
     *     The hexagon to make a move on
     */
    public void makeMove(final Hexagon<HexagonData> hexagon) {
        if (!canReach(hexagon) || this.movesLeft <= 0) {
            return;
        }

        final HexagonData data = HexUtil.getData(hexagon);
        final int check = data.hashCode();

        final int rem = getCurrentPlayer().makeMove(data);
        if (check != data.hashCode()) {
            this.movesLeft -= rem;
        }

        //update the highlighted map
        calculateHighlightedHexes();
    }

    /**
     * @return Number of moves left for the current player
     */
    public int getMovesLeft() {
        if (this.movesLeft == -1) {
            this.movesLeft = calculateMoves();
        }
        return this.movesLeft;
    }

    /**
     * @return The maximum number of moves a player can take this round
     */
    private int calculateMoves() {
        int suggestedMoves = 0;
        final HexColor plrColor = getCurrentPlayer().color;

        for (final Hexagon<HexagonData> hex : HexUtil.getHexagons()) {
            final HexagonData data = HexUtil.getData(hex);
            if (data.color == plrColor) {
                suggestedMoves += data.type.level;
            }
        }

        return suggestedMoves < PlayerHandler.MIN_MOVES ? PlayerHandler.MIN_MOVES : suggestedMoves;
    }

    /**
     * Find out which hexagons that should be highlighted
     */
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

    /**
     * @return Get the highlighted hexagons for the current player
     */
    public HashSet<Hexagon<HexagonData>> getHighlighted() {
        //if the world changes the highlighted must be regenerated
        //TODO find a better way of doing this
        final int newHash = BnW.getGameHandler().getWorld().hashCode();
        if (this.highlighted == null || this.worldHash != newHash) {
            calculateHighlightedHexes();
            this.worldHash = newHash;
        }
        return this.highlighted;
    }
}
