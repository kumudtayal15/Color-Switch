import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class SaveGame implements Serializable {
    private static final long serialVersionUID = 1234L;
    protected final long saveFileID;
    private Vector2D playerPosition;
    private double totalStars;
    private final String filePath;
    private LocalDate date;
    private LocalTime time;
    private ArrayList<ObstacleStateContainer> queueContents;

    public SaveGame() {
        /*
        Generate a random 5 digit ID
        Random number generator is initialized with current time as the seed
         */
        Random random = new Random(System.currentTimeMillis());
        saveFileID = 10000 + random.nextInt(90000);
        filePath = "" + saveFileID;
        queueContents = new ArrayList<>();

        date = LocalDate.now();
        time = LocalTime.now();
        this.playerPosition = new Vector2D();
        this.totalStars = 0;
    }

    public SaveGame(Vector2D playerPosition, double totalStars) {
        this();
        this.playerPosition = playerPosition;
        this.totalStars = totalStars;
    }

    public void updateTimestamp() {
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public Vector2D getPlayerPosition() {
        return playerPosition;
    }

    public double getTotalStars() {
        return totalStars;
    }

    public String getFilePath() {
        return filePath;
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

    public void setTotalStars(double totalStars) {
        this.totalStars = totalStars;
    }

    @Override
    public String toString() {
        return "SaveGame{" +
                "saveFileID=" + saveFileID +
                ", playerPosition=" + playerPosition +
                ", totalStars=" + totalStars +
                ", filePath='" + filePath + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", queueContents=" + queueContents +
                '}';
    }
}
