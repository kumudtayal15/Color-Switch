import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GameLoader {
    private static final String SAVEGAME_DIR = "savegames/";

    public static SaveGame load(long gameID) {
        SaveGame saveGame = null;

        String filePath = SAVEGAME_DIR + Long.toString(gameID);
        try (ObjectInputStream inputStream =
                     new ObjectInputStream(new FileInputStream(filePath))) {
            saveGame = (SaveGame) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert saveGame != null;
        return saveGame;
    }
}
