import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Testing extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        stage.setTitle("Color Switch");
        stage.setScene(new Scene(root,720,720));

        Controller.setStage(stage);
        stage.show();

    }
    public static void main(String[] args){

        String path = "C:\\Users\\sarth\\Desktop\\2019445_2019429_deadline3\\Color Switch\\fxml\\images\\audio.mp3";
        AudioPlayer a = new AudioPlayer(path);
        a.setVolume(0.04);
        Thread t1 = new Thread(a);
        t1.start();

        launch(args);

    }
}
