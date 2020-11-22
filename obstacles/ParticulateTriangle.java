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
    }

    @Override
    public TrajectoryComponent generateTrajectory() {
        return new TriangleTrajectory(trajectorySize, trajectorySpeed, 0);
    }
}
