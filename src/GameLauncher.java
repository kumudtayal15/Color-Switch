import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameLauncher extends Application {
    SaveGame saveGame;
    private PhysicsSystem physicsSystem;
    private CollisionSystem collisionSystem;
    private ProfilingSystem profilingSystem;
    private ScrollingSystem scrollingSystem;
    private SpawnSystem spawnSystem;
    private Ball player;

    public GameLauncher() {
        load(12619);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        /*
        acquire scene width/height as specified in display.properties
         */
        double SCENE_WIDTH = 0, SCENE_HEIGHT = 0;

        try (InputStream input = new FileInputStream("hyperparameters/display.properties")) {
            Properties p = new Properties();
            p.load(input);
            SCENE_WIDTH = Double.parseDouble(p.getProperty("scene.width"));
            SCENE_HEIGHT = Double.parseDouble(p.getProperty("scene.height"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        initialize scene graph root
         */
        Pane root = new Pane();

        /*
        initialize scene with BLACK background color
         */
        final Color BLACK = Color.web("#292929");
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, BLACK);

        /*
        initialize entity manager
         */
        EntityManager entityManager = new EntityManager();

        /*
        initialize canvas
         */
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);

         /*
        initialize color scheme
         */
        ColorScheme colorScheme = new ColorScheme();

        final Vector2D SCREEN_CENTRE = new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2);
        player = new Ball(root, colorScheme.getColorMapping());
        player.create(
//                saveGame.getPlayerPosition(),
                new Vector2D(SCREEN_CENTRE.x, scene.getHeight()),
                entityManager
        );
        scene.setOnKeyPressed(player::impulse);


        /*
        initialize systems
         */
        this.physicsSystem = new PhysicsSystem(entityManager, root);

        this.scrollingSystem = new ScrollingSystem(entityManager, root, root.getLayoutBounds());
        scrollingSystem.setPlayer(player);

        this.spawnSystem = new SpawnSystem(entityManager, root, scrollingSystem, colorScheme.getColorMapping());
        spawnSystem.unpackAndInitialize(saveGame.getQueueContents());

        this.collisionSystem = new CollisionSystem(entityManager, root, player, scrollingSystem);

        this.profilingSystem = new ProfilingSystem(entityManager, root, player, canvas.getGraphicsContext2D());
        profilingSystem.setLocationCrosshairs();
//        profilingSystem.setObjectTracking();

        /*
        add a rotate transform to scene graph root
         */
        Rotate rotateTransform = new Rotate(0, scene.getWidth() / 2, scene.getHeight() / 2);
        root.getTransforms().add(rotateTransform);

        /*
        add player death subscribers
         */
        player.addDeathSubscriber(scrollingSystem);
        player.addDeathSubscriber(collisionSystem);
        player.addDeathSubscriber(spawnSystem);

        /*
        test space
         */

        /*
        initialize systems
         */
        initializeSystems();

        /*
        boilerplate
         */
        stage.setTitle("Color Switch");
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

    public SaveGame load(long gameID) {
        saveGame = GameLoader.load(gameID);
        return saveGame;
    }

    public long save(long gameID, boolean saveToDisk) {
        SaveGame prevSaveGame = GameLoader.load(gameID);
        _save(prevSaveGame, saveToDisk);

        return prevSaveGame.saveFileID;
    }

    public long save(boolean saveToDisk) {
        SaveGame newSaveGame = new SaveGame();
        _save(newSaveGame, saveToDisk);

        return newSaveGame.saveFileID;
    }

    private void _save(SaveGame saveGame, boolean saveToDisk) {
        saveGame.setQueueContents(spawnSystem.pack());
        saveGame.setPlayerPosition(new Vector2D(
                player.ballMeshWrapper.getTranslateX(),
                player.ballMeshWrapper.getTranslateY()
        ));
        saveGame.updateTimestamp();

        if (saveToDisk)
            GameSaver.serialize(saveGame);
    }
}
