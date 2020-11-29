import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.function.Function;

public class MeshComponent extends Component {
    protected final Shape mesh;
    protected Function<Shape, Boolean> collisionHook;

    public MeshComponent(Shape mesh, Color fillColor) {
        this.mesh = mesh;
        mesh.setFill(fillColor);
        setComponentClass(ComponentClass.valueOf("MESH"));

//        default collision condition
        this.collisionHook = otherMesh -> {
            Shape intersect = Shape.intersect(mesh, otherMesh);
            return intersect.getBoundsInLocal().getWidth() != -1;
        };
    }

    public MeshComponent(Shape mesh) {
        this.mesh = mesh;
        setComponentClass(ComponentClass.valueOf("MESH"));
    }

    public Boolean isCollide(Shape otherMesh) {
        return collisionHook.apply(otherMesh);
    };

    public void setCollisionHook(Function<Shape, Boolean> collisionHook) {
        this.collisionHook = collisionHook;
    }

    public Bounds getAbsoluteBounds() {
        return mesh.localToScene(mesh.getBoundsInLocal());
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
