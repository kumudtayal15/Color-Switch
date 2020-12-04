import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EightPointStar extends CompoundObstacle {
    private double SLICE_WIDTH;
    private double SLICE_HEIGHT;
    static String path = "M28.38,17.6A14.19,14.19,0,1,1,14.19,3.41,14.18,14.18,0,0,1,28.38,17.6Zm13.1,17.59A14.19,14.19,0,1,0,55.67,49.38,14.19,14.19,0,0,0,41.48,35.19ZM65.37,64.7A14.19,14.19,0,1,0,79.56,78.89,14.19,14.19,0,0,0,65.37,64.7ZM52.86,0A14.19,14.19,0,1,0,67.05,14.19,14.19,14.19,0,0,0,52.86,0Zm253,152.34a14.19,14.19,0,1,0-14.19-14.19A14.19,14.19,0,0,0,305.81,152.34Zm-27.51-32a14.19,14.19,0,1,0-14.19-14.19A14.19,14.19,0,0,0,278.3,120.31ZM254.23,92.83A14.19,14.19,0,1,0,240,78.64,14.19,14.19,0,0,0,254.23,92.83Zm12.61,62.94a14.19,14.19,0,1,0-14.19-14.18A14.19,14.19,0,0,0,266.84,155.77Z";
    protected double rotationSpeed;
    protected double scaleFactor;

    public EightPointStar(Vector2D anchorPoint, EntityManager entityManager, double rotationSpeed, double scaleFactor) {
        super(anchorPoint, entityManager);

        this.rotationSpeed = rotationSpeed;
        this.scaleFactor = scaleFactor;
    }

    public EightPointStar(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.scaleFactor = 1.25;
        switch (level) {
            case EASY:
                this.rotationSpeed = 50;
                break;
            case MEDIUM:
                this.rotationSpeed = 75;
                break;
            case HARD:
                this.rotationSpeed = 100;
                break;
        }
    }

    @Override
    public void create(int colorIdx) {
        try (InputStream input = new FileInputStream("hyperparameters/obstacle.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            SLICE_WIDTH = Double.parseDouble(properties.getProperty("eightPointStar.sliceWidth"));
            SLICE_HEIGHT = Double.parseDouble(properties.getProperty("eightPointStar.sliceHeight"));
        } catch (IOException e) {
            System.out.println("I/O Exception occurred");
            e.printStackTrace();
        }

        Vector2D starCentre = new Vector2D(0, 0);
        PrimitiveObstacle[] diagonalSlice = new PrimitiveObstacle[4];

        MeshComponent meshComponent;
        RotationComponent rotationComponent;

        for (int i = 0; i < 4; i++) {
            diagonalSlice[i] = new PrimitiveObstacle(null);
            entityManager.register(diagonalSlice[i]);

            Group diagonalSliceContainer = getDiagonalSliceContainer(i * 45);
            Shape diagonalMesh = (SVGPath) diagonalSliceContainer.getChildren().get(0);

            meshComponent = new MeshComponent(
                    diagonalMesh,
                    getMeshColorSynced(i, colorIdx)
            );

            entityManager.addComponents(
                    diagonalSlice[i],
                    meshComponent
            );

            diagonalSlice[i].mesh = diagonalMesh;
            this.children.add(diagonalSlice[i]);
            container.getChildren().add(diagonalSliceContainer);

            rotationComponent = new RotationComponent(rotationSpeed, starCentre.x, starCentre.y);

            entityManager.addComponents(
                    diagonalSlice[i],
                    rotationComponent
            );
            diagonalSliceContainer.getTransforms().add(rotationComponent.getRotateTransform());
        }

    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[i % 4];
    }

    private Group getDiagonalSliceContainer(double rotationAngle) {
        Group container = new Group();

        SVGPath svgPath = new SVGPath();
        svgPath.setContent(path);
        svgPath.getTransforms().add(new Translate(-SLICE_WIDTH / 2, -SLICE_HEIGHT / 2));

        container.getChildren().add(svgPath);
        container.getTransforms().add(new Rotate(rotationAngle, 0, 0));
        container.getTransforms().add(new Scale(scaleFactor, scaleFactor));

        return container;
    }
}
