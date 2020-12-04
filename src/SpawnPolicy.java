import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.Properties;

public class SpawnPolicy {
    private double OBSTACLE_BUFFER;
    private final String[] obstacleClassNames;
    private final double[] maxObstacleHeights;
    private final BitSet obstacleCavity;

    public SpawnPolicy() {
        try (InputStream input = new FileInputStream("hyperparameters/anim.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.OBSTACLE_BUFFER = Double.parseDouble(properties.getProperty("obstacle.buffer"));
        } catch (IOException e) {
            System.out.println("I/O Exception occurred");
            e.printStackTrace();
        }

        obstacleClassNames = new String[] {
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

//    public CompoundObstacle generateObstacle() {
//        CompoundObstacle obstacle;
//
//
//        return obstacle;
//    }
}
