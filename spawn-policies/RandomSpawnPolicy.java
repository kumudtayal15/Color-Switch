import javafx.scene.layout.Pane;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RandomSpawnPolicy extends SpawnPolicy {
    private final Random pseudoRandomGenerator;

    public RandomSpawnPolicy(EntityManager entityManager, Pane sceneGraphRoot) {
        super(entityManager, sceneGraphRoot);
        pseudoRandomGenerator = new Random(System.currentTimeMillis());
    }

    @Override
    Obstacle getNextObstacle() {
        CompoundObstacle obstacle = null;
        try {
            Class<?> obstacleClass = Class.forName(obstacleClassNames[pseudoRandomGenerator.nextInt(NUM_OBSTACLES)]);
            Constructor<?> constructor = obstacleClass.getConstructor(
                    Vector2D.class,
                    EntityManager.class,
                    Level.class
            );

            obstacle = (CompoundObstacle) constructor.newInstance(
                    new Vector2D(sceneGraphRoot.getWidth() / 2, -400),
                    entityManager,
                    Level.EASY
            );
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return obstacle;
    }
}
