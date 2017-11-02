package no.kh498.bnw.game.world;

import com.badlogic.gdx.Gdx;
import no.kh498.bnw.BnW;
import no.kh498.bnw.game.HexColor;
import no.kh498.bnw.game.PlayerHandler;

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
        handler.endTurn();
        if (handler.getCurrentPlayer().color != HexColor.WHITE) {
            handler.endTurn();
        }
    }

    private void unload() {
        this.world.unload();
        BnW.flush();
    }

    private void load() {
        final WorldList nextWorld = WorldList.values()[this.worldNr];
        this.world = nextWorld.getWorld();
        this.world.load();
        Gdx.graphics.setTitle("BnW - " + nextWorld.name().toLowerCase());
        System.out.println("World " + nextWorld + " loaded");
    }

    public World getWorld() {
        return this.world;
    }
}
