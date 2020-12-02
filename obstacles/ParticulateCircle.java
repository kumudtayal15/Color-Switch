import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class ParticulateCircle extends ParticulateObstacle {

    public ParticulateCircle(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectoryRadius,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager, trajectoryRadius, trajectorySpeed, particleCount, particleRadius);
        this.delayFactor = 2 * Math.PI / particleCount;
        /*
        A 45 degree rotation ensures that each group fits neatly along the axis
         */
        this.getNode().getTransforms().add(new Rotate(45, 0, 0));

    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[(i + (colorIdx + 1)) % 4];
    }

    @Override
    public CircleTrajectory generateTrajectory() {
        return new CircleTrajectory(trajectorySize, trajectorySpeed, 0);
    }
}
