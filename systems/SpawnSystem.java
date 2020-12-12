import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SpawnSystem extends BehaviourSystem implements PlayerDeathSubscriber {
    protected Deque<Obstacle> obstacleDeque;
    private final EntityManager entityManager;
    private final ScrollingSystem scrollingSystem;
    private SpawnPolicy spawnPolicy;
    protected double OBSTACLE_BUFFER;
    protected Random pseudoRandomGenerator;
    protected Color[] customColorMapping;

    public SpawnSystem(EntityManager entityManager, Pane sceneGraphRoot, ScrollingSystem scrollingSystem, Color[] customColorMapping) {
        super(entityManager, sceneGraphRoot);

        try (InputStream input = new FileInputStream("hyperparameters/anim.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.OBSTACLE_BUFFER = Double.parseDouble(properties.getProperty("obstacle.buffer"));
        } catch (IOException e) {
            System.out.println("I/O Exception occurred");
            e.printStackTrace();
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update(l * Math.pow(10, -9));
            }
        };

        this.customColorMapping = customColorMapping;
        this.spawnPolicy = new RandomSpawnPolicy(entityManager, sceneGraphRoot, scrollingSystem, OBSTACLE_BUFFER);
        this.obstacleDeque = new ArrayDeque<>(3);
        this.entityManager = entityManager;
        this.sceneGraphRoot = sceneGraphRoot;
        this.scrollingSystem = scrollingSystem;

        this.pseudoRandomGenerator = new Random(System.currentTimeMillis());
    }

    @Override
    public void init() {
        assert obstacleDeque.peekFirst() != null;
//        spawnNextObstacle();
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
        ColorSwitcher colorSwitcher = new ColorSwitcher(
                // TODO: 12-12-2020 dynamic distance calculation
                new Vector2D(sceneGraphRoot.getWidth() / 2, -OBSTACLE_BUFFER / 4),
                entityManager,
                customColorMapping
        );
        sceneGraphRoot.getChildren().add(colorSwitcher.getNode());
        scrollingSystem.add(colorSwitcher.getNode());


        CompoundObstacle obstacle = (CompoundObstacle) spawnPolicy.getNextObstacle();
        assert obstacle != null;
        obstacle.setColorMapping(customColorMapping);
        obstacle.create(colorSwitcher.getDeltaColorIdx());
        Vector2D obstacleCenter = obstacle.getCentre();


        if (obstacle.isHollow) {
            int toss = pseudoRandomGenerator.nextInt(3);
            SVGCollectible collectible;

            switch (toss) {
                case 0:
                    collectible = new Star(entityManager, obstacleCenter);
                    break;
                case 1:
                    collectible = new RandomRotate(entityManager, obstacleCenter);
                    break;
                case 2:
                    collectible = new SkinChanger(entityManager, obstacleCenter);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + toss);
            }

            sceneGraphRoot.getChildren().add(collectible.getNode());
            scrollingSystem.add(collectible.getNode());
        }


        sceneGraphRoot.getChildren().add(obstacle.getNode());
        scrollingSystem.add(obstacle.getNode());
        obstacleDeque.addLast(obstacle);
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

        spawnPolicy = new RandomSpawnPolicy(entityManager, sceneGraphRoot, scrollingSystem, OBSTACLE_BUFFER);
    }

    @Override
    public void onPlayerDeath() {
        this.timer.stop();
    }
}
