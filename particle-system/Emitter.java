import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Emitter extends Group {
    protected Timeline animator;
    protected int particleCount;
    private double SCENE_WIDTH;
    private double SCENE_HEIGHT;

    public Emitter(Vector2D anchorPoint, Color[] particleColorMapping) {
        /*
        Absolute coordinates (relative to scene) of the ball must be provided
        as the anchor point vector
         */

        this.setLayoutX(anchorPoint.x);
        this.setLayoutY(anchorPoint.y);

        try(InputStream input = new FileInputStream("hyperparameters/particle.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.particleCount = Integer.parseInt(properties.getProperty("count"));
        } catch (IOException e) {
            System.out.println("IO Exception occurred");
            e.printStackTrace();
        }

        try (InputStream input = new FileInputStream("hyperparameters/display.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.SCENE_WIDTH = Double.parseDouble(properties.getProperty("scene.width"));
            this.SCENE_HEIGHT = Double.parseDouble(properties.getProperty("scene.height"));
        } catch (IOException e) {
            System.out.println("IO Exception occurred");
            e.printStackTrace();
        }

        // TODO: 23/11/20 Bounding box calculation not scalable!
        Bounds bounds = new BoundingBox(
                -anchorPoint.x, -anchorPoint.y, 0,
                SCENE_WIDTH, SCENE_HEIGHT, 0
        );

        for (int i = 0; i < particleCount; i++) {
            Particle p = new Particle(bounds);
            p.setCustomColorMapping(particleColorMapping);
            p.generate();
            this.getChildren().add(p);
        }

        animator = new Timeline();
        animator.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
                Duration.millis(1000/60d),
                actionEvent -> {
                    /*
                    Can't use the enhanced for loop,
                    concurrent iteration not supported
                     */

                    for (int i = 0; i < getChildren().size(); i++) {
                        Particle p = (Particle) getChildren().get(i);
                        p.doStep();
                    }
                }
        );
        animator.getKeyFrames().add(kf);
        animator.play();
    }
}
