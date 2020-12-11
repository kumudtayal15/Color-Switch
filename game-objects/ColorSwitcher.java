import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class ColorSwitcher extends GameObject implements Collectible {
    static final double radius = 20;
    static final Color[] colorMapping = {
            Color.web("#8C13FB"),
            Color.web("#F6DF0E"),
            Color.web("#35E2F2"),
            Color.web("#FF0080")};
    protected final Group container;
    protected Color deltaColor;

    public ColorSwitcher(Vector2D anchorPoint, EntityManager entityManager) {
        container = new Group();

        container.setTranslateX(anchorPoint.x);
        container.setTranslateY(anchorPoint.y);

        this.deltaColor = colorMapping[new Random(System.currentTimeMillis()).nextInt(4)];

        entityManager.register(this);

        Circle circleMesh = new Circle(radius);
        entityManager.addComponents(this, new MeshComponent(circleMesh));
        container.getChildren().add(circleMesh);

        for (int i = 0; i < 4; i++) {
            Shape roundArc = getRoundArc(i * 90, colorMapping[i % 4]);
            container.getChildren().add(roundArc);
        }
    }

    private Shape getRoundArc(double startAngle, Color color) {
        Arc arc = new Arc(0, 0,radius, radius, startAngle, 90);
        arc.setType(ArcType.ROUND);
        arc.setFill(color);

        return arc;
    }

    public static double getRadius() {
        return radius;
    }

    public Group getNode() {
        return container;
    }

    public Color getDeltaColor() {
        return deltaColor;
    }

    public void setDeltaColor(Color deltaColor) {
        this.deltaColor = deltaColor;
    }
}
