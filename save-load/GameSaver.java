import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameSaver {
    static final String SAVEGAME_DIR = "savegames/";

    public static void serialize(SaveGame saveGame) {
        String filePath = SAVEGAME_DIR + saveGame.saveFileID;

        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(saveGame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
