import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class AudioPlayer implements Runnable{
    MediaPlayer a_player;
    Media audio;
    String Path;
    AudioPlayer(String s){
        this.Path = s;
        this.audio = new Media(new File(Path).toURI().toString());
        this.a_player = new MediaPlayer(audio);
    }
    public void setVolume(double v){
        a_player.setVolume(v);
//        System.out.println("Volume : ");
    }
    @Override
    public void run() {
        a_player.setAutoPlay(true);
    }
}
