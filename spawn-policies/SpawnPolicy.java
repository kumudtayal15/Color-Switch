import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

abstract public class SpawnPolicy {
    protected static final int NUM_OBSTACLES = 12;

    protected Pane sceneGraphRoot;
    protected EntityManager entityManager;
    protected final String[] obstacleClassNames;

    public SpawnPolicy(EntityManager entityManager, Pane sceneGraphRoot) {

        this.sceneGraphRoot = sceneGraphRoot;
        this.entityManager = entityManager;

        obstacleClassNames = new String[]{
//                ParticulateSinWave.class.getName(),
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
                CircleAndCartwheel.class.getName(),
                CartwheelPair.class.getName(),
        };
    }

    abstract Obstacle getNextObstacle();
}