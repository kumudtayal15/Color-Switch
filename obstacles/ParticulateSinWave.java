public class ParticulateSinWave extends ParticulateObstacle {
    public ParticulateSinWave(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectoryAmplitude,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager, trajectoryAmplitude, trajectorySpeed, particleCount, particleRadius);
        this.delayFactor = 2 * Math.PI /  particleCount;
    }

    @Override
    public TrajectoryComponent generateTrajectory() {
        return new SinWaveTrajectory(trajectorySize, trajectorySpeed, 0);
    }
}
