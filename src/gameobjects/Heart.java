package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Heart extends GameObject {

    private final Counter graphicLifeCounter;
    private boolean gotAHeart = false;

    /**
     * construct heart object
     *
     * @param topLeftCorner      Position of the object, in window coordinates (pixels).
     *                           * Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height in window coordinates.
     * @param renderable         The renderable representing the object. Can be null, in which case
     *                           the GameObject will not be rendered.
     * @param graphicLifeCounter graphic life counter
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 Counter graphicLifeCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.graphicLifeCounter = graphicLifeCounter;
    }

    /**
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     *                  the heart falls down
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setVelocity(Vector2.DOWN.mult(100));
    }

    /**
     * @param other The other GameObject.
     * @return the heart should collide with the main paddle only
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return ((other instanceof Paddle) && (!(other instanceof SecondPaddle)));
    }

    /**
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     *                  if achieved a heart, increment the lives
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (this.shouldCollideWith(other)) {
            gotAHeart = true;
            graphicLifeCounter.increment();
        }
    }

    public Boolean didGetAHeart() {
        /*did catch a heart - true if did, false otherwise*/
        return gotAHeart;
    }
}
