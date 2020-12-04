import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class BehaviourSystemTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws NonInvertibleTransformException {

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
        ball.create(new Vector2D(scene.getWidth() / 2, scene.getHeight()), entityManager);
        scene.setOnKeyPressed(ball::impulse);

//        SYSTEMS
        PhysicsSystem physicsSystem = new PhysicsSystem(entityManager, root);

        ProfilingSystem profilingSystem = new ProfilingSystem(entityManager, root, ball, canvas.getGraphicsContext2D());
//        profilingSystem.setObjectTracking(true);
        profilingSystem.setLocationCrosshairs(true);

        ScrollingSystem scrollingSystem = new ScrollingSystem(entityManager, root, root.getLayoutBounds());
        scrollingSystem.setPlayer(ball);

        CollisionSystem collisionSystem = new CollisionSystem(entityManager, root, ball, scrollingSystem);

        SpawnSystem spawnSystem = new SpawnSystem(entityManager, root, scrollingSystem);

        final Vector2D SCREEN_CENTRE = new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2);
        ParticulateHex particulateHex = new ParticulateHex(SCREEN_CENTRE, entityManager, Level.EASY);
//        particulateHex.create();
//        root.getChildren().add(particulateHex.getNode());

        EightPointStar eightPointStar = new EightPointStar(
                new Vector2D(SCREEN_CENTRE.x, SCREEN_CENTRE.y),
                entityManager,
                Level.EASY
        );
        eightPointStar.create();
        root.getChildren().add(eightPointStar.getNode());
        scrollingSystem.add(eightPointStar.getNode());

        Random random = new Random();
        int rotationAngle = Math.max(random.nextInt(180), 90);
        Rotate rotateTransform = new Rotate(0, scene.getWidth() / 2, scene.getHeight() / 2);
        root.getTransforms().add(rotateTransform);
        Rectangle rectangle = new Rectangle(
                scene.getWidth() / 2 - 40, 0,
                80, 30
        );
        rectangle.setFill(Color.WHITE);
        root.getChildren().add(rectangle);
        rectangle.getTransforms().add(rotateTransform.createInverse());

        physicsSystem.init();
//        collisionSystem.init();
        profilingSystem.init();
        scrollingSystem.init();
//        spawnSystem.obstacleDeque.push(lemniscate);
//        spawnSystem.init();

        stage.setTitle("Rendering system test");
        stage.setScene(scene);
;

        stage.show();
    }

}