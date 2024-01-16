package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Heart;

public class ReturnHeart extends CollisionStrategy {

    private final GameObjectCollection gameObjects;
    private final Vector2 dimensions;
    private final Renderable renderable;
    private final Counter graphicLifeCounter;
    private Heart heart;

    /**
     * constucts return heart behaviour
     *
     * @param gameObjects        objects on screen
     * @param dimensions         dimension of heart
     * @param renderable         image of heart
     * @param graphicLifeCounter graphic number of lives
     */
    public ReturnHeart(GameObjectCollection gameObjects, Vector2 dimensions, Renderable renderable,
                       Counter graphicLifeCounter) {
        super(gameObjects);
        this.gameObjects = gameObjects;
        this.dimensions = dimensions;
        this.renderable = renderable;
        this.graphicLifeCounter = graphicLifeCounter;
    }

    /**
     * if the user catch the heart with the paddle it adds him one heart, if it reaches the maximum number
     * of lives it will not add more lives, after the catch the heart disappear
     *
     * @param collideObj    the object that was hit
     * @param colliderObj   the object that hit the other object
     * @param bricksCounter number of bricks on the board
     */
    @Override
    public void OnCollision(GameObject collideObj, GameObject colliderObj, Counter bricksCounter) {
        super.OnCollision(collideObj, colliderObj, bricksCounter);
        heart = new Heart(collideObj.getCenter(), dimensions, renderable, graphicLifeCounter);
        gameObjects.addGameObject(heart, Layer.DEFAULT);
    }
}
