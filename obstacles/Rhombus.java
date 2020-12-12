import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rhombus extends CompoundObstacle {
    protected double sideLength;
    protected double sideThickness;
    protected double rotationSpeed;
    protected double skewAngle;

    public Rhombus(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double sideLength,
            double sideThickness,
            double rotationSpeed,
            double skewAngle) {

        super(anchorPoint, entityManager);
        this.sideLength = sideLength;
        this.sideThickness = sideThickness;
        this.rotationSpeed = rotationSpeed;
        this.skewAngle = skewAngle;
    }

    public Rhombus(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.sideLength = 250;
        this.sideThickness = 30;
        this.skewAngle = 75;

        switch (level) {
            case EASY:
                this.rotationSpeed = 100;
                break;
            case MEDIUM:
                this.rotationSpeed = 150;
                break;
            case HARD:
                this.rotationSpeed = 200;
                break;
        }
    }

    public void create(int colorIdx) {
        PrimitiveObstacle[] roundedRectangle = new PrimitiveObstacle[4];

        Rectangle[] side = new Rectangle[4];
        final double skewAngleInRad = Math.toRadians(skewAngle);

        side[0] = new Rectangle(
                sideLength / 2 - sideThickness / 2,
                -sideLength / 2,
                sideThickness, sideLength);
        side[0].setRotate(90 - skewAngle);

        side[1] = new Rectangle(
                sideLength / 2 * Math.cos(skewAngleInRad) - sideThickness / 2,
                -sideLength / 2 * Math.sin(skewAngleInRad) - sideLength / 2,
                sideThickness, sideLength);
        side[1].setRotate(90);

        side[2] = new Rectangle(
                -sideLength / 2 - sideThickness / 2,
                -sideLength / 2,
                sideThickness, sideLength);
        side[2].setRotate(90 - skewAngle);

        side[3] = new Rectangle(
                -sideLength / 2 * Math.cos(skewAngleInRad) - sideThickness / 2,
                sideLength / 2 * Math.sin(skewAngleInRad) - sideLength / 2,
                sideThickness, sideLength);
        side[3].setRotate(90);

        MeshComponent meshComponent;
        for (int i = 0; i < 4; i++) {
            side[i].setArcWidth(sideThickness);
            side[i].setArcHeight(sideThickness);

            roundedRectangle[i] = new PrimitiveObstacle(null);
            entityManager.register(roundedRectangle[i]);

            meshComponent = new MeshComponent(
                    side[i],
                    getMeshColorSynced(i, colorIdx)
            );
            entityManager.addComponents(
                    roundedRectangle[i],
//                    meshComponent::insertionCallback,
                    meshComponent
            );

            roundedRectangle[i].mesh = side[i];
            this.addChild(roundedRectangle[i]);
        }

        entityManager.register(this);
        RotationComponent rotationComponent = new RotationComponent(
                rotationSpeed, 0, 0);
        entityManager.addComponents(this, rotationComponent);

        this.container.getTransforms().add(rotationComponent.getRotateTransform());
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        Color c;
        try {
            c = colorMapping[(i + (colorIdx + 3)) % 4];
        } catch (NullPointerException e) {
            c = defaultColorMapping[(i + (colorIdx + 3)) % 4];
        }

        return c;
    }
}
