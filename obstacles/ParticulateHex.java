public class ParticulateHex extends ParticulateObstacle {

    public ParticulateHex(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectorySideLength,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager, trajectorySideLength, trajectorySpeed, particleCount, particleRadius);
        this.delayFactor = (6 * trajectorySideLength / trajectorySpeed) / particleCount;

        entityManager.register(this);
        RotationComponent rotationComponent = new RotationComponent(
                150,
                trajectorySideLength / 2,
                -trajectorySideLength * Math.sin(Math.PI / 3)
        );

        entityManager.addComponents(this, rotationComponent);
        this.container.getTransforms().add(rotationComponent.getRotateTransform());
    }

    @Override
    public TrajectoryComponent generateTrajectory() {
        return new HexTrajectory(trajectorySize, trajectorySpeed, 0);
    }
}
