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

    @Override
    public TrajectoryComponent generateTrajectory() {
        return new OscillatingCircleTrajectory(trajectorySize, trajectorySpeed, 0);
    }
}
