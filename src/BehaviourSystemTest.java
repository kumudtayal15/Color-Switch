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
    public void start(Stage stage) throws Exception {
        final double SCENE_WIDTH = 600;
        final double SCENE_HEIGHT = 700;
        Group root = new Group();

        final double RADIUS = 150;
        final double SPEED = 3;
        final int PARTICLE_COUNT = 16;
        final double PARTICLE_RADIUS = 20;

        EntityManager entityManager = new EntityManager();
        ParticulateCircle particulateCircle = new ParticulateCircle(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                RADIUS,
                SPEED,
                PARTICLE_COUNT,
                PARTICLE_RADIUS
        );
        root.getChildren().add(particulateCircle.container);

        RenderBehaviourSystem renderBehaviourSystem = new RenderBehaviourSystem(entityManager);
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                double secondsElapsed = l * Math.pow(10, -9);
                renderBehaviourSystem.update(secondsElapsed);
            }
        };

        timer.start();
        stage.setTitle("Rendering system test");
        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.web("#292929")));
        stage.show();

    }
}