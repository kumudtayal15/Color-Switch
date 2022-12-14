import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RandomSpawnPolicy extends SpawnPolicy {
    private final Random pseudoRandomGenerator;
    private final double OBSTACLE_BUFFER;

    public RandomSpawnPolicy(EntityManager entityManager, Pane sceneGraphRoot, ScrollingSystem scrollingSystem, double obstacleBuffer) {
        super(entityManager, sceneGraphRoot);
        this.pseudoRandomGenerator = new Random(System.currentTimeMillis());
        this.OBSTACLE_BUFFER = obstacleBuffer;
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
                    new Vector2D(sceneGraphRoot.getWidth() / 2, -OBSTACLE_BUFFER),
                    entityManager,
                    Level.EASY
            );
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return obstacle;
    }
}
