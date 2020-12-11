import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class PrimitiveObstacle extends Obstacle {
    protected Shape mesh;

    public PrimitiveObstacle(Vector2D anchorPoint) {
        super(anchorPoint);
    }

    @Override
    void markForDeletion(Pane sceneGraphRoot, ScrollingSystem scrollingSystem, EntityManager entityManager) {
        entityManager.deregister(this);
    }

    @Override
    public Shape getNode() {
        return mesh;
    }
}
