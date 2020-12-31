// TODO: 22/11/20 only rotates in anticlockwise direction!?

import javafx.scene.paint.Color;
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
        this.container.getTransforms().add(new Translate(
                -trajectorySize / 2,
                trajectorySize / 2
        ));
        this.delayFactor = (4 * trajectorySideLength / trajectorySpeed) / particleCount;
    }

//    @Override
//    public MeshComponent generateParticle() {
//        return new MeshComponent(new Rectangle(20, 20));
//    }

    public ParticulateSquare(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.trajectorySize = 200;
        this.particleCount = 20;
        this.particleRadius = 14;

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
                trajectorySize / 2
        ));
        this.delayFactor = (4 * trajectorySize / trajectorySpeed) / particleCount;
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[i % 4];
    }

    @Override
    public SquareTrajectory generateTrajectory() {
        return new SquareTrajectory(trajectorySize, trajectorySpeed, 0);
    }

    @Override
    public Vector2D getCentre() {
        Vector2D v = super.getCentre();
        v.x += trajectorySize / 2;
        v.y -= trajectorySize / 2;

        return v;
    }
}
