import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SpawnSystem extends BehaviourSystem {
    private final SpawnPolicy spawnPolicy;
    protected Deque<Obstacle> obstacleDeque;
    private final EntityManager entityManager;
    private final ScrollingSystem scrollingSystem;
    private double OBSTACLE_BUFFER;
    private final String[] obstacleClassNames;
    private final double[] maxObstacleHeights;
    private final BitSet obstacleCavity;
    private final Random pseudoRandomGenerator;


    public SpawnSystem(EntityManager entityManager, Pane sceneGraphRoot, ScrollingSystem scrollingSystem) {
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


        this.obstacleDeque = new ArrayDeque<>(3);
        this.spawnPolicy = new SpawnPolicy();
        this.entityManager = entityManager;
        this.sceneGraphRoot = sceneGraphRoot;
        this.scrollingSystem = scrollingSystem;
        this.obstacleClassNames = new String[]{
                Cartwheel.class.getName(),
                EightPointStar.class.getName(),
                QuadArcCircle.class.getName(),
                Rhombus.class.getName(),
                Triangle.class.getName(),
                HarmonicCircle.class.getName(),
                Lemniscate.class.getName(),
                ParticulateCircle.class.getName(),
                ParticulateSquare.class.getName(),
                ParticulateTriangle.class.getName(),
                ParticulateHex.class.getName(),
        };
        this.maxObstacleHeights = new double[5];
        this.obstacleCavity = new BitSet();
        this.pseudoRandomGenerator = new Random();
    }

    @Override
    public void init() {
        assert obstacleDeque.peekFirst() != null;
        spawnNextObstacle();
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
        CompoundObstacle obstacle = null;
        try {
            Class<?> obstacleClass = Class.forName(obstacleClassNames[pseudoRandomGenerator.nextInt(11)]);
            Constructor<?> constructor = obstacleClass.getConstructor(
                    Vector2D.class,
                    EntityManager.class,
                    Level.class
            );

            obstacle = (CompoundObstacle) constructor.newInstance(
                    new Vector2D(sceneGraphRoot.getWidth() / 2, -300),
                    entityManager,
                    Level.EASY
            );
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        assert obstacle != null;
        obstacle.create();
        sceneGraphRoot.getChildren().add(obstacle.getNode());
        scrollingSystem.add(obstacle.getNode());

        obstacleDeque.addLast(obstacle);
    }

    private void removeHead() {
        Obstacle head = obstacleDeque.removeFirst();
        head.markForDeletion(sceneGraphRoot, scrollingSystem, entityManager);
    }
}
