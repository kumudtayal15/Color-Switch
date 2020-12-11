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
    SaveGame saveGame;
    private PhysicsSystem physicsSystem;
    private CollisionSystem collisionSystem;
    private ProfilingSystem profilingSystem;
    private ScrollingSystem scrollingSystem;
    private SpawnSystem spawnSystem;
    private Ball player;

    public BehaviourSystemTest() {
        load(74580);
    }

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

        player = new Ball(root);
        player.create(
//                saveGame.getPlayerPosition(),
                new Vector2D(SCREEN_CENTRE.x, scene.getHeight()),
                entityManager
        );
        scene.setOnKeyPressed(player::impulse);

        this.physicsSystem = new PhysicsSystem(entityManager, root);

        this.profilingSystem = new ProfilingSystem(entityManager, root, player, canvas.getGraphicsContext2D());

        this.scrollingSystem = new ScrollingSystem(entityManager, root, root.getLayoutBounds());
        scrollingSystem.setPlayer(player);
        player.addDeathSubscriber(scrollingSystem);

        this.collisionSystem = new CollisionSystem(entityManager, root, player, scrollingSystem);
        player.addDeathSubscriber(collisionSystem);

        this.spawnSystem = new SpawnSystem(entityManager, root, scrollingSystem);
        player.addDeathSubscriber(spawnSystem);

        EightPointStar eightPointStar = new EightPointStar(
                new Vector2D(SCREEN_CENTRE.x, SCREEN_CENTRE.y),
                entityManager,
                Level.EASY
        );
        eightPointStar.create();
        root.getChildren().add(eightPointStar.getNode());
        scrollingSystem.add(eightPointStar.getNode());
        spawnSystem.obstacleDeque.addLast(eightPointStar);
//
//        SVGCollectible svgCollectible = new RandomRotate(
//                entityManager,
//                SCREEN_CENTRE
//        );
//        root.getChildren().add(svgCollectible.getNode());

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

        spawnSystem.unpackAndInitialize(saveGame.getQueueContents());
        initializeSystems();
//        save(true);

        stage.setTitle("Rendering system test");
        stage.setScene(scene);
        stage.show();
    }

    public void initializeSystems() {
        physicsSystem.init();
        collisionSystem.init();
        profilingSystem.init();
        scrollingSystem.init();
        spawnSystem.init();
    }

    public SaveGame load(int gameID) {
        saveGame = GameLoader.load(gameID);
        return saveGame;
    }

    public long save(long gameID, boolean saveToDisk) {
        SaveGame prevSaveGame = GameLoader.load(gameID);
        prevSaveGame.setQueueContents(spawnSystem.pack());
        prevSaveGame.setPlayerPosition(new Vector2D(
                player.ballMeshWrapper.getTranslateX(),
                player.ballMeshWrapper.getTranslateY()
        ));
        prevSaveGame.updateTimestamp();

        if (saveToDisk)
            GameSaver.serialize(prevSaveGame);
        return prevSaveGame.saveFileID;
    }

    public long save(boolean saveToDisk) {
        SaveGame newSaveGame = new SaveGame();
        newSaveGame.setQueueContents(spawnSystem.pack());
        newSaveGame.setPlayerPosition(new Vector2D(
                player.ballMeshWrapper.getTranslateX(),
                player.ballMeshWrapper.getTranslateY()
        ));
        newSaveGame.updateTimestamp();

        if (saveToDisk)
            GameSaver.serialize(newSaveGame);
        return newSaveGame.saveFileID;
    }
}
