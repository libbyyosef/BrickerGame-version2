package src.brick_strategies;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;


public class StrategyFactory {
    private final Vector2 topLeftCorner;
    private final Vector2 dimensionsPaddle;
    private final Vector2 dimensionsHeart;
    private final UserInputListener userInputListener;
    private final Renderable renderablePucks;
    private final Renderable renderableExtraPaddle;
    private final Renderable renderableHeart;
    private final Vector2 windowDimensions;
    private final int minDisFromEdge;
    private final GameObjectCollection gameObjects;
    private final Sound collisionSound;
    private final Counter graphicLifeCounter;
    private final int resizePucks;
    private final Ball ball;
    private final int ballCameraCollisionsNumber;
    private final GameManager gameManager;

    /**
     * @param topLeftCorner              top of left corner of paddle
     * @param dimensionsPaddle           dimensions of paddle
     * @param dimensionsHeart            dimensions of heart
     * @param userInputListener          user input
     * @param renderablePucks            image of pucks
     * @param renderableExtraPaddle      image of paddle
     * @param renderableHeart            image of heart
     * @param windowDimensions           window dimensions
     * @param minDisFromEdge             minimum distance from edge
     * @param collisionSound             sound of collision
     * @param gameObjects                objects on screen
     * @param graphicLifeCounter         graphic life counter
     * @param resizePucks                relative size to divide the ball width and set it to a puck size
     * @param ball                       ball
     * @param ballCameraCollisionsNumber maximum number of collisions till the camera reset
     * @param gameManager                game manager
     */
    public StrategyFactory(Vector2 topLeftCorner, Vector2 dimensionsPaddle,
                           Vector2 dimensionsHeart,
                           UserInputListener userInputListener, Renderable renderablePucks,
                           Renderable renderableExtraPaddle, Renderable renderableHeart,
                           Vector2 windowDimensions, int minDisFromEdge,
                           Sound collisionSound, GameObjectCollection gameObjects,
                           Counter graphicLifeCounter, int resizePucks, Ball ball,
                           int ballCameraCollisionsNumber,
                           GameManager gameManager) {

        this.topLeftCorner = topLeftCorner;
        this.dimensionsPaddle = dimensionsPaddle;
        this.dimensionsHeart = dimensionsHeart;
        this.userInputListener = userInputListener;
        this.renderablePucks = renderablePucks;
        this.renderableExtraPaddle = renderableExtraPaddle;
        this.renderableHeart = renderableHeart;
        this.windowDimensions = windowDimensions;
        this.minDisFromEdge = minDisFromEdge;
        this.gameObjects = gameObjects;
        this.collisionSound = collisionSound;
        this.graphicLifeCounter = graphicLifeCounter;
        this.resizePucks = resizePucks;
        this.ball = ball;
        this.ballCameraCollisionsNumber = ballCameraCollisionsNumber;
        this.gameManager = gameManager;
    }


    /**
     * creates an object according to an input
     *
     * @return a behaviour that was randomly chosen out of all the strategies
     */
    public CollisionStrategy buildStrategy(StrategyRand behaviour) {
        CollisionStrategy collisionStrategy = null;
        switch (behaviour) {
            case EXTRA_PUCKS:
                collisionStrategy = new ExtraPucks(gameObjects, renderablePucks, collisionSound, resizePucks);
                return collisionStrategy;
            case EXTRA_PADDLE:
                collisionStrategy = new ExtraPaddle(topLeftCorner, dimensionsPaddle, renderableExtraPaddle, userInputListener,
                        windowDimensions, minDisFromEdge, gameObjects);
                return collisionStrategy;
            case CAMERA_MOVE:
                collisionStrategy = new CameraMove(gameObjects, ball, ballCameraCollisionsNumber, gameManager);
                return collisionStrategy;
            case RETURN_HEART:
                collisionStrategy = new ReturnHeart(gameObjects, dimensionsHeart, renderableHeart,
                        graphicLifeCounter);
                return collisionStrategy;
            case DOUBLED_BEHAVIOUR:
                collisionStrategy = new DoubledBehaviour(gameObjects);
                return collisionStrategy;
            case REGULAR:
                return collisionStrategy;
            default:
                return null;
        }
    }
}
