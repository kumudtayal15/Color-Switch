import javafx.scene.paint.Color;

public class HarmonicCircle extends ParticulateObstacle {

    public HarmonicCircle(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectoryRadius,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager, trajectoryRadius, trajectorySpeed, particleCount, particleRadius);
        this.delayFactor = 2 * Math.PI / particleCount;
    }

    public HarmonicCircle(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.trajectorySize = 150;
        this.particleCount = 20;
        this.particleRadius = 20;
        this.delayFactor = 2 * Math.PI / particleCount;

        switch (level) {
            case EASY:
                this.trajectorySpeed = 1.5;
                break;
            case MEDIUM:
                this.trajectorySpeed = 2;
                break;
            case HARD:
                this.trajectorySpeed = 2.5;
                break;
        }
    }

    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[(i + (colorIdx + 1)) % 4];
    }

    @Override
    public TrajectoryComponent generateTrajectory() {
        return new OscillatingCircleTrajectory(trajectorySize, trajectorySpeed, 0);
    }
}
