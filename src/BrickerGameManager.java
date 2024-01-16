package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.*;
import src.gameobjects.*;

import java.awt.event.KeyEvent;

public class BrickerGameManager extends GameManager {
    private final static int RESIZE_BALL_BY = 3;
    private static final int PADDLE_WIDTH = 110;
    private static final String PADDLE_PATH = "assets/paddle.png";
    private static final int FRAME_TARGET = 80;
    private static final String BALL_PATH = "assets/ball.png";
    private static final String BALL_SOUND_PATH = "assets/blop.wav";
    private static final float BALL_WIDTH = 20;
    private static final float BALL_SPEED = 100;
    private static final float BALL_HEIGHT = 20;
    private static final float PADDLE_HEIGHT = 15;
    private static final int MIN_DIS_FROM_EDGE = 10;
    private static final int PADDLE_PADDING = 30;
    private static final String HEART_PATH = "assets/heart.png";
    private static final float LIFE_WIDTH = 20;
    private static final float LIFE_HEIGHT = 20;
    private static final float INITIAL_LIFE_X = 10;
    private static final int ROWS_BRICKS = 8;
    private static final int COLS_BRICKS = 7;
    private static final String BRICKS_PATH = "assets/brick.png";
    private static final int BRICK_PADDING_X = 10;
    private static final int BRICK_PADDING_Y = 5;
    private static final float BRICK_HEIGHT = 20;
    private static final float INIT_BRICK_Y_PADDING = 40;
    private static final float INIT_BRICK_X_PADDING = 20;
    private static final float INIT_NUMERIC_WIDTH = 140;
    private static final String BG_PATH = "assets/bg.jpeg";
    private static final String WINNING_MASSAGE = "You win!";
    private static final String LOSING_MASSAGE = "You Lose!";
    private static final String WINDOW_NAME = "GAME";
    private static final float WINDOW_WIDTH = 700;
    private static final float WINDOW_HEIGHT = 500;
    private static final int NUM_OF_BEHAVIOURS = 3;
    private static final String EXTRA_PADDLE_PATH = "assets/botGood.png";
    private static final String PUCK_PATH = "assets/mockBall.png";
    private static final int BALL_CAMERA_COLLISION_NUMBER = 4;
    private static final int WALLS_WIDTH = 10;
    private static final int CEILING_WIDTH = 3;
    private static final int TARGET_NUM = 40;

    private final Counter livesCounter1;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;

    private Brick[][] bricks;
    private final Counter bricksCounter;
    private UserInputListener inputListener1;
    private ExtraPucks extraPucks;
    private ExtraPaddle extraPaddle;
    private Paddle paddle;
    private CameraMove cameraMove;
    private ReturnHeart returnHeart;
    private GraphicLifeCounter graphicLifeCounter;
    private DoubledBehaviour doubledBehaviour;
    private CollisionStrategy collisionStrategy;
    private Vector2 heartDimensions;
    private Renderable heartImage;
    private int numOfLives = 3;
    private Renderable pucksImage;
    private Renderable extraPaddleImage;
    private Renderable returnHeartImage;
    private Vector2 paddleDimensions;

    private Sound collisionSound;


    /**
     * constructor of the game board and logic
     *
     * @param windowTitle      the name on the window
     * @param windowDimensions sizes of the window
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        livesCounter1 = new Counter();
        bricksCounter = new Counter();
    }

    /**
     * initilize all the game object to their starting point
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        /*initialization*/
        this.bricksCounter.reset();
        this.livesCounter1.reset();
        windowController.setTargetFramerate(TARGET_NUM);
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowController.setTargetFramerate(FRAME_TARGET);
        inputListener1 = inputListener;

        /*create ball*/
        createBall(imageReader, soundReader, windowController);

        /*create paddles*/
        Renderable paddleImage = imageReader.readImage(PADDLE_PATH, false);

        /*create paddle*/
        createPaddle(paddleImage, inputListener, windowDimensions);

        /*create hearts*/
        createHearts(imageReader);

        /*creates borders*/
        createBorders(new Vector2(WALLS_WIDTH, windowDimensions.y()));

        /*create numericLifeCounter*/
        createNumericLifeCounter();

        /*create background*/
        createBackground(imageReader);

        /*behaviours*/
        /*extra pucks*/
        pucksImage = imageReader.readImage(PUCK_PATH, true);
        extraPucks = new ExtraPucks(gameObjects(), pucksImage, collisionSound, RESIZE_BALL_BY);

        /*extra paddle*/
        extraPaddleImage = imageReader.readImage(EXTRA_PADDLE_PATH, true);
        extraPaddle = new ExtraPaddle(null, paddleDimensions,
                extraPaddleImage,
                inputListener, windowController.getWindowDimensions(), MIN_DIS_FROM_EDGE, gameObjects());

