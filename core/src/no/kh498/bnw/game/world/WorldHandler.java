package no.kh498.bnw.game.world;

/**
 * @author karl henrik
 */
public class WorldHandler {

    World world;
    int worldnr = -1;

    public WorldHandler() {
        nextWorld();
    }

    public void nextWorld() {
        this.worldnr++;
        if (this.worldnr == WorldList.values().length) {
            this.worldnr = 0;
        }
        if (this.world != null) {
            this.world.unload();
        }
        this.world = WorldList.values()[this.worldnr].getWorld();
        this.world.load();
    }

    public World getWorld() {
        return this.world;
    }
}
