import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
//        System.out.println(Screen.getPrimary().getBounds());
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

        final Color BLACK = Color.web("#292929");
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, BLACK);
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);

        EntityManager entityManager = new EntityManager();

        Ball ball = new Ball(root);
        ball.create(new Vector2D(scene.getWidth() / 2, scene.getHeight() - 100), entityManager);
        scene.setOnKeyPressed(ball::impulse);

        ParticulateSquare particulateSquare = new ParticulateSquare(
                new Vector2D(SCENE_WIDTH / 2 - 125, SCENE_HEIGHT / 2 + 150 - 300),
                entityManager,
                250, 300,
                16, 17
        );
        particulateSquare.create();
        root.getChildren().add(particulateSquare.container);

        ParticulateCircle particulateCircle = new ParticulateCircle(
                new Vector2D(SCENE_WIDTH / 2, -500),
                entityManager,
                150,
                1,
                24,
                15
        );
//        particulateCircle.create();
//        root.getChildren().add(particulateCircle.container);

        ParticleLemniscate particleLemniscate = new ParticleLemniscate(
                new Vector2D(SCENE_WIDTH / 2, -500),
                entityManager,
                100,
                1.5,
                16,
                17
        );
        root.getChildren().add(particleLemniscate.container);

        /*
        Ball decoupled from entity manager
         */

        PhysicsSystem physicsSystem = new PhysicsSystem(entityManager);

        CollisionSystem collisionSystem = new CollisionSystem(entityManager);
        collisionSystem.setPlayer(ball);

        RenderSystem renderSystem = new RenderSystem(entityManager, canvas.getGraphicsContext2D());
        renderSystem.setPlayer(ball);
        renderSystem.setObjectTracking(true);

        ScrollingSystem scrollingSystem = new ScrollingSystem(entityManager, root.getLayoutBounds());
        scrollingSystem.setPlayer(ball);
        scrollingSystem.addAll(
                particulateSquare.container,
                particleLemniscate.container
        );

        physicsSystem.init();
        collisionSystem.init();
        renderSystem.init();
        scrollingSystem.init();

        stage.setTitle("Rendering system test");
        stage.setScene(scene);

        stage.show();
    }

}