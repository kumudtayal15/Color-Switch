import javafx.scene.shape.Rectangle;

public class Triangle extends CompoundObstacle {

    public Triangle(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double sideLength,
            double sideThickness,
            double rotationSpeed) {

        super(anchorPoint);
        container.setLayoutX(anchorPoint.x);
        container.setLayoutY(anchorPoint.y);

        PrimitiveObstacle[] roundedRectangle = new PrimitiveObstacle[3];

        Rectangle[] side = new Rectangle[3];
        final double THIRTY = Math.PI / 6;
        double centroidToMid = sideLength * Math.tan(THIRTY) / 2;

        side[0] = new Rectangle(
                centroidToMid * Math.cos(THIRTY) - sideThickness / 2,
                -centroidToMid * Math.sin(THIRTY) - sideLength / 2,
                sideThickness, sideLength);
        side[0].setRotate(-30);

        side[1] = new Rectangle(
                -centroidToMid * Math.cos(THIRTY) - sideThickness / 2,
                -centroidToMid * Math.sin(THIRTY) - sideLength / 2,
                sideThickness, sideLength
        );
        side[1].setRotate(30);

        side[2] = new Rectangle(
                -sideThickness / 2,
                centroidToMid - sideLength / 2,
                sideThickness, sideLength
        );
        side[2].setRotate(90);

        MeshComponent meshComponent;
        for (int i = 0; i < 3; i++) {
            side[i].setArcWidth(sideThickness);
            side[i].setArcHeight(sideThickness);

            roundedRectangle[i] = new PrimitiveObstacle(null);
            entityManager.register(roundedRectangle[i]);
            meshComponent = new MeshComponent(
                    side[i], colorMapping[(i + 3) % 4]);
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

        /*
        Transform registration done manually instead of using a callback
        since compound obstacles lack a mesh component.
        Transform object must be added to the container instead.
         */
        this.container.getTransforms().add(rotationComponent.getRotateTransform());

//        TrajectoryComponent trajectoryComponent = new CircleTrajectory(
//                100, 50, 0);
//        entityManager.addComponents(this, trajectoryComponent);
    }
}
