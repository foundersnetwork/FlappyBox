package ca.foundersnetwork.game;

import com.badlogic.gdx.Game;

/**
 * Created by abdulkhan on 3/1/15.
 */
public class FlappyBox extends Game {

    private GameScreen gameScreen;


    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {

        // screen management
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }


    public void render() {
        super.render(); //important!
    }

    public void dispose() {

    }

}
