package no.kh498.bnw.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.kh498.bnw.BnW;

public class DesktopLauncher {


    public static void main(final String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;    //Resizing breaks the width/height ratio of the hexes //TODO fix resizing
        config.vSyncEnabled = true;  //Why not? it's not like this is a competitive FPS
        config.foregroundFPS = 1024; //Basically no limit
        config.backgroundFPS = 10;   //Do not consume unnecessary resources when in the background
        config.width = 1366;
        config.height = 768;
        config.samples = 16; //max out the samples as this isn't a very heavy game.
        config.addIcon("icons\\icon_32.PNG", Files.FileType.Internal);
        new LwjglApplication(new BnW(), config);
    }
}
