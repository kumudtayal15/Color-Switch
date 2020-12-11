import javafx.scene.layout.Pane;

abstract public class SpawnPolicy {
    protected static final int NUM_OBSTACLES = 11;

    protected Pane sceneGraphRoot;
    protected EntityManager entityManager;
    protected final String[] obstacleClassNames;
    protected final double[] maxObstacleHeights;

    public SpawnPolicy(EntityManager entityManager, Pane sceneGraphRoot) {


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
    }

    abstract Obstacle getNextObstacle();
}