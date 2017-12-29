package no.kh498.bnw.game.world;

import no.kh498.bnw.game.world.worlds.Beginner;
import no.kh498.bnw.game.world.worlds.Large;
import no.kh498.bnw.game.world.worlds.Obstacles;
import no.kh498.bnw.game.world.worlds.Simple;

/**
 * @author karl henrik
 */
@SuppressWarnings("GwtInconsistentSerializableClass")
public enum WorldList {
    BEGINNER(new Beginner()),
    SIMPLE(new Simple()),
    OBSTACLES(new Obstacles()),
    //    WIN_TEST(new WinTest()),
    LARGE(new Large());

    private final World world;

    WorldList(final World world) {
        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }
}
