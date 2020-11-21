import javafx.scene.shape.Rectangle;

public class ParticulateSquare extends ParticulateObstacle {

    public ParticulateSquare(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectorySideLength,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager, trajectorySideLength, trajectorySpeed, particleCount, particleRadius);
        this.delayFactor = (4 * trajectorySideLength / trajectorySpeed) / particleCount;
    }

//    @Override
//    public MeshComponent generateParticle() {
//        return new MeshComponent(new Rectangle(20, 20));
//    }

    @Override
    public SquareTrajectory generateTrajectory() {
        return new SquareTrajectory(trajectorySize, trajectorySpeed, 0);
    }


}
