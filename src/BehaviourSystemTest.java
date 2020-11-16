import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        final double PARTICLE_RADIUS = 25;

        EntityManager entityManager = new EntityManager();
        ParticulateCircle particulateCircle1 = new ParticulateCircle(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                RADIUS,
                SPEED,
                PARTICLE_COUNT,
                PARTICLE_RADIUS
        );

        ParticulateCircle particulateCircle2 = new ParticulateCircle(
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                2 * RADIUS,
                -SPEED,
                PARTICLE_COUNT,
                2 * PARTICLE_RADIUS
        );

        PrimitiveObstacle rrect = new PrimitiveObstacle(new Vector2D(10, 50));
        entityManager.register(rrect);

        Rectangle roundedRect =
                new Rectangle(SCENE_WIDTH / 2, SCENE_HEIGHT / 2, 20, 50);
        roundedRect.setArcWidth(20);
        roundedRect.setArcHeight(20);
        MeshComponent newMeshComponent = new MeshComponent(roundedRect, Color.WHITE);
        entityManager.addComponents(
                rrect,
                newMeshComponent::insertionCallback,
                newMeshComponent);

        RotationComponent rrc = new RotationComponent(
                500,
                SCENE_WIDTH / 2 + 10,
                SCENE_HEIGHT / 2 + 25);
        entityManager.addComponents(rrect, rrc::insertionCallback, rrc);

        CircleTrajectory rct = new CircleTrajectory(300, 5, 0);
        entityManager.addComponents(rrect, rct);

        root.getChildren().add(particulateCircle1.container);
        root.getChildren().add(particulateCircle2.container);
        root.getChildren().add(rrect.mesh);

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