import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class SpawnSystem extends BehaviourSystem {
    protected Deque<Obstacle> obstacleDeque;
    private final EntityManager entityManager;
    private final ScrollingSystem scrollingSystem;
    private SpawnPolicy spawnPolicy;

    public SpawnSystem(EntityManager entityManager, Pane sceneGraphRoot, ScrollingSystem scrollingSystem) {
        super(entityManager, sceneGraphRoot);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update(l * Math.pow(10, -9));
            }
        };

        this.spawnPolicy = new RandomSpawnPolicy(entityManager, sceneGraphRoot);
        this.obstacleDeque = new ArrayDeque<>(3);
        this.entityManager = entityManager;
        this.sceneGraphRoot = sceneGraphRoot;
        this.scrollingSystem = scrollingSystem;
    }

    @Override
    public void init() {
        assert obstacleDeque.peekFirst() != null;
        timer.start();
    }

    @Override
    public void update(double t) {
        Obstacle head = obstacleDeque.getFirst();
        if (head.getNode().getBoundsInParent().getMinY() > sceneGraphRoot.getHeight()) {
            removeHead();
        }

        Obstacle tail = obstacleDeque.getLast();
        if (tail.getNode().getBoundsInParent().getMinY() > 0) {
            spawnNextObstacle();
        }
    }

    private void spawnNextObstacle() {
        CompoundObstacle obstacle = (CompoundObstacle) spawnPolicy.getNextObstacle();

        assert obstacle != null;
        obstacle.create();
        sceneGraphRoot.getChildren().add(obstacle.getNode());
//        System.out.println(spawnPolicy.getClass().getName() + " adding " + obstacle);
        scrollingSystem.add(obstacle.getNode());

        obstacleDeque.addLast(obstacle);

//        System.out.println(spawnPolicy.getClass().getName() + " spawned a new obstacle at " +
//                obstacle.getNode().getBoundsInParent().getCenterY());
    }

    private void removeHead() {
        Obstacle head = obstacleDeque.removeFirst();
        head.markForDeletion(sceneGraphRoot, scrollingSystem, entityManager);
    }

    public ArrayList<ObstacleStateContainer> pack() {
        /*
        Packs obstacleDeque contents into a container
         */

        ArrayList<ObstacleStateContainer> containerArrayList = new ArrayList<>(3);
        ObstacleStateContainer container;

        for (Obstacle o : obstacleDeque) {
            String className = o.getClass().getName();
            Bounds obstacleNodeBounds = o.getNode().getBoundsInParent();
            Vector2D position = new Vector2D(
                    sceneGraphRoot.getWidth() / 2,
                    obstacleNodeBounds.getCenterY()
            );
            Level level = ((CompoundObstacle) o).level;

            container = new ObstacleStateContainer(className, position, level);
            containerArrayList.add(container);
        }

        return containerArrayList;
    }

    public void unpackAndInitialize(ArrayList<ObstacleStateContainer> containerArrayList) {
        /*
        Unpacks container contents to initialize obstacleDeque
         */
        spawnPolicy = new PresetSpawnPolicy(entityManager, sceneGraphRoot, containerArrayList);
        for (int i = 0; i < containerArrayList.size(); i++) {
            spawnNextObstacle();
        }

        spawnPolicy = new RandomSpawnPolicy(entityManager, sceneGraphRoot);
    }
}
