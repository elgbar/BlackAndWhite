package no.kh498.bnw.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.kh498.bnw.BnW;

public class DesktopLauncher {


    public static void main(final String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.vSyncEnabled = false;
        config.foregroundFPS = 9999;
        config.backgroundFPS = 1;
        config.width = 1366;
        config.height = 768;
        new LwjglApplication(new BnW(), config);
    }
}
