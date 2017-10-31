package no.kh498.bnw.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.kh498.bnw.BnW;

public class DesktopLauncher {


    public static void main(final String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.vSyncEnabled = false;
        config.foregroundFPS = 300;
        config.backgroundFPS = 10;
        config.width = 1366;
        config.addIcon("icons\\icon_32.PNG", Files.FileType.Internal);
        config.height = 768;
        new LwjglApplication(new BnW(), config);
    }
}
