import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Score {
    static int totalScore;
    static int bestScore;

    public Score() {
        try (InputStream input = new FileInputStream("hyperparameters/score.properties")) {
            Properties p = new Properties();
            p.load(input);

            totalScore = Integer.parseInt(p.getProperty("score.total"));
            bestScore = Integer.parseInt(p.getProperty("score.best"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getTotalScore() {
        return totalScore;
    }

    public static int getBestScore() {
        return bestScore;
    }

    public static void setTotalScore(int totalScore1) {
        totalScore += totalScore1;

        try (InputStream input = new FileInputStream("hyperparameters\\score.properties")) {
            Properties p = new Properties();
            p.load(input);
            p.setProperty("score.total", String.valueOf(totalScore));
            p.store(new FileWriter("hyperparameters\\score.properties"),"Total score");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setBestScore(int bestScore1) {
        bestScore = Math.max(bestScore,bestScore1);

        try (InputStream input = new FileInputStream("hyperparameters/score.properties")) {
            Properties p = new Properties();
            p.load(input);
            p.setProperty("score.best", String.valueOf(bestScore));
            p.store(new FileWriter("hyperparameters\\score.properties"),"Best score");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}