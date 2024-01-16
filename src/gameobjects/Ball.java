package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.CameraMove;

import java.util.Random;

public class Ball extends GameObject {


    private final Sound collisionSound;
    private final Counter ballCameraCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the GameObject will not be rendered.
     * @param collisionSound the sound that being heard when the ball hits something
     */

    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        ballCameraCounter = new Counter();
    }

    /**
     * the method handles with a collision of the ball and another object on the board, when the ball hits
     * something, it flipped and turn the other direction
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        if (CameraMove.isCameraMoving()) {
            ballCameraCounter.increment();
        }
    }

    /**
     * @param other The other GameObject.
     * @return true if other is not heart and false otherwise, heart and ball shpuld not collide
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return !(other instanceof Heart);
    }

    /**
     * choose a random diagonal for start direction of the ball
     *
     * @param ballSpeed ball speed
     */
    public void ballRandomDirection(float ballSpeed) {
        float ballVelX = ballSpeed;
        float ballVelY = ballSpeed;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        setVelocity(new Vector2(ballVelX, ballVelY));
    }

    public int getCollisionCount() {
        return ballCameraCounter.value();
    }

    public void resetCounter() {
        ballCameraCounter.reset();
    }
}
