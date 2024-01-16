package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private static final int PADDING = 2;
    private final GameObject[] hearts;
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectCollection;

    private final int initNumOfLives;
    private final int maxNumOfLives;
    private int numOfLives;


    /**
     * a constructor of the graphic life counter in the game (hearts)
     *
     * @param widgetTopLeftCorner  vector of top left corner
     * @param widgetDimensions     vector of the sizes of a heart(a life)
     * @param livesCounter         counter of the lives that left
     * @param widgetRenderable     the image that will be rendered
     * @param gameObjectCollection the collection of game object ob the board
     * @param numOfLives           the number of lives that left
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions,
                              Counter livesCounter, Renderable widgetRenderable,
                              GameObjectCollection gameObjectCollection, int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, null);
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        this.numOfLives = numOfLives;
        this.initNumOfLives = numOfLives;
        maxNumOfLives = initNumOfLives + 1;
        hearts = new GameObject[maxNumOfLives];
        /*adding the life graphic object to the board and update its counter*/
        for (int i = 0; i < maxNumOfLives; i++) {

            Vector2 vecMult =
                    widgetTopLeftCorner.add(new Vector2((int) (i * (PADDING + widgetDimensions.x())), 0));
            hearts[i] = new GameObject(vecMult,
                    widgetDimensions, widgetRenderable);
            if (i + 1 != maxNumOfLives) {
                livesCounter.increment();
                this.gameObjectCollection.addGameObject(hearts[i], Layer.FOREGROUND);
            }
        }


    }


    /**
     * the method update the graphic lives representation on the board according to the number of lives
     * that left
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        while (livesCounter.value() > maxNumOfLives) {
            livesCounter.decrement();
        }
        while (numOfLives > livesCounter.value()) {
            /*update strike-losing a life*/
            numOfLives--;
            this.gameObjectCollection.removeGameObject(this.hearts[numOfLives], Layer.FOREGROUND);

        }
        if (numOfLives < livesCounter.value() && livesCounter.value() <= maxNumOfLives) {
            this.gameObjectCollection.addGameObject(this.hearts[numOfLives], Layer.FOREGROUND);
            numOfLives++;

        }
    }

    public int counter() {
        return livesCounter.value();
    }
}
