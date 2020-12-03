import javafx.scene.paint.Color;

public class ParticulateHex extends ParticulateObstacle {

    public ParticulateHex(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectorySideLength,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager, trajectorySideLength, trajectorySpeed, particleCount, particleRadius);

        this.delayFactor = (6 * trajectorySize / trajectorySpeed) / particleCount;
        this.container.setLayoutX(anchorPoint.x - trajectorySize / 2);
        this.container.setLayoutY(anchorPoint.y + trajectorySize * Math.cos(Math.PI / 6));
    }

    public ParticulateHex(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.trajectorySize = 200;
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

        this.delayFactor = (6 * trajectorySize / trajectorySpeed) / particleCount;
        this.container.setLayoutX(anchorPoint.x - trajectorySize / 2);
        this.container.setLayoutY(anchorPoint.y + trajectorySize * Math.cos(Math.PI / 6));
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[i % 4];
    }

    @Override
    public TrajectoryComponent generateTrajectory() {
        return new HexTrajectory(trajectorySize, trajectorySpeed, 0);
    }
}
