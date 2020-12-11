import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Translate;

public abstract class SVGCollectible extends GameObject implements Collectible {
    protected final Group container;

    public SVGCollectible(EntityManager entityManager, Vector2D anchorPoint) {
        this.container = new Group();
        double scale = getScale();
        SVGPath path = getPath();

        container.setScaleX(scale);
        container.setScaleY(scale);
        container.getTransforms().add(new Translate(-128 / scale, -128 / scale));
        container.getChildren().add(path);
        container.setTranslateX(anchorPoint.x);
        container.setTranslateY(anchorPoint.y);

        entityManager.register(this);
        entityManager.addComponents(this, new MeshComponent(path, getColor()));
    }

    public Group getNode() {
        return container;
    }

    abstract SVGPath getPath();

    protected double getScale() {
        /*
        default scale is 0.15
         */
        return 0.15;
    }

    protected Color getColor() {
        /*
        default color is white
         */
        return Color.WHITE;
    }
}
