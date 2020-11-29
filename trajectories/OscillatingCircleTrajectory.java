import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

//        this.freq = 2 * Math.pow(Math.abs(speed), 1 / 3.0);     // hyperparameter
        this.freq = 2 * Math.PI;
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

