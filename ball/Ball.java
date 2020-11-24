import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Ball extends GameObject {

    private final Pane parent;
    protected final AnimationTimer motionTimer;
    protected final Circle skin;
    protected Color color;
    protected double velocity;
    protected double VELOCITY_UP;
    protected double GRAVITY;

    public Ball(Pane parent) {
        try (InputStream input = new FileInputStream("hyperparameters/ball.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.GRAVITY = Double.parseDouble(properties.getProperty("gravity"));
            this.VELOCITY_UP = Double.parseDouble(properties.getProperty("speed.final"));
            this.color = Color.web(properties.getProperty("defaultcolor"));
        } catch (IOException e) {
            System.out.println("IO exception occurred");
            e.printStackTrace();
        }

        this.velocity = 0;
        skin = new Circle(20, color);
        this.parent = parent;

        Bounds parentBounds = parent.getLayoutBounds();
        motionTimer = new AnimationTimer() {
            double prevTimeStamp = System.nanoTime();

            @Override
            public void handle(long l) {
                update((l - prevTimeStamp) * Math.pow(10, -9));
                prevTimeStamp = l;

                if (skin.getTranslateY() <= parentBounds.getMinY() + skin.getRadius()) {
                    parent.getChildren().remove(skin);
                    parent.getChildren().add(new Emitter(new Vector2D(
                            (parentBounds.getMinX() + parentBounds.getWidth()) / 2,
                            skin.getRadius()
                    )));

                    this.stop();
                }
            }
        };
    }

    public void create(Vector2D initalPosition) {
        skin.setTranslateX(initalPosition.x);
        skin.setTranslateY(initalPosition.y - skin.getRadius());
        parent.getChildren().add(skin);

        motionTimer.start();
    }

    public void update(double dt) {
        double dy = velocity * dt + 0.5 * GRAVITY * (dt * dt);
        skin.setTranslateY(skin.getTranslateY() + dy);
        velocity += GRAVITY * dt;
    }

    public void impulse(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W) {
            setVelocity(-VELOCITY_UP);
        }
    }

    public void increaseVelocity(double delta) {
        this.velocity += delta;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

}
