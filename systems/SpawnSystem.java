import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Properties;

public class SpawnSystem extends BehaviourSystem {
    private final SpawnPolicy spawnPolicy;
    protected Deque<Obstacle> obstacleDeque;
    private final EntityManager entityManager;
    private final ScrollingSystem scrollingSystem;
    private double OBSTACLE_BUFFER;

    public SpawnSystem(EntityManager entityManager, Pane sceneGraphRoot, ScrollingSystem scrollingSystem) {
        super(entityManager, sceneGraphRoot);

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update(l * Math.pow(10, -9));
            }
        };

        try (InputStream input = new FileInputStream("hyperparameters/anim.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.OBSTACLE_BUFFER = Double.parseDouble(properties.getProperty("obstacle.buffer"));
        } catch (IOException e) {
            System.out.println("I/O Exception occurred");
            e.printStackTrace();
        }

        this.obstacleDeque = new ArrayDeque<>(3);
        this.spawnPolicy = new SpawnPolicy();
        this.entityManager = entityManager;
        this.sceneGraphRoot = sceneGraphRoot;
        this.scrollingSystem = scrollingSystem;
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
//        QuadArcCircle quadArcCircle = new QuadArcCircle(
//                new Vector2D(sceneGraphRoot.getWidth() / 2, -400),
//                entityManager,
//                150,
//                "thick",
//                100
//        );
//        quadArcCircle.create();
//        sceneGraphRoot.getChildren().add(quadArcCircle.getNode());
//        scrollingSystem.add(quadArcCircle.getNode());

//        ParticulateSquare particulateSquare = new ParticulateSquare(
//                new Vector2D(sceneGraphRoot.getWidth() / 2, -200),
//                entityManager,
//                200, 300,
//                16,
//                17
//        );
//        particulateSquare.create();
//        sceneGraphRoot.getChildren().add(particulateSquare.getNode());
//        scrollingSystem.add(particulateSquare.getNode());

//        Lemniscate particleLemniscate = new Lemniscate(
//                new Vector2D(sceneGraphRoot.getWidth() / 2, -300),
//                entityManager,
//                70,
//                1.5,
//                12,
//                12
//        );
//        sceneGraphRoot.getChildren().add(particleLemniscate.getNode());
//        scrollingSystem.add(particleLemniscate.getNode());

        Triangle triangle = new Triangle(
                new Vector2D(sceneGraphRoot.getWidth() / 2, -300),
                entityManager,
                250,
                25,
                -100
        );
        triangle.create(0);
        sceneGraphRoot.getChildren().add(triangle.getNode());
        scrollingSystem.add(triangle.getNode());

        obstacleDeque.addLast(triangle);
    }

    private void removeHead() {
        Obstacle head = obstacleDeque.removeFirst();
        head.markForDeletion(sceneGraphRoot, scrollingSystem, entityManager);
    }
}
