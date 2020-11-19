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

        final double RADIUS = 200;
        final double SPEED = 2;
        final int PARTICLE_COUNT = 20;
        final double PARTICLE_RADIUS = 27;

        EntityManager entityManager = new EntityManager();
        ParticulateCircle particulateCircle1 = new ParticulateCircle(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                RADIUS,
                SPEED,
                PARTICLE_COUNT,
                PARTICLE_RADIUS
        );

        QuadArcCircle quadArcCircle = new QuadArcCircle(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                1.5 * RADIUS,
                50,
                100
        );

        QuadArcCircle quadArcCircle2 = new QuadArcCircle(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                1.5 * RADIUS - 70,
                40,
                -100
        );

//        root.getChildren().add(particulateCircle1.container);
        root.getChildren().add(quadArcCircle.container);
        root.getChildren().add(quadArcCircle2.container);

        PhysicsBehaviourSystem physicsBehaviourSystem = new PhysicsBehaviourSystem(entityManager);
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                double secondsElapsed = l * Math.pow(10, -9);
                physicsBehaviourSystem.update(secondsElapsed);
            }
        };

        timer.start();
        stage.setTitle("Rendering system test");
        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.web("#292929")));
        stage.show();
    }
}