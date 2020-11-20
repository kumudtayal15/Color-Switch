import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TrajectoryComponent extends Component {
    protected double size;
    protected double speed;
    protected double delay;
    protected double SCENE_WIDTH;
    protected double SCREEN_HEIGHT;

    public TrajectoryComponent(double size, double speed, double delay) {
        this.size = size;
        this.speed = speed;
        this.delay = delay;

        try (InputStream input = new FileInputStream("hyperparameters/display.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            try {
                SCENE_WIDTH = Double.parseDouble(
                        properties.getProperty("sceneWidth"));
                SCREEN_HEIGHT = Double.parseDouble(
                        properties.getProperty("sceneHeight"));
            } catch (NullPointerException e) {
                System.out.println("Error: Invalid property");
                e.printStackTrace();
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setComponentClass(ComponentClass.valueOf("TRAJECTORY"));
    }

    public Vector2D getPositionVector(double timeInSeconds) {
//        stub implementation
        return new Vector2D();
    }
}

class SinWaveTrajectory extends TrajectoryComponent {

    public SinWaveTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
                (size * (speed * timeInSeconds - delay)) % SCENE_WIDTH,
                size * Math.sin(speed * timeInSeconds - delay)
        );
    }
}

class StraightLineTrajectory extends TrajectoryComponent {

    public StraightLineTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
                (speed * timeInSeconds - delay) % SCENE_WIDTH, 0
//                20 * Math.sin(speed * timeInSeconds - delay)
        );
    }
}

class CircleTrajectory extends TrajectoryComponent {

    public CircleTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
                size * Math.cos(speed * timeInSeconds - delay),
                size * Math.sin(speed * timeInSeconds - delay)
        );
    }
}

class OscillatingCircleTrajectory extends TrajectoryComponent {
    // Ratio of max radial distance to min radial distance
    protected double k;       // hyperparameter

    protected double freq;
    protected double shift;
    protected double amp;

    public OscillatingCircleTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);

        try (InputStream input = new FileInputStream("hyperparameters/trajectory.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String propertyName = "oscillatingcircle.radialDistRatio";
            try {
                k = Double.parseDouble(properties.getProperty(propertyName));
            } catch (NullPointerException e) {
                System.out.println("Error: " + propertyName + ": No such property defined");
                e.printStackTrace();
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.freq = 2 * Math.pow(Math.abs(speed), 1 / 3.0);     // hyperparameter
        this.shift = (k + 1) * size / 2.0;
        this.amp = (k - 1) * size / 2.0;
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {

        // Harmonically varying radius
        double harmonicRad = amp * Math.sin(timeInSeconds * freq) + shift;
        return new Vector2D(
                harmonicRad * Math.cos(speed * timeInSeconds - delay),
                harmonicRad * Math.sin(speed * timeInSeconds - delay)
        );
    }
}

class LemniscateRTrajectory extends TrajectoryComponent {
    //    Ratio of 'size' of X component to 'size' of Y component
//    ('size' here refers to the amplitude)
    protected double RATIO;       // hyperparameter

    public LemniscateRTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);

        try (InputStream input = new FileInputStream("hyperparameters/trajectory.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String propertyName = "lemniscate.ratio";
            try {
                RATIO = Double.parseDouble(properties.getProperty(propertyName));
            } catch (NullPointerException e) {
                System.out.println("Error: " + propertyName + ": No such property defined");
                e.printStackTrace();
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
                RATIO * size * Math.abs(Math.cos(speed * timeInSeconds - delay)),
                size * Math.sin(2 * (speed * timeInSeconds - delay))
        );
    }
}

class LemniscateLTrajectory extends TrajectoryComponent {
    //    Ratio of 'size' of X component to 'size' of Y component
//    ('size' here refers to the amplitude)
    protected double RATIO;       // hyperparameter

    public LemniscateLTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);

        try (InputStream input = new FileInputStream("hyperparameters/trajectory.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            String propertyName = "lemniscate.ratio";
            try {
                RATIO = Double.parseDouble(properties.getProperty(propertyName));
            } catch (NullPointerException e) {
                System.out.println("Error: " + propertyName + ": No such property defined");
                e.printStackTrace();
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
                RATIO * size * -Math.abs(Math.cos(speed * timeInSeconds - delay)),
                size * Math.sin(2 * (speed * timeInSeconds - delay))
        );
    }
}