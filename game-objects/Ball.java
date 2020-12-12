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
    protected double GRAVITY;
    private double SCALE_FACTOR;
    private double ICON_SIZE;
    private final Color[] colorMapping;

    public Ball(Pane sceneGraphRoot, Color[] colorMapping) {
        meshSVGMap = new HashMap<>(6);
        meshSVGMap.put("crochet", "M49.85,116.47A91.26,91.26,0,0,0,51.4,214l48-48ZM110,176.62,60.69,225.93a92,92,0,0,0,17.8,15L126.39,193ZM189.92,96.7,120.61,166,137,182.41l67.91-67.91a92,92,0,0,0-15-17.8ZM147.61,193l40.19,40.2a91.38,91.38,0,0,0,24.47-104.85ZM75.25,47.27,91.64,80.05a91.92,91.92,0,0,0-32.83,24.17L110,155.4l68-68a91.1,91.1,0,0,0-13.62-7.36l16.39-32.78A23.51,23.51,0,0,0,207.51,24C207.51,11,197,0,184,0s-23.5,11-23.5,24a23.46,23.46,0,0,0,6.84,16.56L150,75.19a91.51,91.51,0,0,0-44,0L88.66,40.56A23.46,23.46,0,0,0,95.5,24C95.5,11,85,0,72,0S48.5,11,48.5,24A23.51,23.51,0,0,0,75.25,47.27ZM137,203.62,92.35,248.28c28,11.88,56.47,10.2,83.19-6.12Z");
        meshSVGMap.put("tennis", "M256,128a127.15,127.15,0,0,0-37.49-90.51q-4.29-4.29-8.9-8.11c-9.07,28.89-13.85,61.88-13.85,96,0,35.85,5.24,70.27,15.19,100.13q3.9-3.33,7.56-7A127.15,127.15,0,0,0,256,128Zm-75.36-2.64c0-37.53,5.57-73.88,16.12-105.37a128.28,128.28,0,0,0-137.11-.27c10.62,31.56,16.22,68,16.22,105.64,0,39.48-6.12,77.41-17.7,110A128.2,128.2,0,0,0,198.25,235c-11.53-32.48-17.61-70.32-17.61-109.68Zm-119.89,0c0-34.24-4.82-67.35-14-96.31-3.21,2.64-6.32,5.46-9.3,8.44a128,128,0,0,0,0,181q3.84,3.84,8,7.31c10-29.93,15.3-64.47,15.3-100.46Z");
        meshSVGMap.put("billiards", "M128,256A128,128,0,1,0,0,128,128,128,0,0,0,128,256ZM165.49,59.43A68.57,68.57,0,1,1,96.92,128,68.58,68.58,0,0,1,165.49,59.43ZM141.36,143c0,9.79,8.45,20.14,26.81,20.14,16.79,0,27.81-8.9,27.81-22,0-9.23-6.23-15.24-13.35-17.91v-.33c7.23-3.56,10.57-9.68,10.57-15.91,0-8.79-7-18.58-23.92-18.58-14.46,0-25.47,8.12-25.47,20.36,0,6.34,3.45,12.24,10.46,15.91v.22C146.36,128.31,141.36,134.31,141.36,143ZM168.5,99.83c6.23,0,8.79,4.45,8.79,9s-3.45,8-7.34,9.13c-5.79-1.78-10.13-4.9-10.13-9.8C159.83,103.73,162.72,99.83,168.5,99.83Zm-1.11,30.7c6.57,1.79,11.34,5.57,11.34,11.91,0,5.12-4,9-9.9,9a9.9,9.9,0,0,1-10.23-10.12C158.6,136.1,161.83,132.09,167.39,130.53Z");
        meshSVGMap.put("ufo", "M242.86,122.93c8.28-25.61,2.32-62.17-22.6-87.09-18-18-42.1-26.14-63.74-26.14a75.76,75.76,0,0,0-24.24,3.81C95-5,62.35-4.13,44.41,13.81c-13,13-16.41,33.48-11.51,57l-8.1,8.1a19.14,19.14,0,0,0-5.05-.74A19.76,19.76,0,1,0,39.51,97.91a20.14,20.14,0,0,0-.73-5.06l.62-.62c8.56,22,23.35,45.34,43.39,67L54.45,187.57a19.72,19.72,0,1,0,14.69,19,20.14,20.14,0,0,0-.73-5.06L96.74,173.2c21.61,20.05,44.89,34.87,66.89,43.46l-.54.55a20.08,20.08,0,1,0,14,14l8-8c23.6,5,44.17,1.62,57.23-11.46C260.22,193.81,260.73,159.9,242.86,122.93ZM207,49.17c26.09,26.08,24.67,66.76,11,80.41-7.07,7.07-14.3,10.51-22.09,10.51-13.77,0-31.2-10.17-50.43-29.42-45.68-45.7-26.2-65.2-18.87-72.54,6-6,17.2-9.59,29.93-9.59A71.71,71.71,0,0,1,207,49.17Z");
//        meshSVGMap.put("batman", "M229.16,65.59a66.3,66.3,0,0,0-37.95-13.25c7.46,2.71,13.72,12.29,13.8,20.35s-7.15,16-21.62,23.47-30.79,5.26-33.11,3.34-1.85-5.41-3.49-12.53-9.08-13-9.08-13c1.77,3.07,3,9.63,3.1,11.12.16,1.9,0,4.06-1.76,2.85-2.83-1.9-8-2.85-11-2.85s-8.22,1-11,2.85c-1.81,1.21-1.93-1-1.77-2.85.13-1.49,1.33-8,3.1-11.12,0,0-7.44,5.92-9.07,13S108,97.59,105.71,99.5s-18.63,4.16-33.1-3.34S50.91,80.75,51,72.69s6.34-17.64,13.79-20.35A66.26,66.26,0,0,0,26.84,65.59C9.1,79.23-.22,100,0,113.08s2.91,40.38,23.41,58.59c0,0-2.18-15.34,1.58-24.58s8.18-12.78,14.81-12.78c0,0-.88,9.24,5.31,21.78a112.63,112.63,0,0,0,14.81,22.25s6.19-28.64,29.4-30.3S128,203.63,128,203.66s15.47-57.28,38.68-55.62,29.4,30.3,29.4,30.3a112.63,112.63,0,0,0,14.81-22.25c6.19-12.54,5.32-21.78,5.32-21.78,6.63,0,11,3.56,14.81,12.78s1.57,24.58,1.57,24.58c20.5-18.21,23.18-45.5,23.41-58.59S246.9,79.23,229.16,65.59Z");
        meshSVGMap.put("guitar", "M134.58,0H120.85A6.86,6.86,0,0,0,114,6.86v32a6.86,6.86,0,0,0,6.86,6.86h13.73a6.86,6.86,0,0,0,6.86-6.86v-32A6.86,6.86,0,0,0,134.58,0Zm-32,2.29h6.86v9.15h-6.86Zm0,16h6.86v9.15h-6.86Zm0,16h6.86v9.15h-6.86ZM146,2.29h6.86v9.15H146Zm0,16h6.86v9.15H146Zm0,16h6.86v9.15H146Zm29.39,139.51c-7.06-8.47-6.58-20.08-3.35-30.63a34.06,34.06,0,0,0,1.7-10.76c0-18.19-9.8-27.4-28.12-30.62L146,112a11.46,11.46,0,0,1-11.44,11.45H120.85a11.5,11.5,0,0,1-11.44-11.62l.38-9.88c-18,3.3-27.55,12.52-27.55,30.53a34.35,34.35,0,0,0,1.7,10.76c3.23,10.55,3.71,22.16-3.35,30.63s-14.37,20.43-14.37,31.83C66.22,240,89.51,256.1,128,256c38.5.1,61.78-16,61.78-50.34C189.78,194.26,182.55,182.39,175.41,173.83Zm-29.1,27.26H109.7v-9.16h36.61Zm-18.19-30a18.31,18.31,0,1,1,18.31-18.3A18.3,18.3,0,0,1,128.12,171.07ZM139.36,57a6.88,6.88,0,0,0-6.86-6.65h-9.57A6.86,6.86,0,0,0,116.07,57L114,112a6.86,6.86,0,0,0,6.86,6.86h13.73a6.86,6.86,0,0,0,6.86-6.86Z");

        String ballMeshSVG = null;

        try (InputStream input = new FileInputStream("hyperparameters/ball.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.GRAVITY = Double.parseDouble(properties.getProperty("gravity"));
            this.VELOCITY_UP = Double.parseDouble(properties.getProperty("impartedvelocity"));
            this.SCALE_FACTOR = Double.parseDouble(properties.getProperty("radius.scalefactor"));
            this.ICON_SIZE  = Double.parseDouble(properties.getProperty("icon.size"));
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

                    for(PlayerDeathSubscriber p : deathSubscribers) {
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

        motionTimer.start();
    }

    public void update(double dt) {
        double dy = velocity * dt + 0.5 * GRAVITY * (dt * dt);
        ballMeshWrapper.setTranslateY(Math.min(ballMeshWrapper.getTranslateY() + dy, 700));
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

    public void addDeathSubscriber(PlayerDeathSubscriber deathSubscriber) {
        deathSubscribers.add(deathSubscriber);
    }

    public double getVelocity() {
        return velocity;
    }

    public int getScore() {
        return score;
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
}
