package ca.foundersnetwork.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by abdulkhan on 3/1/15.
 */
public class ObstacleCollection {

    /**
     * A resizable array of obstacles. Essentially a LibGDX implementation of a Java ArrayList
     */
    private Array<Obstacle> obstacles;

    public ObstacleCollection() {
        this.obstacles = new Array<Obstacle>(0);
    }


    /**
     *
     * If there is no obstacles in the array or the player has passed the previous one, this function will create a new obstacle gate ahead of the camera's path.
     *
     * @param cameraX The current position of the camera
     * @param gapSize The desired gap between the two obstacles
     * @param width The desired width of the obstacles
     * @param viewportWidth The viewport width in world units
     * @param viewportHeight The viewport width in world units
     */
    public void addObstacleGateIfNecessary(float cameraX, float gapSize, float width, float viewportWidth, float viewportHeight) {

        // If empty
        if (obstacles.size == 0) {
            createRandomObstacleGate(cameraX + viewportWidth, width, gapSize, viewportHeight);
        }

        // If the closest obstacle to the player is behind it.
        else if (obstacles.get(obstacles.size - 1).getX() <= cameraX) {
            createRandomObstacleGate(cameraX + viewportWidth, width, gapSize, viewportHeight);
        }
    }

    /**
     *
     * Determines if there has been contact with the player.
     *
     * @param player
     * @return
     */
    public boolean hitPlayer(Player player) {
        Rectangle playerRect = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());

        // Challenge question: why do I iterate through the list backwards? What can I do to further improve memory management?
        for (int i = obstacles.size - 1; i >= 0; i--) {

            Obstacle obs = obstacles.get(i);
            Rectangle obstacleRect = new Rectangle(obs.getX(), obs.getY(), obs.getWidth(), obs.getHeight());

            if (obstacleRect.overlaps(playerRect)) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * Creates the Flappy Bird "obstacle gate" we have all grown to hate over time.
     * This is probably the trickiest part of the game to make because of the randomness involved in creating the gates
     *
     * @param x The x position of the gate
     * @param width The desired width of the gate
     * @param gapSize The desired gap between the top and bottom obstacles
     * @param viewportHeight The height of the viewport
     */
    private void createRandomObstacleGate(float x, float width, float gapSize, float viewportHeight) {

        // LibGDX random implementation
        RandomXS128 random = new RandomXS128();

        // The y-coordinate of the top edge of the bottom gate, chosen randomly
        float bottomGateTopEdge = random.nextFloat() * (viewportHeight - gapSize);

        // The y-coordinate of the bottom edge of the top gate, chosen according to the bottom gates position and the gap size.
        float topGateBottomEdge = bottomGateTopEdge + gapSize;

        // create the obstacles by identifying expressions for the X and Y coordinates, the widths and the heights.
        Obstacle bottom = new Obstacle(x, 0, width, bottomGateTopEdge);
        Obstacle top = new Obstacle(x, topGateBottomEdge, width, viewportHeight - topGateBottomEdge);

        // add the newly created obstacles to our array
        obstacles.add(bottom);
        obstacles.add(top);

    }

    /**
     * Draws the obstacles on the screen.
     *
     * @param batch
     */
    public void renderObstacles(SpriteBatch batch) {

        // Challenge question: how can I increase the efficiency of this function? What happens as we add more Obstacles.
        for (int i = 0; i < obstacles.size; i ++){
            obstacles.get(i).render(batch);
        }
    }

    /**
     *
     * @return The number of obstacles in the collection.
     */
    public int size() {
        return obstacles.size;
    }


    /**
     * Challenge: figure out a way to remove the the obstacles that the camera has already passed!
     */
    public void removeObstacles() {

    }

}
