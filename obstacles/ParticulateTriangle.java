import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

public class ParticulateTriangle extends ParticulateObstacle {

    public ParticulateTriangle(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectorySideLength,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager, trajectorySideLength, trajectorySpeed, particleCount, particleRadius);
        this.delayFactor = (3 * trajectorySideLength / trajectorySpeed) / particleCount;

        this.container.getTransforms().add(new Translate(
                -trajectorySideLength / 2,
                trajectorySideLength / 2 * Math.tan(Math.PI / 6)
        ));
    }

    public ParticulateTriangle(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.trajectorySize = 350;
        this.particleCount = 20;
        this.particleRadius = 20;

        switch (level) {
            case EASY:
                this.trajectorySpeed = 170;
                break;
            case MEDIUM:
                this.trajectorySpeed = 250;
                break;
            case HARD:
                this.trajectorySpeed = 300;
                break;
        }

        this.container.getTransforms().add(new Translate(
                -trajectorySize / 2,
                trajectorySize / 2 * Math.tan(Math.PI / 6)
        ));
        this.delayFactor = (3 * trajectorySize / trajectorySpeed) / particleCount;
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[i % 4];
    }

    @Override
    public TrajectoryComponent generateTrajectory() {
        return new TriangleTrajectory(trajectorySize, trajectorySpeed, 0);
    }

    @Override
    public Vector2D getCentre() {
        Vector2D v = super.getCentre();
        v.x += trajectorySize / 2;
        v.y -= trajectorySize / 2 * Math.tan(Math.PI / 6);

        return v;
    }
}
