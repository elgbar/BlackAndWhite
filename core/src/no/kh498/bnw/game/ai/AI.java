package no.kh498.bnw.game.ai;

import no.kh498.bnw.BnW;
import no.kh498.bnw.game.HexType;
import no.kh498.bnw.game.Player;
import no.kh498.bnw.game.PlayerHandler;
import no.kh498.bnw.game.world.World;
import no.kh498.bnw.hexagon.HexagonData;
import no.kh498.bnw.util.HexUtil;
import org.codetome.hexameter.core.api.Hexagon;

/**
 * @author kheba
 */
public class AI {

    public static void makeMove(Player player, World world) {
        PlayerHandler ph = BnW.getGameHandler().getPlayerHandler();

        boolean modified = true;
        while (modified) {
            modified = false;
            for (Hexagon<HexagonData> hexagon : HexUtil.getHexagons(world.getGrid())) {
                if (ph.getMovesLeft() == 0) { return; }
                HexagonData data = HexUtil.getData(hexagon);
                if (data.color == player.color && data.type.getNextLevel().level > data.type.level) {
                    ph.makeMove(hexagon);
                    modified = true;
                }
            }
            if (!modified && ph.getMovesLeft() > Player.ATTACK_COST) {
                HexType minType = null;
                Hexagon<HexagonData> minHex = null;

                for (Hexagon<HexagonData> hexagon : ph.getHighlighted()) {
                    HexagonData data = HexUtil.getData(hexagon);
                    if (data.color != player.color && data.type.canChange() &&
                        (minType == null || data.type.level < minType.level)) {
                        minHex = hexagon;
                        minType = data.type;
                    }
                }
                if (minHex != null) {
                    ph.makeMove(minHex);
                    modified = true;
                }
            }
        }

    }

}
