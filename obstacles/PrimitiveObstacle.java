import javafx.scene.Node;
import javafx.scene.shape.Shape;

public class PrimitiveObstacle extends Obstacle {
    protected Shape mesh;

    public PrimitiveObstacle(Vector2D anchorPoint) {
        super(anchorPoint);
    }

    @Override
    public Node getNode() {
        return mesh;
    }

    @Override
    public void translate(Vector2D coordinates) {
//        this.mesh.setTr

    }
}
