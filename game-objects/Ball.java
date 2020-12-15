import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Ball extends GameObject {

    private final Pane sceneGraphRoot;
    protected final AnimationTimer motionTimer;
    protected final Shape ballMesh;
    protected final Group ballMeshWrapper;
    protected int score;
    protected boolean isAlive;
    private final ArrayList<PlayerDeathSubscriber> deathSubscribers;
    protected Color color;
    protected double velocity;
    protected final HashMap<String, String> meshSVGMap;
    protected double VELOCITY_UP;
    private double GRAVITY;
    protected double lowerBound;
    private double SCALE_FACTOR;
    private double ICON_SIZE;
    private final Color[] colorMapping;
    protected boolean hasStarted;

    public Ball(Pane sceneGraphRoot, Color[] colorMapping) {
        meshSVGMap = new HashMap<>(6);
        meshSVGMap.put("crochet", "M49.85,116.47A91.26,91.26,0,0,0,51.4,214l48-48ZM110,176.62,60.69,225.93a92,92,0,0,0,17.8,15L126.39,193ZM189.92,96.7,120.61,166,137,182.41l67.91-67.91a92,92,0,0,0-15-17.8ZM147.61,193l40.19,40.2a91.38,91.38,0,0,0,24.47-104.85ZM75.25,47.27,91.64,80.05a91.92,91.92,0,0,0-32.83,24.17L110,155.4l68-68a91.1,91.1,0,0,0-13.62-7.36l16.39-32.78A23.51,23.51,0,0,0,207.51,24C207.51,11,197,0,184,0s-23.5,11-23.5,24a23.46,23.46,0,0,0,6.84,16.56L150,75.19a91.51,91.51,0,0,0-44,0L88.66,40.56A23.46,23.46,0,0,0,95.5,24C95.5,11,85,0,72,0S48.5,11,48.5,24A23.51,23.51,0,0,0,75.25,47.27ZM137,203.62,92.35,248.28c28,11.88,56.47,10.2,83.19-6.12Z");
        meshSVGMap.put("tennis", "M256,128a127.15,127.15,0,0,0-37.49-90.51q-4.29-4.29-8.9-8.11c-9.07,28.89-13.85,61.88-13.85,96,0,35.85,5.24,70.27,15.19,100.13q3.9-3.33,7.56-7A127.15,127.15,0,0,0,256,128Zm-75.36-2.64c0-37.53,5.57-73.88,16.12-105.37a128.28,128.28,0,0,0-137.11-.27c10.62,31.56,16.22,68,16.22,105.64,0,39.48-6.12,77.41-17.7,110A128.2,128.2,0,0,0,198.25,235c-11.53-32.48-17.61-70.32-17.61-109.68Zm-119.89,0c0-34.24-4.82-67.35-14-96.31-3.21,2.64-6.32,5.46-9.3,8.44a128,128,0,0,0,0,181q3.84,3.84,8,7.31c10-29.93,15.3-64.47,15.3-100.46Z");
        meshSVGMap.put("billiards", "M128,256A128,128,0,1,0,0,128,128,128,0,0,0,128,256ZM165.49,59.43A68.57,68.57,0,1,1,96.92,128,68.58,68.58,0,0,1,165.49,59.43ZM141.36,143c0,9.79,8.45,20.14,26.81,20.14,16.79,0,27.81-8.9,27.81-22,0-9.23-6.23-15.24-13.35-17.91v-.33c7.23-3.56,10.57-9.68,10.57-15.91,0-8.79-7-18.58-23.92-18.58-14.46,0-25.47,8.12-25.47,20.36,0,6.34,3.45,12.24,10.46,15.91v.22C146.36,128.31,141.36,134.31,141.36,143ZM168.5,99.83c6.23,0,8.79,4.45,8.79,9s-3.45,8-7.34,9.13c-5.79-1.78-10.13-4.9-10.13-9.8C159.83,103.73,162.72,99.83,168.5,99.83Zm-1.11,30.7c6.57,1.79,11.34,5.57,11.34,11.91,0,5.12-4,9-9.9,9a9.9,9.9,0,0,1-10.23-10.12C158.6,136.1,161.83,132.09,167.39,130.53Z");
        meshSVGMap.put("ufo", "M242.86,122.93c8.28-25.61,2.32-62.17-22.6-87.09-18-18-42.1-26.14-63.74-26.14a75.76,75.76,0,0,0-24.24,3.81C95-5,62.35-4.13,44.41,13.81c-13,13-16.41,33.48-11.51,57l-8.1,8.1a19.14,19.14,0,0,0-5.05-.74A19.76,19.76,0,1,0,39.51,97.91a20.14,20.14,0,0,0-.73-5.06l.62-.62c8.56,22,23.35,45.34,43.39,67L54.45,187.57a19.72,19.72,0,1,0,14.69,19,20.14,20.14,0,0,0-.73-5.06L96.74,173.2c21.61,20.05,44.89,34.87,66.89,43.46l-.54.55a20.08,20.08,0,1,0,14,14l8-8c23.6,5,44.17,1.62,57.23-11.46C260.22,193.81,260.73,159.9,242.86,122.93ZM207,49.17c26.09,26.08,24.67,66.76,11,80.41-7.07,7.07-14.3,10.51-22.09,10.51-13.77,0-31.2-10.17-50.43-29.42-45.68-45.7-26.2-65.2-18.87-72.54,6-6,17.2-9.59,29.93-9.59A71.71,71.71,0,0,1,207,49.17Z");
        meshSVGMap.put("ghost", "M250.83,87.53a18.42,18.42,0,0,0-21.56-3.77l-13.11,6.53-1.93-14.68a87,87,0,0,0-172.45,0L39.84,90.31,26.73,83.78A18.32,18.32,0,0,0,5.16,87.53a18.12,18.12,0,0,0-2.76,21.7l28.25,50.84-9.1,69.2A18.42,18.42,0,0,0,48.46,248l21.65-11.5a12.83,12.83,0,0,1,11.77-.15L115.08,253a29,29,0,0,0,25.81,0l33.2-16.61a12.85,12.85,0,0,1,11.79.15L207.51,248a18.43,18.43,0,0,0,26.92-18.7l-9.12-69.22,28.25-50.83a18.07,18.07,0,0,0-2.73-21.71ZM160,80.08a16,16,0,1,1-16,16A16,16,0,0,1,160,80.08Zm-64,0a16,16,0,1,1-16,16A16,16,0,0,1,96,80.08Zm85.32,74.58a26.66,26.66,0,1,1-53.32,0H82.67a8,8,0,1,1,0-16h90.65a8,8,0,0,1,8,8Z");
        meshSVGMap.put("broken", "M149.42,63.66A10.71,10.71,0,0,1,138.71,53V30.46a8.58,8.58,0,0,0-8.57-8.55h-4.28a8.58,8.58,0,0,0-8.57,8.55V53a10.71,10.71,0,0,1-21.42,0V30.46a30,30,0,0,1,30-29.93h4.28a30,30,0,0,1,30,29.93V53A10.71,10.71,0,0,1,149.42,63.66ZM56.53,100.77a5.37,5.37,0,0,1,6.9.57L88,125.86a5.35,5.35,0,0,0,7.27.28l29.25-25a5.35,5.35,0,0,1,7,0l29.25,25a5.35,5.35,0,0,0,7.27-.28l24.56-24.52a5.37,5.37,0,0,1,6.9-.57l32.34,23.07a107,107,0,0,0-207.6,0Zm142.94,41.64a5.36,5.36,0,0,0-6.9.56L168,167.49a5.35,5.35,0,0,1-7.27.28l-29.25-25a5.35,5.35,0,0,0-7,0l-29.25,25a5.35,5.35,0,0,1-7.27-.28L63.44,143a5.36,5.36,0,0,0-6.9-.56L22.41,166.72a107,107,0,0,0,211.18,0Z");

        String ballMeshSVG = null;

        try (InputStream input = new FileInputStream("hyperparameters/ball.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.GRAVITY = Double.parseDouble(properties.getProperty("gravity"));
            this.VELOCITY_UP = Double.parseDouble(properties.getProperty("impartedvelocity"));
            this.SCALE_FACTOR = Double.parseDouble(properties.getProperty("radius.scalefactor"));
            this.ICON_SIZE = Double.parseDouble(properties.getProperty("icon.size"));
            ballMeshSVG = meshSVGMap.get(properties.getProperty("ball.type"));
        } catch (IOException e) {
            System.out.println("IO exception occurred");
            e.printStackTrace();
        }

        this.colorMapping = colorMapping;
        this.color = colorMapping[0];
        this.isAlive = true;
        this.deathSubscribers = new ArrayList<>();
        this.velocity = 0;
//        this.gravity = 0;

        ballMeshWrapper = new Group();
        SVGPath path = new SVGPath();
        path.setFill(color);
        path.setContent(ballMeshSVG);
        path.setScaleX(SCALE_FACTOR);
        path.setScaleY(SCALE_FACTOR);
        path.getTransforms().add(new Translate(-(ICON_SIZE / 2) / SCALE_FACTOR, -(ICON_SIZE / 2) / SCALE_FACTOR));
        ballMesh = path;
        ballMeshWrapper.getChildren().add(ballMesh);

        this.sceneGraphRoot = sceneGraphRoot;

        motionTimer = new AnimationTimer() {
            double prevTimeStamp = System.nanoTime();

            @Override
            public void handle(long l) {

//                if (hasStarted) {
                update((l - prevTimeStamp) * Math.pow(10, -9));
                prevTimeStamp = l;
//                }

                if (!isAlive) {
                    sceneGraphRoot.getChildren().remove(ballMeshWrapper);

                    for (PlayerDeathSubscriber p : deathSubscribers) {
                        p.onPlayerDeath();
                    }

                    this.stop();

                    Emitter emitter = new Emitter(new Vector2D(
                            ballMeshWrapper.getTranslateX(),
                            ballMeshWrapper.getTranslateY()),
                            colorMapping
                    );

                    sceneGraphRoot.getChildren().add(emitter);
                }
            }
        };
    }

    public void create(Vector2D initialPosition, EntityManager entityManager) {
        entityManager.register(this);
        ballMeshWrapper.setTranslateX(initialPosition.x);
        ballMeshWrapper.setTranslateY(initialPosition.y);
        sceneGraphRoot.getChildren().add(ballMeshWrapper);

        RotationComponent rotationComponent = new RotationComponent(100, 0, 0);
        entityManager.addComponents(this, rotationComponent);
        ballMeshWrapper.getTransforms().add(rotationComponent.getRotateTransform());

        lowerBound = initialPosition.y;
        motionTimer.start();
    }

    public void update(double dt) {
        double dy = velocity * dt + 0.5 * GRAVITY * (dt * dt);
        ballMeshWrapper.setTranslateY(Math.min(ballMeshWrapper.getTranslateY() + dy, lowerBound));
        velocity += GRAVITY * dt;
    }

    public void impulse(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.W) {
            if (!hasStarted) {
                hasStarted = true;
                lowerBound = sceneGraphRoot.getHeight() - ICON_SIZE * SCALE_FACTOR;
            }
            setVelocity(-VELOCITY_UP);
        } else if (keyEvent.getCode() == KeyCode.LEFT) {
            Rotate rotate = (Rotate) sceneGraphRoot.getTransforms().get(0);
            rotate.setAngle(rotate.getAngle() - 90);
        } else if (keyEvent.getCode() == KeyCode.RIGHT) {
            Rotate rotate = (Rotate) sceneGraphRoot.getTransforms().get(0);
            rotate.setAngle(rotate.getAngle() + 90);
        }
    }

    public void saveGame(SaveGame saveGame) {
        saveGame.setPlayerPosition(new Vector2D(
                ballMeshWrapper.getTranslateX(),
                ballMeshWrapper.getTranslateY()
        ));
    }

    public void addDeathSubscriber(PlayerDeathSubscriber deathSubscriber) {
        deathSubscribers.add(deathSubscriber);
    }

    public double getVelocity() {
        return velocity;
    }

    public int getScore() {
        return score;
    }

    public Vector2D getPosition() {
        return new Vector2D(ballMeshWrapper.getTranslateX(), ballMeshWrapper.getTranslateY());
    }

    public ArrayList<String> getSkinSVGList() {
        return new ArrayList<>(meshSVGMap.values());
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setColor(Color color) {
        this.color = color;
        ballMesh.setFill(color);
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }
}
