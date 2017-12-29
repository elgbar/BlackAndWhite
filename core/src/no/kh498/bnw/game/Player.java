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

    private int hexagons = -1;

    Player(final HexColor color) {
        this.color = color;
        TimerUtil.scheduleTask(this::calculateHexagons, 0.1f);
    }

    /**
     * Calculate the number of hexagons this color has
     */
    public void calculateHexagons() {
        Player.this.hexagons = 0;
        for (final Hexagon<HexagonData> hexagon : HexUtil.getHexagons()) {
            if (HexUtil.getData(hexagon).color == Player.this.color) {
                Player.this.hexagons++;
            }
        }
    }

    int makeMove(final HexagonData data) {
        if (data.color != this.color) {
            if (data.type.shouldChangeColor()) {

                final Player prevOwner = BnW.getGameHandler().getPlayerHandler().getPlayer(data.color);
                if (prevOwner != null) {
                    prevOwner.hexagons--;
                    if (prevOwner.hexagons == 0) {
                        BnW.gameOver = true;
                    }
                }
                this.hexagons++;
                data.color = this.color;
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
        return this.hexagons;
    }

    @Override
    public String toString() {
        return "Player{" + "color=" + this.color + '}';
    }
}
