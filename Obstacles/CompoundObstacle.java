import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import java.util.ArrayDeque;
import java.util.Collection;

public class CompoundObstacle extends Obstacle {

    protected final Group container;
    protected final Collection<Obstacle> children;

    public CompoundObstacle(Vector2D anchorPoint) {
        super(anchorPoint);
        this.children = new ArrayDeque<>();
        this.container = new Group();
    }

    @Override
    public Node getNode() {
        return container;
    }

    public void add(Obstacle childObstacle) {
        this.children.add(childObstacle);
        container.getChildren().add(childObstacle.getNode());
    }

    public void remove(Obstacle obstacle) {
//        stub
    }
}
