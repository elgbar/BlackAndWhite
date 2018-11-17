package no.kh498.bnw.game.world;

import com.badlogic.gdx.Gdx;
import no.kh498.bnw.BnW;
import no.kh498.bnw.game.Player;
import no.kh498.bnw.game.PlayerHandler;
import no.kh498.bnw.input.InputListener;
import no.kh498.bnw.util.TimerUtil;
import org.codetome.hexameter.core.internal.GridData;

/**
 * @author karl henrik
 */
public class WorldHandler {

    private World world;
    private int worldNr = 0;

    public WorldHandler() {
        load();
    }

    public void nextWorld() {
        worldNr++;

        //Make the worlds loop
        if (worldNr == WorldList.values().length) {
            worldNr = 0;
        }
        unload();
        load();
    }

    public void unload() {
        //reset camera
        BnW.moveCamera(-BnW.getChangedX(), -BnW.getChangedY());
        InputListener.resetTotalZoom();
        world.unload();
    }

    public void load() {
        BnW.gameOver = false;
        final WorldList nextWorld = WorldList.values()[worldNr];
        world = nextWorld.getWorld();
        world.load();
        centerWorld();


        Gdx.graphics.setTitle("BnW - " + nextWorld.name().toLowerCase());


        TimerUtil.scheduleTask(() -> {
            final PlayerHandler handler = BnW.getGameHandler().getPlayerHandler();
            //make the white player always start, and with the correct movement points.
            handler.reset();
//            if (handler.getCurrentPlayer().color != HexColor.WHITE) {
////                handler.endTurn();
//            }

            for (final Player player : BnW.getGameHandler().getPlayerHandler().getPlayers()) {
                player.calculateHexagons();
            }
            System.out.println("World " + nextWorld + " loaded");
        }, 0.1f);
    }

    public void centerWorld() {

        final GridData data = world.grid.getGridData();
        final float deltaX = (float) (
            ((data.getGridWidth() * data.getHexagonWidth() + data.getHexagonWidth() / 2) - Gdx.graphics.getWidth()) /
            2);
        final float deltaY = (float) (
            (((data.getGridHeight() * data.getHexagonHeight()) + (data.getHexagonHeight() / 2)) -
             Gdx.graphics.getHeight()) / 2);
//        final float deltaX = (float) ((data.getGridWidth() * data.getHexagonWidth() - Gdx.graphics.getWidth()) / 2);
//        final float deltaY = (float) ((data.getGridHeight() * data.getHexagonHeight() - Gdx.graphics.getHeight()) /
// 2);

        //put the grid in the center of the world
        BnW.moveCamera(deltaX, deltaY);
    }

    public World getWorld() {
        return world;
    }
}
