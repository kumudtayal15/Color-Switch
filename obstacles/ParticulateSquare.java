// TODO: 22/11/20 only rotates in anticlockwise direction!?

import javafx.scene.transform.Translate;

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
        this.container.getTransforms().add(new Translate(-trajectorySideLength / 2, trajectorySideLength / 2));
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
