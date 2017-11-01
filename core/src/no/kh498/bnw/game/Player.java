package no.kh498.bnw.game;

import no.kh498.bnw.hexagon.HexUtil;
import no.kh498.bnw.hexagon.HexagonData;
import org.codetome.hexameter.core.api.Hexagon;

/**
 * @author karl henrik
 */
public class Player {

    public HexColor color;


    Player(final HexColor color) {
        this.color = color;
    }

    void makeMove(final HexagonData data) {
        if (data.color != this.color) {
            if (data.type.shouldChangeColor()) {
                data.color = this.color;
            }
            else {
                data.type = data.type.getPrevLevel();
            }
        }
        else {
            data.type = data.type.getNextLevel();
        }
    }

    int calculateMoves() {
        int sum = 0;
        int hexes = 0;
        for (final Hexagon<HexagonData> hex : HexUtil.getHexagons()) {
            final HexagonData data = HexUtil.getData(hex);
            if (data.color == this.color) {
                hexes++;
                sum += data.type.level;
            }
        }

        final int suggestedMoves = sum / hexes;

        if (suggestedMoves < PlayerHandler.DEFAULT_MOVES) {
            return PlayerHandler.DEFAULT_MOVES;
        }
        return suggestedMoves;
    }
}
