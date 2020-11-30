import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SpawnSystem extends BehaviourSystem {
    private final SpawnPolicy spawnPolicy;
    private Obstacle topObstacle;
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

        try (InputStream input = new FileInputStream("anim.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.OBSTACLE_BUFFER = Double.parseDouble(properties.getProperty("obstacle.buffer"));
        } catch (IOException e) {
            System.out.println("I/O Exception occurred");
            e.printStackTrace();
        }

        this.spawnPolicy = new SpawnPolicy();
        this.entityManager = entityManager;
        this.sceneGraphRoot = sceneGraphRoot;
        this.scrollingSystem = scrollingSystem;
    }

    @Override
    public void init() {
//        assert topObstacle != null;
        spawnNextObstacle();
        timer.start();
    }

    @Override
    public void update(double t) {
        Bounds topObstacleBounds = topObstacle.getNode().getBoundsInParent();
        if (topObstacleBounds.getMinY() >= 0) {
            spawnNextObstacle();
        }
    }

    public void setTopObstacle(Obstacle topObstacle) {
        this.topObstacle = topObstacle;
    }

    private void spawnNextObstacle() {
//        topObstacle = spawnPolicy.next();
        sceneGraphRoot.getChildren().add(topObstacle.getNode());
        scrollingSystem.add(topObstacle.getNode());
    }
}
