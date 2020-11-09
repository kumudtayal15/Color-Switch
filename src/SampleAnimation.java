import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SampleAnimation extends Application {

    private final int PARTICLE_COUNT = 16;

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException {
        Group root = new Group();
        Circle[] circles = new Circle[PARTICLE_COUNT];
        TrajectoryComponent[] trajectories = new TrajectoryComponent[PARTICLE_COUNT];

        double speed = 0;
        double delay = Math.PI / 8;
        double amplitude = 0;
        try (InputStream input = new FileInputStream("src/anim.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            try {
                speed = Double.parseDouble(properties.getProperty("speed"));
                amplitude = Double.parseDouble(properties.getProperty("amplitude"));
            } catch (NullPointerException e) {
                System.out.println("Error: Undefined property");
                e.printStackTrace();
                System.exit(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            circles[i] = new Circle(300, 300, 20, Color.RED);
            if (i < PARTICLE_COUNT / 2) {
                trajectories[i] = new LemniscateLTrajectory(amplitude, speed, delay * i);
            } else {
                trajectories[i] = new LemniscateRTrajectory(amplitude, speed, delay * i);
            }
        }
        root.getChildren().addAll(circles);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double secondsElapsed = l * Math.pow(10, -9);

                for (int i = 0; i < PARTICLE_COUNT; i++) {
                    circles[i].setTranslateX(trajectories[i].getPositionVector(secondsElapsed).x);
                    circles[i].setTranslateY(trajectories[i].getPositionVector(secondsElapsed).y);
                }
            }
        };

        Scene scene = new Scene(root, 600, 600);
        timer.start();

        primaryStage.setTitle("AnimationTimer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
