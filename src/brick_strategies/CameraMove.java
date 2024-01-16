package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import src.gameobjects.Ball;
import src.gameobjects.Puck;

public class CameraMove extends CollisionStrategy {
    private static boolean cameraMove;
    private final GameObjectCollection gameObjects;
    private final Ball ball;
    private final int ballCameraCollisionsNumber;
    private final GameManager gameManager;

    /**
     * constructor of camera move behaviour
     *
     * @param gameObjects                all the objects on the board (ball,paddle ect.)
     * @param ball                       green ball
     * @param ballCameraCollisionsNumber while the camera is moving, this number will be the maximum number
     *                                   of objects which the ball can collide and then the camera reset
     * @param gameManager                manage the game
     */
    public CameraMove(GameObjectCollection gameObjects, Ball ball, int ballCameraCollisionsNumber,
                      GameManager gameManager) {
        super(gameObjects);
        this.gameObjects = gameObjects;
        this.ball = ball;
        this.ballCameraCollisionsNumber = ballCameraCollisionsNumber;
        this.gameManager = gameManager;
        cameraMove = false;
    }

    /**
     * this method is active when the main ball hits a brick with 'camera move' behaviour, moves the camera
     * till the ball hits X num of objects.
     *
     * @param collideObj    the object that was hit
     * @param colliderObj   the object that hit the other object
     * @param bricksCounter number of bricks on the board
     */
    @Override
    public void OnCollision(GameObject collideObj, GameObject colliderObj, Counter bricksCounter) {
        super.OnCollision(collideObj, colliderObj, bricksCounter);
        if (!(colliderObj instanceof Puck)) {
            cameraMove = true;
        }
    }

    public static boolean isCameraMoving() {
        /*returns true if the camera is active and moves, false otherwise*/
        return cameraMove;
    }

    /**
     * stops the camera movements and resets ball counter when the ball hits X objects while the camera moved
     */
    public void handleCollision() {
        if (ball.getCollisionCount() >= ballCameraCollisionsNumber) {
            this.setCameraStopMoving();
            gameManager.setCamera(null);
            ball.resetCounter();
        }
    }

    public void setCameraStopMoving() {
        /*sets the camera move to false when the camera stop moving*/
        cameraMove = false;
    }
}
