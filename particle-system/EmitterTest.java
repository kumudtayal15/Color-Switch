import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmitterTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Emitter emitter = new Emitter(new Vector2D(1000, 540));

        stage.setTitle("Testing emitter");
        stage.setScene(new Scene(emitter, 1920, 1080));
        stage.show();
    }
}
