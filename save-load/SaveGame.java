import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class SaveGame implements Serializable {
    private static final long serialVersionUID = 1234L;
    protected final long saveFileID;
    private Vector2D playerPosition;
    private int score;
    private LocalDate date;
    private LocalTime time;
    private ArrayList<ObstacleStateContainer> queueContents;

    public SaveGame() {
        double SCREEN_WIDTH = 0, SCREEN_HEIGHT = 0;
        try (InputStream input = new FileInputStream("hyperparameters/display.properties")) {
            Properties p = new Properties();
           p.load(input);
            SCREEN_WIDTH = Double.parseDouble(p.getProperty("scene.width"));
            SCREEN_HEIGHT = Double.parseDouble(p.getProperty("scene.height"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Generate a random 5 digit ID
        Random number generator is initialized with current time as the seed
         */
        Random random = new Random(System.currentTimeMillis());
        saveFileID = 10000 + random.nextInt(90000);
        queueContents = new ArrayList<>();

        date = LocalDate.now();
        time = LocalTime.now();
        this.playerPosition = new Vector2D(SCREEN_WIDTH / 2, SCREEN_HEIGHT);
        this.score = 0;
    }

    public SaveGame(Vector2D playerPosition, int score) {
        this();
        this.playerPosition = playerPosition;
        this.score = score;
    }

    public void updateTimestamp() {
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Vector2D getPlayerPosition() {
        return playerPosition;
    }

    public double getScore() {
        return score;
    }

    public ArrayList<ObstacleStateContainer> getQueueContents() {
        return queueContents;
    }

    public void setQueueContents(ArrayList<ObstacleStateContainer> queueContents) {
        this.queueContents = queueContents;
    }

    public void setPlayerPosition(Vector2D playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "SaveGame{" +
                "saveFileID=" + saveFileID +
                ", playerPosition=" + playerPosition +
                ", totalScore=" + score +
                ", date=" + date +
                ", time=" + time +
                ", queueContents=" + queueContents +
                '}';
    }
}
