import javafx.application.Application;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
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
        add reference arrow
         */
        SVGPath directionArrow = new SVGPath();
        final double DIR_ARROW_SCALE = 0.25;
        directionArrow.getTransforms().add(new Scale(DIR_ARROW_SCALE, DIR_ARROW_SCALE));
        directionArrow.setContent("M76.6,1.17a5.14,5.14,0,0,0-.66.66L1.87,74.34a5.86,5.86,0,0,0,3.9,10.14h39v93.94c0,3.51.78,7.41,3.9,7.41H111a9,9,0,0,0,7.8-7.41V84.48h35.09A6.23,6.23,0,0,0,159.36,81V78.63a6.2,6.2,0,0,0-2-4.29L83.74,1.83A5.07,5.07,0,0,0,76.6,1.17ZM42.8,203.37a5.85,5.85,0,0,0,5.85,5.85H111a5.85,5.85,0,1,0,0-11.69H48.65A5.84,5.84,0,0,0,42.8,203.37Zm0,23.39a5.85,5.85,0,0,0,5.85,5.85H111a5.85,5.85,0,1,0,0-11.69H48.65A5.84,5.84,0,0,0,42.8,226.76Zm0,23.39A5.85,5.85,0,0,0,48.65,256H111a5.85,5.85,0,1,0,0-11.69H48.65A5.84,5.84,0,0,0,42.8,250.15Z");
        directionArrow.setCache(true);
        directionArrow.setCacheHint(CacheHint.QUALITY);
        directionArrow.setTranslateX(scene.getWidth() / 2 - 160 / 2d * DIR_ARROW_SCALE);
        directionArrow.setTranslateY(scene.getHeight() / 1.15 - 128 * DIR_ARROW_SCALE);
        directionArrow.setFill(Color.WHITE);

        root.getChildren().add(directionArrow);

         /*
        initialize color scheme
         */
        ColorScheme colorScheme = new ColorScheme();

        final Vector2D SCREEN_CENTRE = new Vector2D(scene.getWidth() / 2, scene.getHeight() / 2);
        player = new Ball(root, colorScheme.getColorMapping());
        player.create(
//                saveGame.getPlayerPosition(),
//                new Vector2D(SCREEN_CENTRE.x, scene.getHeight()),
                SCREEN_CENTRE,
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
        collisionSystem.halt(5000);
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
        prevSaveGame.setScore(player.getScore());
        prevSaveGame.setPlayerPosition(player.getPosition());
        _save(prevSaveGame, saveToDisk);

        return prevSaveGame.saveFileID;
    }

    public long save(boolean saveToDisk) {
        SaveGame newSaveGame = new SaveGame(player.getPosition(), player.getScore());
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
