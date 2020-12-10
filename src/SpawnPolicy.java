import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.Properties;

abstract public class SpawnPolicy {
    protected static final int NUM_OBSTACLES = 11;

    protected Pane sceneGraphRoot;
    protected EntityManager entityManager;
    protected double OBSTACLE_BUFFER;
    protected final String[] obstacleClassNames;
    protected final double[] maxObstacleHeights;
    protected final BitSet obstacleCavity;

    public SpawnPolicy(EntityManager entityManager, Pane sceneGraphRoot) {
        try (InputStream input = new FileInputStream("hyperparameters/anim.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.OBSTACLE_BUFFER = Double.parseDouble(properties.getProperty("obstacle.buffer"));
        } catch (IOException e) {
            System.out.println("I/O Exception occurred");
            e.printStackTrace();
        }

        this.sceneGraphRoot = sceneGraphRoot;
        this.entityManager = entityManager;

        obstacleClassNames = new String[]{
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
        maxObstacleHeights = new double[5];
        obstacleCavity = new BitSet();
    }

    abstract Obstacle getNextObstacle();
}