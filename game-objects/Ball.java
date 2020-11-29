import javafx.animation.AnimationTimer;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class Ball extends GameObject {

    private final Pane parent;
    protected final AnimationTimer motionTimer;
    protected final Shape ballMesh;
    protected final Group ballMeshWrapper;
    protected int score;
    protected boolean isAlive;
    protected Color color;
    protected double velocity;
    protected double VELOCITY_UP;
    protected double GRAVITY;
    private double SCALE_FACTOR;
    private double ICON_SIZE;

    public Ball(Pane parent) {
        final HashMap<String, String> meshSVGMap = new HashMap<>(3);
        meshSVGMap.put("crochet", "M49.85,116.47A91.26,91.26,0,0,0,51.4,214l48-48ZM110,176.62,60.69,225.93a92,92,0,0,0,17.8,15L126.39,193ZM189.92,96.7,120.61,166,137,182.41l67.91-67.91a92,92,0,0,0-15-17.8ZM147.61,193l40.19,40.2a91.38,91.38,0,0,0,24.47-104.85ZM75.25,47.27,91.64,80.05a91.92,91.92,0,0,0-32.83,24.17L110,155.4l68-68a91.1,91.1,0,0,0-13.62-7.36l16.39-32.78A23.51,23.51,0,0,0,207.51,24C207.51,11,197,0,184,0s-23.5,11-23.5,24a23.46,23.46,0,0,0,6.84,16.56L150,75.19a91.51,91.51,0,0,0-44,0L88.66,40.56A23.46,23.46,0,0,0,95.5,24C95.5,11,85,0,72,0S48.5,11,48.5,24A23.51,23.51,0,0,0,75.25,47.27ZM137,203.62,92.35,248.28c28,11.88,56.47,10.2,83.19-6.12Z");
        meshSVGMap.put("tennis", "M256,128a127.15,127.15,0,0,0-37.49-90.51q-4.29-4.29-8.9-8.11c-9.07,28.89-13.85,61.88-13.85,96,0,35.85,5.24,70.27,15.19,100.13q3.9-3.33,7.56-7A127.15,127.15,0,0,0,256,128Zm-75.36-2.64c0-37.53,5.57-73.88,16.12-105.37a128.28,128.28,0,0,0-137.11-.27c10.62,31.56,16.22,68,16.22,105.64,0,39.48-6.12,77.41-17.7,110A128.2,128.2,0,0,0,198.25,235c-11.53-32.48-17.61-70.32-17.61-109.68Zm-119.89,0c0-34.24-4.82-67.35-14-96.31-3.21,2.64-6.32,5.46-9.3,8.44a128,128,0,0,0,0,181q3.84,3.84,8,7.31c10-29.93,15.3-64.47,15.3-100.46Z");
        meshSVGMap.put("billiards", "M128,256A128,128,0,1,0,0,128,128,128,0,0,0,128,256ZM165.49,59.43A68.57,68.57,0,1,1,96.92,128,68.58,68.58,0,0,1,165.49,59.43ZM141.36,143c0,9.79,8.45,20.14,26.81,20.14,16.79,0,27.81-8.9,27.81-22,0-9.23-6.23-15.24-13.35-17.91v-.33c7.23-3.56,10.57-9.68,10.57-15.91,0-8.79-7-18.58-23.92-18.58-14.46,0-25.47,8.12-25.47,20.36,0,6.34,3.45,12.24,10.46,15.91v.22C146.36,128.31,141.36,134.31,141.36,143ZM168.5,99.83c6.23,0,8.79,4.45,8.79,9s-3.45,8-7.34,9.13c-5.79-1.78-10.13-4.9-10.13-9.8C159.83,103.73,162.72,99.83,168.5,99.83Zm-1.11,30.7c6.57,1.79,11.34,5.57,11.34,11.91,0,5.12-4,9-9.9,9a9.9,9.9,0,0,1-10.23-10.12C158.6,136.1,161.83,132.09,167.39,130.53Z");

        String ballMeshSVG = null;

        try (InputStream input = new FileInputStream("hyperparameters/ball.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.GRAVITY = Double.parseDouble(properties.getProperty("gravity"));
            this.VELOCITY_UP = Double.parseDouble(properties.getProperty("impartedvelocity"));
            this.SCALE_FACTOR = Double.parseDouble(properties.getProperty("radius.scalefactor"));
            this.ICON_SIZE  = Double.parseDouble(properties.getProperty("icon.size"));
            this.color = Color.web(properties.getProperty("defaultcolor"));
            ballMeshSVG = meshSVGMap.get(properties.getProperty("ball.type"));
        } catch (IOException e) {
            System.out.println("IO exception occurred");
            e.printStackTrace();
        }

        this.isAlive = true;
        this.velocity = 0;

        ballMeshWrapper = new Group();
//        ballMesh = new Circle(15, color);
        SVGPath path = new SVGPath();
        path.setFill(color);
        path.setContent(ballMeshSVG);
        path.setScaleX(SCALE_FACTOR);
        path.setScaleY(SCALE_FACTOR);
        path.getTransforms().add(new Translate(-(ICON_SIZE / 2) / SCALE_FACTOR, -(ICON_SIZE / 2) / SCALE_FACTOR));
        ballMesh = path;
        ballMeshWrapper.getChildren().add(ballMesh);

        this.parent = parent;

        motionTimer = new AnimationTimer() {
            double prevTimeStamp = System.nanoTime();

            @Override
            public void handle(long l) {

                update((l - prevTimeStamp) * Math.pow(10, -9));
                prevTimeStamp = l;

                if (!isAlive) {
                    parent.getChildren().remove(ballMeshWrapper);
                    parent.getChildren().add(new Emitter(new Vector2D(
                            ballMeshWrapper.getTranslateX(),
                            ballMeshWrapper.getTranslateY()
                    )));

                    this.stop();
                }
            }
        };
    }

    public void create(Vector2D initialPosition, EntityManager entityManager) {
        entityManager.register(this);
        ballMeshWrapper.setTranslateX(initialPosition.x);
        ballMeshWrapper.setTranslateY(initialPosition.y);
        parent.getChildren().add(ballMeshWrapper);

        RotationComponent rotationComponent = new RotationComponent(250, 0, 0);
        entityManager.addComponents(this, rotationComponent);
        ballMeshWrapper.getTransforms().add(rotationComponent.getRotateTransform());

        motionTimer.start();
    }

    public void update(double dt) {
        double dy = velocity * dt + 0.5 * GRAVITY * (dt * dt);
        ballMeshWrapper.setTranslateY(Math.min(ballMeshWrapper.getTranslateY() + dy, 600));
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