        /*camera change - move*/
        cameraMove = new CameraMove(gameObjects(), ball, BALL_CAMERA_COLLISION_NUMBER, this);

        /*return heart*/
        returnHeartImage = imageReader.readImage(HEART_PATH, true);
        returnHeart = new ReturnHeart(gameObjects(), new Vector2(LIFE_WIDTH, LIFE_HEIGHT), returnHeartImage, livesCounter1);

        /*create bricks*/
        createBricks(imageReader, windowDimensions);
    }

    /**
     * creates the ball
     *
     * @param imageReader      image of ball
     * @param soundReader      sound when the ball hits an object
     * @param windowController information of the window game
     */
    private void createBall(ImageReader imageReader
            , SoundReader soundReader, WindowController windowController) {
        Renderable ballImage = imageReader.readImage(BALL_PATH, true);
        collisionSound = soundReader.readSound(BALL_SOUND_PATH);
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_WIDTH, BALL_HEIGHT), ballImage, collisionSound);
        windowDimensions = windowController.getWindowDimensions();
        relocateBall();
    }

    /**
     * relocate the ball at the start of the game and when the user loses
     */
    private void relocateBall() {
        ball.setCenter(windowDimensions.mult(0.5f));
        this.gameObjects().addGameObject(ball, Layer.DEFAULT);
        ball.ballRandomDirection(BALL_SPEED);
    }

    /**
     * creates the paddle
     *
     * @param paddleImage      image of the paddle
     * @param inputListener    input from the user if to move right or left
     * @param windowDimensions sizes of paddle
     */
    private void createPaddle(Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions) {
        paddleDimensions = new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle = new Paddle(Vector2.ZERO, paddleDimensions, paddleImage,
                inputListener, windowDimensions, MIN_DIS_FROM_EDGE);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - PADDLE_PADDING));
        gameObjects().addGameObject(paddle, Layer.DEFAULT);
    }


    /**
     * creates the graphic lives
     *
     * @param imageReader image of the lives - a heart
     */
    private void createHearts(ImageReader imageReader) {
        heartImage = imageReader.readImage(HEART_PATH, true);
        heartDimensions = new Vector2(LIFE_WIDTH, LIFE_HEIGHT);
        Vector2 initialVec = Vector2.LEFT.add(new Vector2(INITIAL_LIFE_X,
                windowDimensions.y() - LIFE_HEIGHT - 1));
        graphicLifeCounter = new GraphicLifeCounter(initialVec, heartDimensions,
                this.livesCounter1, heartImage, super.gameObjects(), numOfLives);
        this.gameObjects().addGameObject(graphicLifeCounter, Layer.FOREGROUND);
    }


    /**
     * creates 56 bricks
     *
     * @param imageReader      image of a brick
     * @param windowDimensions sizes of a brick
     *                         each brick gets at least one behaviour
     */
    private void createBricks(ImageReader imageReader, Vector2 windowDimensions) {
        doubledBehaviour = new DoubledBehaviour(gameObjects());
        bricks = new Brick[ROWS_BRICKS][COLS_BRICKS];
        Renderable brickImage = imageReader.readImage(BRICKS_PATH, false);
        float widthOfBrick = (windowDimensions.x()) / ROWS_BRICKS;
        collisionStrategy = new CollisionStrategy(gameObjects());
        Vector2 initialVec = Vector2.LEFT.add(new Vector2(INIT_BRICK_X_PADDING, INIT_BRICK_Y_PADDING));
        for (int i = 0; i < ROWS_BRICKS; i++) {
            for (int j = 0; j < COLS_BRICKS; j++) {
                Vector2 topLeftCorner = initialVec.add(new Vector2(((int) widthOfBrick + BRICK_PADDING_X) * j
                        , ((int) BRICK_HEIGHT + BRICK_PADDING_Y) * i));
                bricks[i][j] =
                        new Brick(topLeftCorner,
                                new Vector2(widthOfBrick, BRICK_HEIGHT), brickImage,
                                strategiesPerBrick(topLeftCorner), new CollisionStrategy(super.gameObjects()),
                                bricksCounter);

                gameObjects().addGameObject(bricks[i][j], Layer.DEFAULT);
                bricksCounter.increment();
            }
        }
    }


    /**
     * creates left and right borders and a ceiling
     *
     * @param vec size of sides
     */
    private void createBorders(Vector2 vec) {
        GameObject leftWall = new GameObject(Vector2.ZERO, vec, null);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(), 0), vec, null);
        GameObject ceiling = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), CEILING_WIDTH),
                null);
        gameObjects().addGameObject(leftWall, Layer.DEFAULT);
        gameObjects().addGameObject(rightWall, Layer.DEFAULT);
        gameObjects().addGameObject(ceiling, Layer.DEFAULT);
    }

    /**
     * creates the text of number of lives
     */
    private void createNumericLifeCounter() {
        Vector2 initialVec = Vector2.LEFT.add(new Vector2(INIT_NUMERIC_WIDTH,
                windowDimensions.y() - LIFE_HEIGHT - 1));
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(livesCounter1, initialVec,
                new Vector2(LIFE_WIDTH, LIFE_HEIGHT),
                super.gameObjects());
        gameObjects().addGameObject(numericLifeCounter, Layer.FOREGROUND);
    }

    /**
     * creates background
     *
     * @param imageReader background image
     */
    private void createBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage(BG_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), windowDimensions
                .y()), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }


    /**
     * check if the game ends - if it is a win or a loose and update the board according to it
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForRemoveExtraPaddle();
        checkCameraMove();
        manageExtraLivesAndErasePucks();
        checkForGameEnd();
    }


    private CollisionStrategy[] strategiesPerBrick(Vector2 topLeftCorner) {
        CollisionStrategy[] collisionStrategies = new CollisionStrategy[NUM_OF_BEHAVIOURS];
        StrategyFactory strategyFactory = new StrategyFactory(topLeftCorner,
                paddleDimensions,
                graphicLifeCounter.getDimensions(), inputListener1, pucksImage, extraPaddleImage, heartImage,
                windowDimensions, MIN_DIS_FROM_EDGE, collisionSound, gameObjects(), livesCounter1,
                RESIZE_BALL_BY, ball, BALL_CAMERA_COLLISION_NUMBER, this
        );
        StrategyRand firstStrategy = doubledBehaviour.randomizeBehaviour();
        int numDoubles = 0;
        if (firstStrategy == StrategyRand.DOUBLED_BEHAVIOUR) {
            numDoubles++;
        }
        doubledBehaviour.handle(firstStrategy, collisionStrategies, 0, strategyFactory, numDoubles,
                NUM_OF_BEHAVIOURS - 1);
        return collisionStrategies;
    }

    /**
     * remove heart object from screen if it was catches or out of boundaries
     * remove a puck if it is out of boundaries
     */
    private void manageExtraLivesAndErasePucks() {
        for (var obj : gameObjects()) {
            if (obj instanceof Heart) {
                if (obj.getCenter().y() > windowController.getWindowDimensions().y()) {
                    /*if heart was not picked up and out of bounds*/
                    gameObjects().removeGameObject(obj, Layer.DEFAULT);
                }
                if (((Heart) obj).didGetAHeart()) {
                    gameObjects().removeGameObject(obj, Layer.DEFAULT);
                }
            }
            if (obj instanceof Puck) {
                if (obj.getCenter().y() > windowDimensions.y()) {
                    gameObjects().removeGameObject(obj, Layer.DEFAULT);
                }
            }
        }
    }

    /**
     * handle camera move if active
     */
    private void checkCameraMove() {

        if (CameraMove.isCameraMoving()) {
            /*camera should start moving but haven't started yet*/
            if (this.getCamera() == null) {
                this.setCamera(new Camera(ball, Vector2.ZERO, windowController.getWindowDimensions().mult(1.2f),
                        windowController.getWindowDimensions()));
            }
            cameraMove.handleCollision();
        }
    }


    /**
     * if the extra paddle reached its maximum number of collisions it will remove it from screen
     */
    private void checkForRemoveExtraPaddle() {
        for (var obj : gameObjects()) {
            if (obj instanceof SecondPaddle) {
                if (((SecondPaddle) obj).shouldRemove()) {
                    gameObjects().removeGameObject(obj, Layer.DEFAULT);
                    extraPaddle.setPaddleNotExist();
                    break;
                }
            }
        }
    }


    /**
     * check if the game ends - if it is a win or a loose, let the user decides if he wants to play again
     * ot not, print a win or loose message to screen, there is a win if there are no more bricks or it the
     * user pressed 'w', if there are no more lives there is a loose
     */
    private void checkForGameEnd() {
        boolean isWin = false;
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if (bricksCounter.value() == 0 || inputListener1.isKeyPressed(KeyEvent.VK_W)) {
            /*user wins*/
            prompt = WINNING_MASSAGE;
            isWin = true;
        }
        if (ballHeight > windowDimensions.y()) {
            /*user lost*/
            prompt = LOSING_MASSAGE;
            livesCounter1.decrement();
            ball.setCenter(windowDimensions.mult(0.5f));
        }
        if (!prompt.isEmpty()) {
            prompt += " Play again?";
            if (livesCounter1.value() == 0 || isWin) {
                if (windowController.openYesNoDialog(prompt)) {
                    windowController.resetGame();
                } else {
                    windowController.closeWindow();
                }
            }
        }
    }

    public static void main(String[] args) {
        new BrickerGameManager(WINDOW_NAME, new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT)).run();
    }
}