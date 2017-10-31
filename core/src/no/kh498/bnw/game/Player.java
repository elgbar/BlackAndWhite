package no.kh498.bnw.game;

import no.kh498.bnw.hexagon.HexColor;
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


    public void makeMove(final Hexagon<HexagonData> hexagon) {
        final HexagonData data = HexagonData.get(hexagon);
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
        hexagon.setSatelliteData(data);
    }
}
