import javafx.animation.AnimationTimer;
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
    protected int score;
    protected boolean isAlive;
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

        this.isAlive = true;
        this.velocity = 0;
        skin = new Circle(15, color);
        this.parent = parent;

        motionTimer = new AnimationTimer() {
            double prevTimeStamp = System.nanoTime();

            @Override
            public void handle(long l) {

                update((l - prevTimeStamp) * Math.pow(10, -9));
                prevTimeStamp = l;

                if (!isAlive) {
//                    Rectangle rect = new Rectangle(0, 0, 1920, 1080);
//                    rect.setFill(Color.WHITE);
//                    parent.getChildren().add(rect);
//                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), rect);
//                    fadeTransition.setFromValue(1.0);
//                    fadeTransition.setToValue(0.0);
//                    fadeTransition.setCycleCount(Transition.INDEFINITE);
//                    fadeTransition.play();

                    parent.getChildren().remove(skin);
                    parent.getChildren().add(new Emitter(new Vector2D(
                            skin.getTranslateX(),
                            skin.getTranslateY()
                    )));

                    this.stop();
                }
            }
        };
    }

    public void create(Vector2D initialPosition, EntityManager entityManager) {
        entityManager.register(this);
        skin.setTranslateX(initialPosition.x);
        skin.setTranslateY(initialPosition.y - skin.getRadius());
        parent.getChildren().add(skin);

        motionTimer.start();
    }

    public void update(double dt) {
        double dy = velocity * dt + 0.5 * GRAVITY * (dt * dt);
//        skin.setTranslateY(skin.getTranslateY() + dy);
        skin.setTranslateY(Math.min(skin.getTranslateY() + dy, 600));
        velocity += GRAVITY * dt;
    }

    public void impulse(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W) {
            setVelocity(-VELOCITY_UP);
        }
    }

    public double getVelocity() {
        return velocity;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}
