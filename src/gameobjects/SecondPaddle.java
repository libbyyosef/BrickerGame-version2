package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Paddle;

public class SecondPaddle extends Paddle {


    private static Counter counter;
    private final int numCollisionAllowed;

    /**
     * Construct a second paddle instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object. Can be null, in which case
     *                            the GameObject will not be rendered.
     * @param inputListener       the key that the user press on
     * @param windowDimensions    sizes of the paddle
     * @param minDisFromEdge      the minimum distance from edge that the paddle can reach to
     * @param numCollisionAllowed after this number of ball collisions the second paddle will disappear
     */

    public SecondPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        UserInputListener inputListener, Vector2 windowDimensions, int minDisFromEdge, int numCollisionAllowed) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions,
                minDisFromEdge);
        this.numCollisionAllowed = numCollisionAllowed;
        counter = new Counter();
    }

    /**
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     *                  only the ball and puck sholud collide with second paddle
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (!(other instanceof Ball)) {
            return;
        }
        super.onCollisionEnter(other, collision);
        counter.increment();
    }

    public int counter() {
        return counter.value();
    }

    /**
     * @return true if reached the max num of ball collisions , falls otherwise
     */
    public boolean shouldRemove() {
        return counter.value() == numCollisionAllowed;
    }


}
