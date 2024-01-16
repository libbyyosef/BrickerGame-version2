package src.brick_strategies;

import danogl.collisions.GameObjectCollection;

import java.util.Random;

public class DoubledBehaviour extends CollisionStrategy {
    /**
     * double behaviour constructor
     *
     * @param gameObjects game object
     */
    public DoubledBehaviour(GameObjectCollection gameObjects) {
        super(gameObjects);
    }

    /**
     * generate a random behavior
     *
     * @return a random behaviour out of all behaviours (6)
     */
    public StrategyRand randomizeBehaviour() {
        StrategyRand[] collisionStrategies = {StrategyRand.EXTRA_PUCKS, StrategyRand.EXTRA_PUCKS,
                StrategyRand.CAMERA_MOVE, StrategyRand.RETURN_HEART, StrategyRand.DOUBLED_BEHAVIOUR,
                StrategyRand.REGULAR};
        Random rand = new Random();
        return collisionStrategies[rand.nextInt(collisionStrategies.length)];
    }

    /**
     * generate a random behavior out of 5 behaviours(all except from normal)
     *
     * @return a random behaviour out of 5 behaviours(all except from normal)
     */
    public StrategyRand randomizeBehaviourWithoutNormal() {
        StrategyRand[] collisionStrategies = {StrategyRand.EXTRA_PUCKS, StrategyRand.EXTRA_PADDLE,
                StrategyRand.CAMERA_MOVE, StrategyRand.RETURN_HEART, StrategyRand.DOUBLED_BEHAVIOUR};
        Random rand = new Random();
        return collisionStrategies[rand.nextInt(collisionStrategies.length)];
    }


    /**
     * generate a random behavior out of 5 behaviours(all except from double)
     *
     * @return a random behaviour out of 5 behaviours(all except from double)
     */
    public StrategyRand randomizeBehaviourWithoutDouble() {
        StrategyRand[] collisionStrategies = {StrategyRand.EXTRA_PUCKS, StrategyRand.EXTRA_PUCKS,
                StrategyRand.CAMERA_MOVE, StrategyRand.RETURN_HEART, StrategyRand.REGULAR};
        Random rand = new Random();
        return collisionStrategies[rand.nextInt(collisionStrategies.length)];
    }

    /**
     * generate a random behavior out of 4 behaviours(all except from normal and double)
     *
     * @return a random behaviour out of 4 behaviours(all except from normal and double)
     */
    public StrategyRand randomizeBehaviourWithoutDoubleWithoutNormal() {
        StrategyRand[] collisionStrategies = {StrategyRand.EXTRA_PUCKS, StrategyRand.EXTRA_PUCKS,
                StrategyRand.CAMERA_MOVE, StrategyRand.RETURN_HEART};
        Random rand = new Random();
        return collisionStrategies[rand.nextInt(collisionStrategies.length)];
    }

    /**
     * the base case - generate one random behavior from 4 behaviours - all except from normal and double,
     * and one behaviour from 5 behaviours - all except from double, adds them to the bricks strategies array
     *
     * @param brickStrategies array of strategies of a brick
     * @param index           index of the array
     * @param strategyFactory factory to create the behaviours from
     * @return index- the nect location to add behaviour to
     */
    public int base(CollisionStrategy[] brickStrategies, int index, StrategyFactory strategyFactory) {
        brickStrategies[index] = strategyFactory.buildStrategy(randomizeBehaviourWithoutDouble());
        index++;
        brickStrategies[index] =
                strategyFactory.buildStrategy(randomizeBehaviourWithoutDoubleWithoutNormal());
        index++;
        return index;
    }


    /**
     * handles the strategies that will be randomized to each brick, handles their array, consider the
     * probability of each case
     *
     * @param strategy          first strategy that randomized
     * @param collisionStrategy array of brick's strategies
     * @param index             index of the array - the location that the next behaviour will be added to
     * @param strategyFactory   factory of behaviours, create a behaviour according to input
     * @param numOfDoubles      number of double behaviours that have been randomized
     * @param numOfMaxDouble    the maximum possible number of double behaviours in an array
     */
    public void handle(StrategyRand strategy, CollisionStrategy[] collisionStrategy, int index,
                       StrategyFactory strategyFactory, int numOfDoubles, int numOfMaxDouble) {
        if (!(strategy == StrategyRand.DOUBLED_BEHAVIOUR)) {
            collisionStrategy[index] = strategyFactory.buildStrategy(strategy);
            return;
        }
        if (numOfDoubles < numOfMaxDouble) {
            StrategyRand strategy1 = randomizeBehaviourWithoutNormal();
            if (strategy1 == StrategyRand.DOUBLED_BEHAVIOUR) {
                index = base(collisionStrategy, index, strategyFactory);
            } else {
                collisionStrategy[index] = strategyFactory.buildStrategy(randomizeBehaviourWithoutDouble());
                index++;
            }
        }
        collisionStrategy[index] = strategyFactory.buildStrategy(randomizeBehaviourWithoutDouble());
    }
}





