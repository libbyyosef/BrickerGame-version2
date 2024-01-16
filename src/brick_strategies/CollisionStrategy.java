package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;


public class CollisionStrategy {
    private final GameObjectCollection gameObjects;

    /**
     * Collision strategy constructor
     *
     * @param gameObjects objects on the screen
     */
    public CollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * the method handles with a collision of the bricks(and the ball)
     * it deletes the brick that was heated and decrement the number of bricks on the screen
     *
     * @param collideObj    the object that was hit
     * @param colliderObj   the object that hit the other object
     * @param bricksCounter number of bricks on the board
     */
    public void OnCollision(GameObject collideObj, GameObject colliderObj, Counter bricksCounter) {
        if (gameObjects.removeGameObject(collideObj)) {
            gameObjects.removeGameObject(collideObj);
            bricksCounter.decrement();
        }
    }
}
