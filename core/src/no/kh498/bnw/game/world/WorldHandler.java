package no.kh498.bnw.game.world;

import com.badlogic.gdx.Gdx;
import no.kh498.bnw.BnW;
import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.PlayerHandler;
import no.kh498.bnw.input.InputListener;
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
        this.worldNr++;

        //Make the worlds loop
        if (this.worldNr == WorldList.values().length) {
            this.worldNr = 0;
        }
        unload();
        load();

        final PlayerHandler handler = BnW.getGame().getPlayerHandler();

        //make the white player always start, and with the correct movement points.
        handler.endTurn();
        if (handler.getCurrentPlayer().color != HexColor.WHITE) {
            handler.endTurn();
        }
    }

    private void unload() {
        //reset camera
        final InputListener listener = BnW.getInputListener();
        listener.moveCamera(-listener.getChangedX(), -listener.getChangedY());

        this.world.unload();
        BnW.flush();
    }

    private void load() {
        final WorldList nextWorld = WorldList.values()[this.worldNr];
        this.world = nextWorld.getWorld();
        this.world.load();

        final InputListener listener = BnW.getInputListener();

        final GridData data = this.world.grid.getGridData();

        //center of the grid
        final float x = (float) ((data.getGridWidth() * data.getHexagonWidth()) / 2);
        final float y = (float) ((data.getGridHeight() * data.getHexagonHeight()) / 2);
        listener.moveCamera(x - Gdx.graphics.getWidth() / 2, y - Gdx.graphics.getHeight() / 2);

        Gdx.graphics.setTitle("BnW - " + nextWorld.name().toLowerCase());
        System.out.println("World " + nextWorld + " loaded");
    }

    public World getWorld() {
        return this.world;
    }
}
