package no.kh498.bnw.game;

import no.kh498.bnw.BnW;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import no.kh498.bnw.util.TimerUtil;
import org.codetome.hexameter.core.api.Hexagon;

/**
 * @author karl henrik
 */
public class Player {

    public static final int ATTACK_COST = 2;
    public static final int REINFORCE_COST = 1;

    public HexColor color;
    private boolean isAI;

    private int hexagons = -1;

    public Player(final HexColor color, boolean isAI) {
        this.color = color;
        this.isAI = isAI;
        TimerUtil.scheduleTask(this::calculateHexagons, 0.1f);
    }

    /**
     * Calculate the number of hexagons this color has
     */
    public void calculateHexagons() {
        hexagons = 0;
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons()) {
            if (HexUtil.getData(hexagon).color == color) {
                hexagons++;
            }
        }
    }

    int makeMove(final HexagonData data) {
        if (data.color != color) {
            if (data.type.shouldChangeColor()) {

                final Player prevOwner = BnW.getGameHandler().getPlayerHandler().getPlayer(data.color);
                if (prevOwner != null) {
                    prevOwner.hexagons--;
                    if (prevOwner.hexagons == 0) {
                        BnW.gameOver = true;
                    }
                }
                hexagons++;
                data.color = color;
            }
            else {
                data.type = data.type.getPrevLevel();

            }
            return ATTACK_COST;
        }
        else {
            data.type = data.type.getNextLevel();
            return REINFORCE_COST;
        }
    }

    public int getHexagons() {
        return hexagons;
    }

    public boolean isAI() {
        return isAI;
    }

    @Override
    public String toString() {
        return "Player{" + "color=" + color + '}';
    }
}
