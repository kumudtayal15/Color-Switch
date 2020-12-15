import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Score {
    int totalScore;
    int bestScore;

    public Score() {
        try (InputStream input = new FileInputStream("hyperparameters/score.properties")) {
            Properties p = new Properties();
            p.load(input);

            this.totalScore = Integer.parseInt(p.getProperty("score.total"));
            this.bestScore = Integer.parseInt(p.getProperty("score.best"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;

        try (InputStream input = new FileInputStream("hyperparameters/score.properties")) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBestScore(int bestScore) {
        this.bestScore = Math.max(bestScore, this.bestScore);

        try (InputStream input = new FileInputStream("hyperparameters/score.properties")) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
