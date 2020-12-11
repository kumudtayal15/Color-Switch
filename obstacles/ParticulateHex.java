import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

public class ParticulateHex extends ParticulateObstacle {

    public ParticulateHex(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectorySideLength,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager, trajectorySideLength, trajectorySpeed, particleCount, particleRadius);

        this.container.getTransforms().add(new Translate(
                -trajectorySize / 2,
                trajectorySize * Math.cos(Math.PI / 6)
        ));
        this.delayFactor = (6 * trajectorySize / trajectorySpeed) / particleCount;
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

        this.container.getTransforms().add(new Translate(
                -trajectorySize / 2,
                trajectorySize * Math.cos(Math.PI / 6)
        ));

        this.delayFactor = (6 * trajectorySize / trajectorySpeed) / particleCount;
    }

    @Override
    public void create(int colorIdx) {
        super.create(colorIdx);

        entityManager.register(this);
        RotationComponent rotationComponent = new RotationComponent(
                100,
                trajectorySize / 2,
                -trajectorySize * Math.cos(Math.PI / 6)
        );
        entityManager.addComponents(this, rotationComponent);
        this.getNode().getTransforms().add(rotationComponent.getRotateTransform());
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[i % 4];
    }

    @Override
    public TrajectoryComponent generateTrajectory() {
        return new HexTrajectory(trajectorySize, trajectorySpeed, 0);
    }

    @Override
    public Vector2D getCentre() {
        Vector2D v = super.getCentre();
        v.x += trajectorySize / 2;
        v.y -= trajectorySize * Math.cos(Math.PI / 6);

        return v;
    }
}
