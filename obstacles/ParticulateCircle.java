import javafx.scene.shape.Rectangle;

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

    @Override
    public CircleTrajectory generateTrajectory() {
        return new CircleTrajectory(trajectorySize, trajectorySpeed, 0);
    }
}
