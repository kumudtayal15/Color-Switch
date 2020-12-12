import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ColorScheme {
    private final Color[] colorMapping;

    public ColorScheme() {
        colorMapping = new Color[4];

        try (InputStream input = new FileInputStream("hyperparameters/colorscheme.properties")) {
            Properties p = new Properties();
            p.load(input);

            for (int i = 0; i < 4; i++) {
                String colorID = "color." + (i + 1);
                colorMapping[i] = Color.web(p.getProperty(colorID));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Color[] getColorMapping() {
        return colorMapping;
    }
}
