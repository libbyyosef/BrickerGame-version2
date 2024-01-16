package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.SecondPaddle;

public class ExtraPaddle extends CollisionStrategy {

    private static Boolean isPaddleExist;
    private final UserInputListener inputListener;
    private final Vector2 startLocation;
    private final GameObjectCollection gameObjectCollection;
    private final int numCollisionAllowed = 3;

    private final Vector2 dimensions;
    private final Renderable renderable;
    private final Vector2 windowDimensions;
    private final int minDisFromEdge;
    private SecondPaddle secondPaddle;

    private Vector2 topLeftCorner;

    /**
     * Construct extra paddle behaviour
     *
     * @param topLeftCorner    not used, here because of inheritance
     * @param dimensions       Width and height in window coordinates.
     * @param inputListener    the key that the user press on
     * @param minDisFromEdge   the minimum distance from edge that the paddle can reach to
     * @param renderable       decide if to be seen on screen or not, if seen with a specific image
     * @param windowDimensions window dimensions
     * @param gameObjects      objects on screen
     */


    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,
                       Vector2 windowDimensions, int minDisFromEdge, GameObjectCollection gameObjects) {
        super(gameObjects);
        gameObjectCollection = gameObjects;
        this.dimensions = dimensions;
        this.renderable = renderable;
        this.inputListener = inputListener;
        this.startLocation = windowDimensions.mult((float) 0.5);
        /*start haft screen as asked*/
        this.windowDimensions = windowDimensions;
        this.minDisFromEdge = minDisFromEdge;
        isPaddleExist = false;
    }


    /**
     * handles collision of an object and a brick with extra paddle behaviour, if there is no extra paddle
     * yet, creates one
     *
     * @param collideObj    the object that was hit
     * @param colliderObj   the object that hit the other object
     * @param bricksCounter number of bricks on the board
     */
    public void OnCollision(GameObject collideObj, GameObject colliderObj, Counter bricksCounter) {
        super.OnCollision(collideObj, colliderObj, bricksCounter);
        if (!isPaddleExist) {
            secondPaddle = new SecondPaddle(startLocation, dimensions, renderable, inputListener,
                    windowDimensions, minDisFromEdge, numCollisionAllowed);
            gameObjectCollection.addGameObject(secondPaddle, Layer.DEFAULT);
            isPaddleExist = true;
        }
    }

    public void setPaddleNotExist() {
        /*means that there is no extra paddle currently on screen*/
        isPaddleExist = false;
    }
}
