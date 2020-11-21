import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

abstract public class TrajectoryComponent extends Component {
    protected double size;
    protected double speed;
    protected double delay;
    protected double SCENE_WIDTH;
    protected double SCREEN_HEIGHT;

    public TrajectoryComponent(double size, double speed, double delay) {
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

        this.size = size;
        this.speed = speed;
        this.delay = delay;
    }

    abstract public Vector2D getPositionVector(double timeInSeconds);

    public double getSize() {
        return size;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDelay() {
        return delay;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }
}
