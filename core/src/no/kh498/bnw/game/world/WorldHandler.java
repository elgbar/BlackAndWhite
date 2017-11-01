package no.kh498.bnw.game.world;

import com.badlogic.gdx.Gdx;

/**
 * @author karl henrik
 */
public class WorldHandler {

    private World world;
    private int worldNr = -1;

    public WorldHandler() {
        nextWorld();
    }

    public void nextWorld() {
        this.worldNr++;
        if (this.worldNr == WorldList.values().length) {
            this.worldNr = 0;
        }
        if (this.world != null) {
            this.world.unload();
        }
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
