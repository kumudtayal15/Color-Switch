import javafx.scene.Node;
import javafx.scene.layout.Pane;

abstract public class Obstacle extends GameObject {
    protected Vector2D anchorPoint;

    public Obstacle(Vector2D anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    abstract void markForDeletion(Pane sceneGraphRoot, ScrollingSystem scrollingSystem, EntityManager entityManager);

    public Vector2D getAnchorPoint() {
        return anchorPoint;
    }

    public void setAnchorPoint(Vector2D anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    public abstract Node getNode();
}
