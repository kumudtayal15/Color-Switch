import javafx.scene.Node;

abstract public class Obstacle extends GameObject {
    private Vector2D anchorPoint;

    public Obstacle(Vector2D anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    public Vector2D getAnchorPoint() {
        return anchorPoint;
    }

    public void setAnchorPoint(Vector2D anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    public abstract Node getNode();
}
