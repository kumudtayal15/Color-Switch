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
        ball.create(new Vector2D(scene.getWidth() / 2, scene.getHeight()), entityManager);
        scene.setOnKeyPressed(ball::impulse);

//        SYSTEMS
        PhysicsSystem physicsSystem = new PhysicsSystem(entityManager, root);

        InfoRenderSystem infoRenderSystem = new InfoRenderSystem(entityManager, root, ball, canvas.getGraphicsContext2D());
//        infoRenderSystem.setObjectTracking(true);
//        infoRenderSystem.setLocationCrosshairs(true);

        ScrollingSystem scrollingSystem = new ScrollingSystem(entityManager, root, root.getLayoutBounds());
        scrollingSystem.setPlayer(ball);

        CollisionSystem collisionSystem = new CollisionSystem(entityManager, root, ball, scrollingSystem);

        Cartwheel cartwheel1 = new Cartwheel(
                new Vector2D(scene.getWidth() / 2 - 50, scene.getHeight() / 2),
                entityManager,
                10,
                -100
        );
        cartwheel1.create(1);
        root.getChildren().add(cartwheel1.getNode());
        scrollingSystem.add(cartwheel1.getNode());

        QuadArcCircle quadArcCircle = new QuadArcCircle(
                new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2),
                entityManager,
                200,
                "thick",
                100
        );
        quadArcCircle.create(1);
        root.getChildren().add(quadArcCircle.getNode());
        scrollingSystem.add(quadArcCircle.getNode());

//        Cartwheel cartwheel2 = new Cartwheel(
//                new Vector2D(scene.getWidth() / 2 + 100, scene.getHeight() / 2),
//                entityManager,
//                100,
//                -100
//        );
//        cartwheel2.create(1);
//        root.getChildren().add(cartwheel2.getNode());
//        scrollingSystem.add(cartwheel2.getNode());

        physicsSystem.init();
        collisionSystem.init();
        infoRenderSystem.init();
        scrollingSystem.init();

        stage.setTitle("Rendering system test");
        stage.setScene(scene);

        stage.show();
    }

}