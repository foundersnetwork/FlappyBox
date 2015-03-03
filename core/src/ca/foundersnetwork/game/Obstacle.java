package ca.foundersnetwork.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by abdulkhan on 3/1/15.
 */
public class Obstacle {

    private float x, y, width, height;

    /**
     * Used to display the obstacle.
     */
    private Sprite sprite;

    public Obstacle(float x, float y, float width, float height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.sprite = new Sprite(new Texture(Gdx.files.internal("obstacle.png")));
        this.sprite.setBounds(x, y, width, height);
    }


    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
