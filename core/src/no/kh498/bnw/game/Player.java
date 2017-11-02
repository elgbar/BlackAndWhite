package no.kh498.bnw.game;

import no.kh498.bnw.hexagon.HexagonData;

/**
 * @author karl henrik
 */
public class Player {

    public static final int ATTACK_COST = 2;
    public static final int REINFORCE_COST = 1;

    public HexColor color;


    Player(final HexColor color) {
        this.color = color;
    }

    int makeMove(final HexagonData data) {
        if (data.color != this.color) {
            if (data.type.shouldChangeColor()) {
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
}
