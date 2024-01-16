package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Puck;

public class ExtraPucks extends CollisionStrategy {

    private static final int PUCKS_AMOUNT = 1;
    private static final int PUCK_SPEED = 250;
    private static final int NUM_OF_PUCKS = 1;
    private final Ball[] pucks = new Puck[PUCKS_AMOUNT];
    private final Renderable renderable;
    private final Sound collisionSound;
    private final int resizePuck;
    private final GameObjectCollection gameObjects;


    /**
     * constructs extra pucks behaviour
     *
     * @param gameObjects    objects on screen
     * @param renderable     image of puck
     * @param collisionSound sound of collision
     * @param resizePuck     number  (1/resizePuck) relative to ball size and the puck size
     */
    public ExtraPucks(GameObjectCollection gameObjects, Renderable renderable,
                      Sound collisionSound, int resizePuck) {
        super(gameObjects);
        this.gameObjects = gameObjects;
        this.renderable = renderable;
        this.collisionSound = collisionSound;
        this.resizePuck = resizePuck;
    }

    /**
     * handles collision of an object and a brick with extra pucks' behaviour, will create new pucks on
     * screen, and they will mostly act like the ball (movement(
     *
     * @param collideObj    the object that was hit
     * @param colliderObj   the object that hit the other object
     * @param bricksCounter number of bricks on the screen
     */
    @Override
    public void OnCollision(GameObject collideObj, GameObject colliderObj, Counter bricksCounter) {
        super.OnCollision(collideObj, colliderObj, bricksCounter);
        for (int i = 0; i < NUM_OF_PUCKS; i++) {
            pucks[i] = new Puck(collideObj.getTopLeftCorner(),
                    new Vector2(collideObj.getDimensions().x(), collideObj.getDimensions().y()), renderable,
                    collisionSound, resizePuck);
            gameObjects.addGameObject(pucks[i], Layer.DEFAULT);
            pucks[i].ballRandomDirection(PUCK_SPEED);
        }

    }
}

