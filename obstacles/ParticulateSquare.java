// TODO: 22/11/20 only rotates in anticlockwise direction!?

import javafx.scene.transform.Rotate;
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
        this.container.setLayoutX(anchorPoint.x - trajectorySideLength / 2);
        this.container.setLayoutY(anchorPoint.y + trajectorySideLength / 2);
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
