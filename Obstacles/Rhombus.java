import javafx.scene.shape.Rectangle;

public class Rhombus extends CompoundObstacle {

    public Rhombus(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double sideLength,
            double sideThickness,
            double rotationSpeed,
            double skewAngle) {

        super(anchorPoint);
        container.setLayoutX(anchorPoint.x);
        container.setLayoutY(anchorPoint.y);

        PrimitiveObstacle[] roundedRectangle = new PrimitiveObstacle[4];

        Rectangle[] side = new Rectangle[4];
        final double skewAngleInRad = skewAngle * (Math.PI / 180);

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
                    side[i], colorMapping[i]);
            entityManager.addComponents(
                    roundedRectangle[i],
                    meshComponent::insertionCallback,
                    meshComponent);

            this.addChild(roundedRectangle[i]);
        }

        entityManager.register(this);
        RotationComponent rotationComponent = new RotationComponent(
                rotationSpeed, 0, 0);
        entityManager.addComponents(this, rotationComponent);

        this.container.getTransforms().add(rotationComponent.getRotateTransform());
    }
}
