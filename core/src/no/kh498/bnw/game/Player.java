package no.kh498.bnw.game;

import no.kh498.bnw.hexagon.HexagonData;

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
}
