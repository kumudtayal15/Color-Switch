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

//        Image image = new Image(new File("assets/colorswitcher.png").toURI().toString());
//        ImageView imageView = new ImageView(image);
//        imageView.setFitHeight(100);
//        imageView.setFitWidth(100);
//        imageView.setTranslateX(SCENE_WIDTH / 2);
//        imageView.setTranslateY(SCENE_HEIGHT / 2);
//        imageView.getTransforms().add(new Translate(-50, -50));
//        root.getChildren().add(imageView);

//        ColorSwitcher colorSwitcher = new ColorSwitcher(
//                entityManager,
//                new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2 + 150)
//        );
//        root.getChildren().add(colorSwitcher.container);

        Star star = new Star(
                entityManager,
                new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2 + 150)
        );
        root.getChildren().add(star.getContainer());

        Ball ball = new Ball(root);
        System.out.println(scene.getHeight());
        ball.create(new Vector2D(scene.getWidth() / 2, scene.getHeight()), entityManager);
        scene.setOnKeyPressed(ball::impulse);

        ParticulateSquare particulateSquare = new ParticulateSquare(
                new Vector2D(SCENE_WIDTH / 2 - 125, SCENE_HEIGHT / 2 + 150 - 300),
                entityManager,
                250, 300,
                16, 17
        );
//        particulateSquare.create();
//        root.getChildren().add(particulateSquare.container);

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
                new Vector2D(SCENE_WIDTH / 2, SCENE_HEIGHT / 2),
                entityManager,
                70,
                1.5,
                12,
                12
        );
        root.getChildren().add(particleLemniscate.container);

        /*
        Ball decoupled from entity manager
         */

//        HashMap<String, BehaviourSystem> systemHashMap = new HashMap<>(4);
//        systemHashMap.put("physics", new PhysicsSystem(entityManager));
//        systemHashMap.put("collision", new CollisionSystem(entityManager));

        PhysicsSystem physicsSystem = new PhysicsSystem(entityManager);

        CollisionSystem collisionSystem = new CollisionSystem(entityManager);
        collisionSystem.setPlayer(ball);

//        InfoRenderSystem infoRenderSystem = new InfoRenderSystem(entityManager, canvas.getGraphicsContext2D());
//        infoRenderSystem.setPlayer(ball);
//        infoRenderSystem.setObjectTracking(true);
//        infoRenderSystem.setLocationCrosshairs(true);

        ScrollingSystem scrollingSystem = new ScrollingSystem(entityManager, root.getLayoutBounds());
        scrollingSystem.setPlayer(ball);
        scrollingSystem.addAll(
//                particulateSquare.container,
//                colorSwitcher.getContainer(),
                star.getContainer(),
                particleLemniscate.container
        );

        physicsSystem.init();
        collisionSystem.init();
//        infoRenderSystem.init();
        scrollingSystem.init();

        stage.setTitle("Rendering system test");
        stage.setScene(scene);

        stage.show();
    }

}