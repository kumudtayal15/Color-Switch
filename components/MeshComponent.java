import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class MeshComponent extends Component {
    protected final Shape mesh;

    public MeshComponent(Shape mesh, Color fillColor) {
        this.mesh = mesh;
        mesh.setFill(fillColor);
        setComponentClass(ComponentClass.valueOf("MESH"));
    }

    public MeshComponent(Shape mesh) {
        this.mesh = mesh;
        setComponentClass(ComponentClass.valueOf("MESH"));
    }

    public Bounds getAbsoluteBounds() {
        return mesh.localToScene(mesh.getLayoutBounds());
    }

    public Shape getMesh() {
        return mesh;
    }

    public void insertionCallback(EntityManager entityManager, GameObject gameObject) {
        if (gameObject.getClass() == PrimitiveObstacle.class) {
            PrimitiveObstacle obstacle = (PrimitiveObstacle) gameObject;
            obstacle.mesh = mesh;
        }
    }
}
