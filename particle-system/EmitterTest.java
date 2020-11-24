import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmitterTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Emitter emitter = new Emitter(new Vector2D(250, 250));

        stage.setTitle("Testing emitter");
        stage.setScene(new Scene(emitter, 500, 500));
        stage.show();
    }
}
