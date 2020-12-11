import javafx.scene.layout.Pane;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class PresetSpawnPolicy extends SpawnPolicy {
    private final ArrayList<ObstacleStateContainer> obstacleStateContainers;
    private int idx;

    public PresetSpawnPolicy(EntityManager entityManager, Pane sceneGraphRoot, ArrayList<ObstacleStateContainer> obstacleStateContainers) {
        super(entityManager, sceneGraphRoot);
        this.obstacleStateContainers = obstacleStateContainers;
        this.idx = 0;
    }

    @Override
    Obstacle getNextObstacle() {
        ObstacleStateContainer container = obstacleStateContainers.get(idx);
            CompoundObstacle obstacle = null;

            try {
                Class<?> obstacleClass = Class.forName(container.className);
                Constructor<?> constructor = obstacleClass.getConstructor(
                        Vector2D.class,
                        EntityManager.class,
                        Level.class
                );

                obstacle = (CompoundObstacle) constructor.newInstance(
                        container.position,
                        entityManager,
                        container.level
                );
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            idx += 1;
            return obstacle;
    }
}
