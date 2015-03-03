package ca.foundersnetwork.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static ca.foundersnetwork.game.Constants.*;


/**
 * Created by abdulkhan on 3/1/15.
 */
public class World implements InputProcessor {

    /**
     * Camera used to view different areas of the world.
     */
    private OrthographicCamera gameCamera;

    /**
     * LibGDX class used to display images
     */
    private SpriteBatch batch;

    /**
     * LibGDX class used to maintain aspect ratio
     */
    private Viewport viewport;

    /**
     * Data structure for the "Flappy Box"
     */
    private Player player;

    /**
     * Data structure to represent a group of obstacles. Two obstacles make one gate.
     */
    private ObstacleCollection obstacles;

    /**
     * The gravity in world units.
     */
    private float gravity;

    /**
     * The speed we set the box to jump at when we provide the device input.
     */
    private float jumpVelocity;

    /**
     * The state of the game
     */
    private GameState state;

    /**
     * The width of our view in world units.
     */
    private float viewportWidth;

    /**
     * The height of our view in world units.
     */
    private float viewportHeight;

    public World() {

        // The World class implements InputProcessor, a LibGDX interface used to receive input
        // By configuring this as the InputProcessor for the program, we can detect input from various sources
        Gdx.input.setInputProcessor(this);

        // We set the state of the game to PENDING_START, later to become STARTED
        this.state = GameState.PENDING_START;

        // use the constant values from the "Constants" class for basic values
        this.viewportWidth = VIEWPORT_WIDTH;
        this.viewportHeight = VIEWPORT_HEIGHT;
        this.gravity = GRAVITY;
        this.jumpVelocity = JUMP_VELOCITY;

        // Basic LibGDX classes we need to display our graphics
        this.batch = new SpriteBatch();
        this.gameCamera = new OrthographicCamera();
        this.viewport = new FitViewport(viewportWidth,viewportHeight,gameCamera);
        this.viewport.apply();

        // Initializing the player data structure
        this.player = new Player(viewportWidth / 2 - PLAYER_WIDTH / 2,          // X-Position
                viewportHeight / 2 - PLAYER_HEIGHT / 2,                         // Y-Position
                PLAYER_X_VELOCITY,                                              // X-Velocity
                0.0f,                                                           // Y-Velocity
                PLAYER_WIDTH,                                                   // Width in world units
                PLAYER_HEIGHT);                                                 // Height in world units

        // Initialize our collection of obstacles
        this.obstacles = new ObstacleCollection();

        // Add obstacles to the map
        obstacles.addObstacleGateIfNecessary(gameCamera.position.x + viewportWidth, GAP_SIZE, OBSTACLE_WIDTH, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        // Set the camera to center on the player
        gameCamera.position.set(player.getCenterX(), gameCamera.viewportHeight / 2, 0);
        gameCamera.update();

    }

    /**
     *
     * The update method is where we calculate any changes in our data, due to factors such as User input, or
     * in-game environmental factors such as gravity.
     *
     * @param deltaTime The time, as provided by LibGDX, that it takes for one frame to go by
     */
    public void update(float deltaTime) {

        // We only update positions of objects when the game has actually started
        if (state == GameState.STARTED) {

            // update obstacle collection
            obstacles.addObstacleGateIfNecessary(gameCamera.position.x, GAP_SIZE, OBSTACLE_WIDTH, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

            // update the player's x and y positions according to gravitational acceleration and velocity.
            player.update(deltaTime, gravity);

            // check if the player has hit an obstacle
            if (obstacles.hitPlayer(player)) {
                state = GameState.ENDED;
            }

            // check if the player has fallen off the map
            if (player.getY() < 0 || (player.getY() + player.getHeight()) > VIEWPORT_HEIGHT) {
                state = GameState.ENDED;
            }

            // update the camera to center around the player
            gameCamera.position.set(player.getCenterX(), gameCamera.viewportHeight / 2, 0);
            gameCamera.update();
        }
    }

    public void render() {

        // clear the screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // this operation makes the SpriteBatch convert the world units we used to the pixel units of the window.
        batch.setProjectionMatrix(gameCamera.combined);

        // rendering all objects to the screen
        batch.begin();
        player.render(batch);
        obstacles.renderObstacles(batch);
        batch.end();


    }

    /**
     * If the game is pending start, we start the game if ANY key is pressed. To jump, we require the user to hit the space bar.
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        if (state == GameState.PENDING_START) {
            state = GameState.STARTED;
        } else if (state == GameState.STARTED) {
            if (keycode == Input.Keys.SPACE) {
                player.jump(jumpVelocity);
            }
        }

        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button   @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.  @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
