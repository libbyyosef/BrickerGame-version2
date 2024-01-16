package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {
    private final TextRenderable textRenderable;
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectCollection;

    /**constructor of the textual representation of the number of life that left - will be written on board
     * @param livesCounter number of lives that left and will be written on the board
     * @param topLeftCorner location of the number
     * @param dimensions sizes of the number
     * @param gameObjectCollection collection of game objects
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        textRenderable = new TextRenderable(String.valueOf(livesCounter.value()));
        this.renderer().setRenderable(textRenderable);
    }

    /**updates the color of the textual representation according to the number of life that left and
     * update the number of life that left
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int lives = livesCounter.value();
        switch (lives) {
            case 1:
                textRenderable.setColor(Color.red);
                break;
            case 2:
                textRenderable.setColor(Color.yellow);
                break;
            default:
                textRenderable.setColor(Color.green);
                break;
        }
        textRenderable.setString(String.valueOf(lives));
    }

}
