package src.gameobjects;

import danogl.collisions.GameObjectCollection;
import src.brick_strategies.CameraMove;
import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.ExtraPucks;

public class Brick extends GameObject {

    private final Counter counter;
    private CollisionStrategy strategy;
    private CollisionStrategy regularStrategy;
    private CollisionStrategy[] arrayStrategy;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param strategy      the strategy to act when there is collision with the brick and onther object
     * @param counter       number of bricks on the board
     */

    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy strategy
            , Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.strategy = strategy;
        this.counter = counter;
    }

    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy[] strategy, CollisionStrategy regularStrategy
            , Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.arrayStrategy = strategy;
        this.counter = counter;
        this.regularStrategy = regularStrategy;
    }

    /**
     * the method handles with a collision of a brick and another object on the board
     * if a puck hits a brick with camera move behavior - it aill not become active
     * if the camera moves and the ball hits a brick with camera move behavior, it will not become active
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        regularStrategy.OnCollision(this, other, counter);
        for (var strategy1 : arrayStrategy) {
            if (strategy1 != null) {
                if (strategy1 instanceof CameraMove) {
                    if (CameraMove.isCameraMoving()) {
                        continue;
                    }
                    if (other instanceof Puck) {
                        continue;
                    }
                }
                strategy1.OnCollision(this, other, counter);
            }
        }
    }
}
