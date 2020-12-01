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

//        Group container = new Group();
//        SVGPath[] svgPaths = new SVGPath[6];
//        for (int i = 0; i < 5; i++) {
//            svgPaths[i] = new SVGPath();
//            svgPaths[i].setFill(Color.WHITE);
//            container.getChildren().add(svgPaths[i]);
//        }
//        svgPaths[0].setContent("M0,38.8l33,24l33-24L53.4,0H12.6L0,38.8z");
//        svgPaths[1].setContent("M65.3,56.9V24.5L31.6,0L0.8,10L0,45.2c8.8,10,19.8,18,32,23.2L65.3,56.9z");
//        root.getChildren().add(container);

        EntityManager entityManager = new EntityManager();

//        Star star = new Star(
//                entityManager,
//                new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2 + 150)
//        );
//        root.getChildren().add(star.getNode());

        Ball ball = new Ball(root);
//        ball.create(new Vector2D(0, 0), entityManager);
        ball.create(new Vector2D(scene.getWidth() / 2, scene.getHeight()), entityManager);
        scene.setOnKeyPressed(ball::impulse);

        ParticulateSquare particulateSquare = new ParticulateSquare(
                new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2),
                entityManager,
                250, 300,
                16, 17
        );
//        particulateSquare.create();
//        root.getChildren().add(particulateSquare.container);

        ParticulateCircle particulateCircle = new ParticulateCircle(
                new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2),
                entityManager,
                150,
                1,
                24,
                15
        );
//        particulateCircle.create();
//        root.getChildren().add(particulateCircle.container);

//        Lemniscate particleLemniscate = new Lemniscate(
//                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
//                entityManager,
//                70,
//                1.5,
//                12,
//                12
//        );
//        root.getChildren().add(particleLemniscate.container);

//        Triangle triangle = new Triangle(
//                new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2 - 200),
//                entityManager,
//                350,
//                20,
//                -100
//        );
//        root.getChildren().add(triangle.getNode());

        QuadArcCircle quadArcCircle = new QuadArcCircle(
                new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2),
                entityManager,
                150,
                "thick",
                100
        );
        quadArcCircle.create();
        root.getChildren().add(quadArcCircle.container);

//        ColorSwitcher colorSwitcher1 = new ColorSwitcher(
//                quadArcCircle.getAnchorPoint(),
//                entityManager
//        );
//        root.getChildren().add(colorSwitcher1.getNode());
//
//        Cartwheel cartwheel = new Cartwheel(
//                new Vector2D(scene.getWidth() / 2 - 100, scene.getHeight() / 2 - 450),
//                entityManager,
//                100,
//                50
//        );
//        cartwheel.create();
//        root.getChildren().add(cartwheel.container);
//
//        Cartwheel cartwheel1 = new Cartwheel(
//                new Vector2D(scene.getWidth() / 2 + 100, scene.getHeight() / 2 - 450),
//                entityManager,
//                100,
//                -50
//        );
//        cartwheel1.create();
//        root.getChildren().add(cartwheel1.container);
//
        Lemniscate lemniscate = new Lemniscate(
                new Vector2D(scene.getWidth() / 2, -500),
                entityManager,
                90,
                1,
                12,
                16
        );
//        root.getChildren().add(lemniscate.getNode());

        /*
        Ball decoupled from entity manager
         */

        PhysicsSystem physicsSystem = new PhysicsSystem(entityManager, root);

        InfoRenderSystem infoRenderSystem = new InfoRenderSystem(entityManager, root, ball, canvas.getGraphicsContext2D());
//        infoRenderSystem.setObjectTracking(true);
//        infoRenderSystem.setLocationCrosshairs(true);

        ScrollingSystem scrollingSystem = new ScrollingSystem(entityManager, root, root.getLayoutBounds());
        scrollingSystem.setPlayer(ball);
        scrollingSystem.add(quadArcCircle.getNode());

        CollisionSystem collisionSystem = new CollisionSystem(entityManager, root, ball, scrollingSystem);

        SpawnSystem spawnSystem = new SpawnSystem(entityManager, root, scrollingSystem);
        spawnSystem.obstacleDeque.push(quadArcCircle);

        physicsSystem.init();
        collisionSystem.init();
//        infoRenderSystem.init();
        scrollingSystem.init();
        spawnSystem.init();

        stage.setTitle("Rendering system test");
        stage.setScene(scene);

        stage.show();
    }

}