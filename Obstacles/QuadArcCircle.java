import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;

public class QuadArcCircle extends CompoundObstacle {
    public QuadArcCircle(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double outerRadius,
            double thickness,
            double rotationSpeed) {

        super(anchorPoint);
        container.setLayoutX(anchorPoint.x);
        container.setLayoutY(anchorPoint.y);

        Vector2D circleCenter = new Vector2D(0, 0);
        PrimitiveObstacle[] solidArcs = new PrimitiveObstacle[4];
        Color[] colorMapping = {Color.web("#8C13FB"), Color.web("#F6DF0E"), Color.web("#35E2F2"), Color.web("#FF0080")};

//        Arcs are numbered anticlockwise, from the first quadrant
        MeshComponent meshComponent;
        RotationComponent rotationComponent;
        for (int i = 0; i < 4; i++) {
            solidArcs[i] = new PrimitiveObstacle(circleCenter);
            entityManager.register(solidArcs[i]);

            Shape arcMesh = getSolidArc(circleCenter, outerRadius, thickness, i * 90);
            meshComponent = new MeshComponent(
                    arcMesh,
                    colorMapping[i]);
            entityManager.addComponents(
                    solidArcs[i],
                    meshComponent::insertionCallback,
                    meshComponent);
            container.getChildren().add(arcMesh);

            rotationComponent = new RotationComponent(rotationSpeed, circleCenter.x, circleCenter.y);
            entityManager.addComponents(
                    solidArcs[i],
                    rotationComponent::insertionCallback,
                    rotationComponent);
        }
    }

    private Shape getSolidArc(Vector2D circleCenter, double outerRadius, double thickness, double startAngle) {
        Arc outer = new Arc(
                circleCenter.x,
                circleCenter.y,
                outerRadius,
                outerRadius,
                startAngle,
                90);
        Arc inner = new Arc(
                circleCenter.x,
                circleCenter.y,
                outerRadius - thickness,
                outerRadius - thickness,
                startAngle,
                90);

        outer.setType(ArcType.ROUND);
        inner.setType(ArcType.ROUND);

        return Shape.subtract(outer, inner);
    }

    private Shape getSolidArc(Vector2D circleCenter, double outerRadius, double thickness, double startAngle, Color color) {
        Shape solidArc = getSolidArc(circleCenter, outerRadius, thickness, startAngle);
        solidArc.setFill(color);

        return solidArc;
    }
}
