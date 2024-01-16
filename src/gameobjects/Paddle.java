package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 470;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDisFromEdge;

    /**
     * Construct a paddle instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener    the key that the user press on
     * @param windowDimensions sizes of the paddle
     * @param minDisFromEdge   the minimum distance from edge that the paddle can reach to
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable
            , UserInputListener inputListener, Vector2 windowDimensions, int minDisFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDisFromEdge = minDisFromEdge;
    }

    /**
     * moves the paddle left and right according to the user input and does not go beyond the borders
     *
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
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if ((this.getTopLeftCorner().x() - minDisFromEdge) > 0) {
                movementDir = movementDir.add(Vector2.LEFT);
            }
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if ((this.getTopLeftCorner().x() + this.getDimensions().x()) + minDisFromEdge < windowDimensions.x()) {
                movementDir = movementDir.add(Vector2.RIGHT);
            }
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
