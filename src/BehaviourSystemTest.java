import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;
import java.util.Random;

public class BehaviourSystemTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws NonInvertibleTransformException {


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

        final Vector2D SCREEN_CENTRE = new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2);

        Ball ball = new Ball(root);
        ball.create(SCREEN_CENTRE, entityManager);
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

//        ColorSwitcher colorSwitcher = new ColorSwitcher(
//                new Vector2D(SCREEN_CENTRE.x, SCREEN_CENTRE.y),
//                entityManager
//        );
//        root.getChildren().add(colorSwitcher.getNode());

        Star star = new Star(
                entityManager,
                SCREEN_CENTRE
        );
        root.getChildren().add(star.getNode());

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
//        profilingSystem.init();
        scrollingSystem.init();
        spawnSystem.obstacleDeque.push(eightPointStar);
        spawnSystem.init();

        SaveGame saveGame = new SaveGame();
        saveGame.setQueueContents(spawnSystem.pack());
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(saveGame.getFilePath()))) {
            outputStream.writeObject(saveGame);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        SaveGame saveGame1;
//        try(ObjectInputStream inputStream =
//                new ObjectInputStream(new FileInputStream("74580"))) {
//            saveGame1 = (SaveGame) inputStream.readObject();
//            System.out.println(saveGame1);
//            spawnSystem.unpackAndInitialize(saveGame1.getQueueContents());
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        spawnSystem.init();

        stage.setTitle("Rendering system test");
        stage.setScene(scene);

//        System.out.println(Cartwheel.class.getName());
        stage.show();
    }
}
