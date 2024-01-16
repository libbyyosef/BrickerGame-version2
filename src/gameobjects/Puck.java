package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Puck extends Ball {
    private Sound collisionSound;

    /**
     * Construct a new puck instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the GameObject will not be rendered.
     * @param collisionSound the sound that being heard when the ball hits something
     * @param resizeDivideBy the number to divide ball width and set it to puck
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                float resizeDivideBy) {
        super(topLeftCorner, new Vector2(dimensions.x() / resizeDivideBy, dimensions.y()), renderable,
                collisionSound);
        this.collisionSound = collisionSound;
    }
}
