package ca.foundersnetwork.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by abdulkhan on 3/1/15.
 */
public class Player {
    private float x, y, xVel, yVel, width, height;
    private float previousX, previousY;

    /**
     * Used to display our player.
     */
    private Sprite sprite;


    /**
     * Class constructor. Called when an instance of a Player object is created. We will use this to initialize the Player's properties.
     *
     * @param x
     * @param y
     * @param xVel
     * @param yVel
     */
    public Player(float x, float y, float xVel, float yVel,  float width, float height) {

        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
        this.width = width;
        this.height = height;
        this.previousX = x;
        this.previousY = y;

        Texture texture = new Texture(Gdx.files.internal("player.png"));

        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
        this.sprite.setSize(width, height);
    }


    public void update(float deltaTime, float gravity) {

        // update previous "tick" position
        previousX = x;
        previousY = y;

        // update X position (velocity remains constant)
        x += xVel * deltaTime;

        // update Y velocity
        yVel += gravity * deltaTime;

        // update Y position
        y += yVel * deltaTime;

    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }


    public void jump(float yVel) {
        this.yVel = yVel;
    }

    public float getCenterX() {
        return (x + width / 2);
    }

    public float getCenterY() {
        return (y + height / 2);
    }

    public float getPreviousX() {
        return previousX;
    }

    public float getPreviousY() {
        return previousY;
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
