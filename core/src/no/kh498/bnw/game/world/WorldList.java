package no.kh498.bnw.game.world;

import no.kh498.bnw.game.world.worlds.Obstacles;
import no.kh498.bnw.game.world.worlds.Simple;

/**
 * @author karl henrik
 */
@SuppressWarnings("GwtInconsistentSerializableClass")
public enum WorldList {
    SIMPLE(new Simple()),
    OBSTACLES(new Obstacles());

    private final World world;

    WorldList(final World world) {
        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }
}
