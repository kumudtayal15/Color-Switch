import javafx.application.Application;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javafx.scene.text.Font;
import java.io.File;

public class GameLauncher extends Application {
    SaveGame saveGame;
    private PhysicsSystem physicsSystem;
    protected CollisionSystem collisionSystem;
    private ProfilingSystem profilingSystem;
    private ScrollingSystem scrollingSystem;
    private SpawnSystem spawnSystem;
    private Ball player;

    public static Pane top_root;
    private static Parent p_m = null;
    public static Parent d_m = null;

    public GameLauncher() {
//        load(12619);
    }

    public static void remove_pause(){
        top_root.getChildren().remove(p_m);
    }
    public static void remove_death(){
        top_root.getChildren().remove(d_m);
    }
    public void stop_system(){
        physicsSystem.timer.stop();
        collisionSystem.timer.stop();
        profilingSystem.timer.stop();
        scrollingSystem.timer.stop();
        spawnSystem.timer.stop();
        player.motionTimer.stop();
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

        //        Pane root = (Pane) top_root.getChildren().get(0);
        Pane root = new Pane();
        root.setMinSize(720,720);
        root.setMaxSize(720,720);
        root.setPrefSize(720,720);
        top_root.getChildren().add(root);
        Controller.savegame = saveGame;

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
//        Pane root = new Pane();

        /*
        initialize scene with BLACK background color
         */
        final Color BLACK = Color.web("#292929");
        Scene scene = new Scene(top_root, SCENE_WIDTH, SCENE_HEIGHT, BLACK);

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
                saveGame.getPlayerPosition(),
//                new Vector2D(SCREEN_CENTRE.x - 200, SCREEN_CENTRE.y),
                entityManager
        );
        Controller.player = player;
        scene.setOnKeyPressed(player::impulse);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.P){
                    try {
//                    Parent p_m = null;
                        p_m = FXMLLoader.load(getClass().getResource("pausemenu.fxml"));
                        top_root.getChildren().add(p_m);
                        stop_system();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    player.impulse(keyEvent);
                }
            }
        });

        /*
        initialize systems
         */
        this.physicsSystem = new PhysicsSystem(entityManager, root);

        this.scrollingSystem = new ScrollingSystem(entityManager, root, root.getBoundsInParent());
        scrollingSystem.setPlayer(player);

        this.spawnSystem = new SpawnSystem(entityManager, root, scrollingSystem, colorScheme.getColorMapping());
        spawnSystem.unpackAndInitialize(saveGame.getQueueContents());
        player.setScore((int) saveGame.getScore());
        this.collisionSystem = new CollisionSystem(entityManager, root, player, scrollingSystem);

        this.profilingSystem = new ProfilingSystem(entityManager, root, player, canvas.getGraphicsContext2D());
        profilingSystem.setLocationCrosshairs();
//        profilingSystem.setObjectTracking();

               /*'
        Pause Image
         */
        Image pause_image = new Image(new File("fxml/images/pause.png").toURI().toString());
        ImageView pause = new ImageView(pause_image);
        pause.setFitWidth(50);
        pause.setFitHeight(50);
        pause.setTranslateY(50);
        pause.setTranslateX(50);
        top_root.getChildren().add(pause);
        pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
//                    Parent p_m = null;
                    p_m = FXMLLoader.load(getClass().getResource("pausemenu.fxml"));
                    top_root.getChildren().add(p_m);
                    stop_system();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Label score = new Label();
        score.setText(String.valueOf(saveGame.getScore())
        );
        score.setTextFill(Color.WHITE);
        score.setFont(Font.font("Proxima Nova", FontWeight.BOLD, 50));
        score.relocate(600,50);
        top_root.getChildren().add(1,score);

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
//        CompoundObstacle compoundObstacle = new AdjacentQuadCirclesVertical(SCREEN_CENTRE, entityManager, Level.EASY);
//        compoundObstacle.setColorMapping(colorScheme.getColorMapping());
//        root.getChildren().add(compoundObstacle.getNode());
//        compoundObstacle.create();

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
        prevSaveGame.setScore(player.getScore());
        prevSaveGame.setPlayerPosition(player.getPosition());
        _save(prevSaveGame, saveToDisk);
        this.saveGame = prevSaveGame;
//        Controller.savegame = prevSaveGame;
        return prevSaveGame.saveFileID;
    }

    public long save(boolean saveToDisk) {
        SaveGame newSaveGame = new SaveGame(player.getPosition(), player.getScore());
        _save(newSaveGame, saveToDisk);
        this.saveGame = newSaveGame;
//        Controller.savegame = newSaveGame;
        return newSaveGame.saveFileID;
    }

    private void _save(SaveGame saveGame, boolean saveToDisk) {
        saveGame.setQueueContents(spawnSystem.pack());
        saveGame.setPlayerPosition(new Vector2D(
                player.ballMeshWrapper.getTranslateX(),
                player.ballMeshWrapper.getTranslateY()
        ));
        saveGame.updateTimestamp();
//        System.out.println(saveGame.getDate() + " " + saveGame.getTime());
        Controller.savegame = saveGame;
        if (saveToDisk)
            GameSaver.serialize(saveGame);
    }
}
