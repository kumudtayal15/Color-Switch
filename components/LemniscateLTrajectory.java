import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

