import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BehaviourSystemTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        double SCENE_WIDTH = 0, SCENE_HEIGHT = 0;

        try (InputStream input = new FileInputStream("hyperparameters/display.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            SCENE_WIDTH = Double.parseDouble(properties.getProperty("scene.width"));
            SCENE_HEIGHT = Double.parseDouble(properties.getProperty("scene.height"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pane root = new Pane();

//        root.setTranslateX(SCENE_WIDTH / 2);
//        root.setTranslateY(SCENE_HEIGHT / 2);
        final Color BLACK = Color.web("#292929");
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, BLACK);

        EntityManager entityManager = new EntityManager();

        ParticulateSinWave sinWave = new ParticulateSinWave(
                new Vector2D(0, SCENE_HEIGHT / 2),
                entityManager,
                200, 4,
                20, 24
        );
//        sinWave.create();
//        root.getChildren().add(sinWave.container);

        ParticulateSquare particulateSquare = new ParticulateSquare(
                new Vector2D(SCENE_WIDTH / 2 - 200, SCENE_HEIGHT / 2 + 200),
                entityManager,
                400, 300,
                24, 22
        );
//        particulateSquare.create();
//        root.getChildren().add(particulateSquare.container);

        ParticulateHex particulateHex = new ParticulateHex(
                new Vector2D(SCENE_WIDTH / 2 - 200, SCENE_HEIGHT / 2 + 200),
                entityManager,
                250, 300,
                28, 20
        );
//        particulateHex.create();
//        root.getChildren().add(particulateHex.container);

        ParticulateTriangle particulateTriangle = new ParticulateTriangle(
                new Vector2D(SCENE_WIDTH / 2 - 250, SCENE_HEIGHT / 2 + 200),
                entityManager,
                500, 200,
                24, 22
        );
//        particulateTriangle.create();
//        root.getChildren().add(particulateTriangle.container);

        HarmonicCircle harmonicCircle = new HarmonicCircle(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                200,
                2,
                20,
                27
        );
//        harmonicCircle.create();
//        root.getChildren().add(harmonicCircle.container);

        ParticulateCircle particulateCircle = new ParticulateCircle(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                200,
                2,
                20,
                27
        );
//        particulateCircle.create();
//        root.getChildren().add(particulateCircle.container);

        QuadArcCircle quadArcCircle = new QuadArcCircle(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                300,
                70,
                50
        );
        root.getChildren().add(quadArcCircle.container);

        Triangle triangle = new Triangle(
                new Vector2D(0, SCENE_HEIGHT / 2),
                entityManager,
                400,
                40,
                300
        );
//        Group triangleGroup = new Group();
//        triangleGroup.setTranslateY(SCENE_HEIGHT / 2);
//        triangleGroup.getChildren().add(triangle.container);
//        entityManager.addComponents(triangle, new HorizontalLineTrajectory(0, 700, 0));
//        root.getChildren().add(triangle.container);

        Rhombus rhombus = new Rhombus(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                450,
                50,
                150,
                70
        );
//        root.getChildren().add(rhombus.container);

        ParticleLemniscate particleLemniscate = new ParticleLemniscate(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                200,
                1,
                16,
                30
        );
//        root.getChildren().add(particleLemniscate.container);


        PhysicsBehaviourSystem physicsBehaviourSystem = new PhysicsBehaviourSystem(entityManager);

        /*
        Ball decoupled from entity manager
         */
        Ball ball = new Ball(root);
        ball.create(new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2));
        scene.setOnKeyPressed(ball::impulse);

        AnimationTimer timer = new AnimationTimer() {

            final double tNought = System.nanoTime();

            @Override
            public void handle(long l) {
                double secondsElapsed = (l - tNought) * Math.pow(10, -9);
                physicsBehaviourSystem.update(secondsElapsed);
            }
        };


        stage.setTitle("Rendering system test");
        stage.setScene(scene);

        timer.start();
        stage.show();
    }
}