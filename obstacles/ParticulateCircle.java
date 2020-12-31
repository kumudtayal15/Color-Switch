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
    }

    public ParticulateCircle(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.trajectorySize = 130;
        this.particleCount = 20;
        this.particleRadius = 17;
        this.delayFactor = 2 * Math.PI / particleCount;

        switch (level) {
            case EASY:
                this.trajectorySpeed = 1.5;
                break;
            case EASY_NEG:
                this.trajectorySpeed = -1.5;
                break;
            case MEDIUM:
                this.trajectorySpeed = 2;
                break;
            case HARD:
                this.trajectorySpeed = 2.5;
                break;
        }
    }

    @Override
    public void create(int colorIdx) {
        /*
        A 45 degree rotation ensures that each group fits neatly along the axis
         */
        this.getNode().getTransforms().add(new Rotate(45, 0, 0));
        super.create(colorIdx);
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
