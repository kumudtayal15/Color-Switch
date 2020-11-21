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
//        GridPane root = new GridPane();
//        root.setHgap(100);
//        root.setVgap(150);
//        root.setGridLinesVisible(true);

        final double RADIUS = 200;
        final double SPEED = 2;
        final int PARTICLE_COUNT = 20;
        final double PARTICLE_RADIUS = 27;

        EntityManager entityManager = new EntityManager();

        ParticulateSquare particulateSquare = new ParticulateSquare(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                400, 300,
                24, 22
        );
        particulateSquare.create();
        root.getChildren().add(particulateSquare.container);

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
                new Vector2D(0, 0),
                entityManager,
                RADIUS,
                50,
                50
        );

        Triangle triangle = new Triangle(
                new Vector2D(0, 0),
                entityManager,
                400,
                40,
                300
        );
//        Group triangleGroup = new Group();
////        triangleGroup.setTranslateY(SCENE_HEIGHT / 2);
//        triangleGroup.getChildren().add(triangle.container);
//        entityManager.addComponents(triangle, new HorizontalLineTrajectory(0, 700, 0));

        Rhombus rhombus = new Rhombus(
                new Vector2D(0, 0),
                entityManager,
                350,
                30,
                150,
                65
        );

        ParticleLemniscate particleLemniscate = new ParticleLemniscate(
                new Vector2D(0, 0),
                entityManager,
                150,
                1,
                12,
                25
        );

//        root.getChildren().add(particulateCircle.container);
//        GridPane.setColumnIndex(particulateCircle.container, 0);
//        GridPane.setRowIndex(particulateCircle.container, 0);
//
//        root.getChildren().add(triangle.container);
//        GridPane.setConstraints(triangle.container, 1, 1);
////        GridPane.setColumnSpan(triangle.container, 2);
//
//        root.getChildren().add(quadArcCircle.container);
//        GridPane.setConstraints(quadArcCircle.container, 2, 0);
//
//        root.getChildren().add(rhombus.container);
//        GridPane.setConstraints(rhombus.container, 0, 1);
//
//        root.getChildren().add(particleLemniscate.container);
//        GridPane.setConstraints(particleLemniscate.container, 1, 0);
//        GridPane.setMargin(particleLemniscate.container, new Insets(0, 50, 0, 50));


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