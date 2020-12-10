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
import java.util.HashMap;
import java.util.Properties;

public class Ball extends GameObject {

    private final Pane sceneGraphRoot;
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

    public Ball(Pane sceneGraphRoot) {
        final HashMap<String, String> meshSVGMap = new HashMap<>(3);
        meshSVGMap.put("crochet", "M49.85,116.47A91.26,91.26,0,0,0,51.4,214l48-48ZM110,176.62,60.69,225.93a92,92,0,0,0,17.8,15L126.39,193ZM189.92,96.7,120.61,166,137,182.41l67.91-67.91a92,92,0,0,0-15-17.8ZM147.61,193l40.19,40.2a91.38,91.38,0,0,0,24.47-104.85ZM75.25,47.27,91.64,80.05a91.92,91.92,0,0,0-32.83,24.17L110,155.4l68-68a91.1,91.1,0,0,0-13.62-7.36l16.39-32.78A23.51,23.51,0,0,0,207.51,24C207.51,11,197,0,184,0s-23.5,11-23.5,24a23.46,23.46,0,0,0,6.84,16.56L150,75.19a91.51,91.51,0,0,0-44,0L88.66,40.56A23.46,23.46,0,0,0,95.5,24C95.5,11,85,0,72,0S48.5,11,48.5,24A23.51,23.51,0,0,0,75.25,47.27ZM137,203.62,92.35,248.28c28,11.88,56.47,10.2,83.19-6.12Z");
        meshSVGMap.put("tennis", "M256,128a127.15,127.15,0,0,0-37.49-90.51q-4.29-4.29-8.9-8.11c-9.07,28.89-13.85,61.88-13.85,96,0,35.85,5.24,70.27,15.19,100.13q3.9-3.33,7.56-7A127.15,127.15,0,0,0,256,128Zm-75.36-2.64c0-37.53,5.57-73.88,16.12-105.37a128.28,128.28,0,0,0-137.11-.27c10.62,31.56,16.22,68,16.22,105.64,0,39.48-6.12,77.41-17.7,110A128.2,128.2,0,0,0,198.25,235c-11.53-32.48-17.61-70.32-17.61-109.68Zm-119.89,0c0-34.24-4.82-67.35-14-96.31-3.21,2.64-6.32,5.46-9.3,8.44a128,128,0,0,0,0,181q3.84,3.84,8,7.31c10-29.93,15.3-64.47,15.3-100.46Z");
        meshSVGMap.put("billiards", "M128,256A128,128,0,1,0,0,128,128,128,0,0,0,128,256ZM165.49,59.43A68.57,68.57,0,1,1,96.92,128,68.58,68.58,0,0,1,165.49,59.43ZM141.36,143c0,9.79,8.45,20.14,26.81,20.14,16.79,0,27.81-8.9,27.81-22,0-9.23-6.23-15.24-13.35-17.91v-.33c7.23-3.56,10.57-9.68,10.57-15.91,0-8.79-7-18.58-23.92-18.58-14.46,0-25.47,8.12-25.47,20.36,0,6.34,3.45,12.24,10.46,15.91v.22C146.36,128.31,141.36,134.31,141.36,143ZM168.5,99.83c6.23,0,8.79,4.45,8.79,9s-3.45,8-7.34,9.13c-5.79-1.78-10.13-4.9-10.13-9.8C159.83,103.73,162.72,99.83,168.5,99.83Zm-1.11,30.7c6.57,1.79,11.34,5.57,11.34,11.91,0,5.12-4,9-9.9,9a9.9,9.9,0,0,1-10.23-10.12C158.6,136.1,161.83,132.09,167.39,130.53Z");
        meshSVGMap.put("biohazard", "M255.82,171.4A70.69,70.69,0,0,0,192,105.19,70.75,70.75,0,0,0,154.87,10.1,4.27,4.27,0,0,0,151,17.67,43.54,43.54,0,1,1,90.47,76.78a74.85,74.85,0,0,1,66.68-4.13,4.26,4.26,0,1,0,3.33-7.85A83.36,83.36,0,0,0,86.84,69,43.48,43.48,0,0,1,104.9,17.67,4.27,4.27,0,0,0,101,10.1a70.73,70.73,0,0,0-37.16,95.09A70.71,70.71,0,0,0,0,172.58a4.27,4.27,0,0,0,8.44,1.06,43.55,43.55,0,0,1,83.17-6.83c.15,1.33,8.25,19.22-3.58,38.58A74.54,74.54,0,0,1,53.9,154.11a4.27,4.27,0,0,0-8.43,1.39,83.73,83.73,0,0,0,37.41,56.71,43.47,43.47,0,0,1-58.11,5.27,4.31,4.31,0,1,0-5.95,6.19C20.1,224.74,38,246.22,70.5,246.22a70.8,70.8,0,0,0,57.5-29.5c25.33,35.53,78.54,40.45,109.6,6.46a4.27,4.27,0,0,0-5.78-6.24A43.54,43.54,0,1,1,203,139.16a74.36,74.36,0,0,1-25.38,59,4.27,4.27,0,1,0,5.65,6.4,83.42,83.42,0,0,0,28.27-64.91,43.42,43.42,0,0,1,35.85,33c.31,6.67,10.08,5.4,8.48-1.19Zm-127.88-6.2a23.39,23.39,0,1,1,23.4-23.39,23.41,23.41,0,0,1-23.4,23.39Z");
        meshSVGMap.put("virus", "M241.87,107.75l-21-7.71a94.52,94.52,0,0,0-10-22.28l3.44-7.4a21.56,21.56,0,0,0-28.62-28.62l-7.4,3.44a94.93,94.93,0,0,0-22.28-10l-7.71-21a21.58,21.58,0,0,0-40.5,0l-7.71,21a94.93,94.93,0,0,0-22.28,10l-7.4-3.44A21.56,21.56,0,0,0,41.74,70.36l3.44,7.4a94.93,94.93,0,0,0-10,22.28l-21,7.71a21.58,21.58,0,0,0,0,40.5l21,7.71a94.93,94.93,0,0,0,10,22.28l-3.44,7.4a21.54,21.54,0,0,0,19.55,30.6,21.88,21.88,0,0,0,9.07-2l7.4-3.44a94.52,94.52,0,0,0,22.28,10l7.71,21a21.58,21.58,0,0,0,40.5,0l7.71-21a94.52,94.52,0,0,0,22.28-10l7.4,3.44a21.88,21.88,0,0,0,9.07,2,21.54,21.54,0,0,0,19.55-30.6l-3.44-7.4a94.52,94.52,0,0,0,10-22.28l21-7.71a21.58,21.58,0,0,0,0-40.5Zm-117,44.47c-8,9.24-18.71,14.35-30.07,14.35s-22-5.11-30.08-14.35c-16.55-19.11-16.55-50.15,0-69.22a2.25,2.25,0,0,1,3.48,0l56.63,65.21a3.05,3.05,0,0,1,.74,2A2.86,2.86,0,0,1,124.83,152.22Zm66.49,0c-8,9.24-18.72,14.35-30.08,14.35s-22-5.11-30.07-14.35a3.11,3.11,0,0,1,0-4.05L187.8,83a2.28,2.28,0,0,1,3.52,0C207.87,102.11,207.87,133.15,191.32,152.22Z");
        meshSVGMap.put("ufo", "M242.86,122.93c8.28-25.61,2.32-62.17-22.6-87.09-18-18-42.1-26.14-63.74-26.14a75.76,75.76,0,0,0-24.24,3.81C95-5,62.35-4.13,44.41,13.81c-13,13-16.41,33.48-11.51,57l-8.1,8.1a19.14,19.14,0,0,0-5.05-.74A19.76,19.76,0,1,0,39.51,97.91a20.14,20.14,0,0,0-.73-5.06l.62-.62c8.56,22,23.35,45.34,43.39,67L54.45,187.57a19.72,19.72,0,1,0,14.69,19,20.14,20.14,0,0,0-.73-5.06L96.74,173.2c21.61,20.05,44.89,34.87,66.89,43.46l-.54.55a20.08,20.08,0,1,0,14,14l8-8c23.6,5,44.17,1.62,57.23-11.46C260.22,193.81,260.73,159.9,242.86,122.93ZM207,49.17c26.09,26.08,24.67,66.76,11,80.41-7.07,7.07-14.3,10.51-22.09,10.51-13.77,0-31.2-10.17-50.43-29.42-45.68-45.7-26.2-65.2-18.87-72.54,6-6,17.2-9.59,29.93-9.59A71.71,71.71,0,0,1,207,49.17Z");

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

                update((l - prevTimeStamp) * Math.pow(10, -9));
                prevTimeStamp = l;

                if (!isAlive) {
                    sceneGraphRoot.getChildren().remove(ballMeshWrapper);
                    sceneGraphRoot.getChildren().add(new Emitter(new Vector2D(
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
        sceneGraphRoot.getChildren().add(ballMeshWrapper);

        RotationComponent rotationComponent = new RotationComponent(100, 0, 0);
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
        else if (keyEvent.getCode() == KeyCode.LEFT) {
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

    public void setColor(Color color) {
        this.color = color;
        ballMesh.setFill(color);
    }
}
