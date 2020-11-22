import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BehaviourSystemTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        final double SCENE_WIDTH = 1920;
        final double SCENE_HEIGHT = 1080;
        Group root = new Group();
//        root.setTranslateX(SCENE_WIDTH / 2);
//        root.setTranslateY(SCENE_HEIGHT / 2);

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
                300, 300,
                28, 25
        );
        particulateHex.create();
        root.getChildren().add(particulateHex.container);

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
//        root.getChildren().add(quadArcCircle.container);

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
        AnimationTimer timer = new AnimationTimer() {

            //            final double startTime = System.currentTimeMillis();
            @Override
            public void handle(long l) {
                double secondsElapsed = l * Math.pow(10, -9);
//                double secondsElapsed = (System.currentTimeMillis() - startTime) / 1000d;
//                System.out.println("Time elapsed: " + secondsElapsed);
                physicsBehaviourSystem.update(secondsElapsed);
            }
        };

        timer.start();
        stage.setTitle("Rendering system test");
        final Color BLACK = Color.web("#292929");
        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, BLACK));
        stage.show();
    }
}