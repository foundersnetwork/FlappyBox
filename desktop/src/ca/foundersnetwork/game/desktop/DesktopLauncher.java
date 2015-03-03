package ca.foundersnetwork.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ca.foundersnetwork.game.FlappyBox;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Flappy Box";
        config.width = 480;
        config.height = 853;


        new LwjglApplication(new FlappyBox(), config);
    }
}