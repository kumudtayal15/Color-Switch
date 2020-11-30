import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.ArrayDeque;
import java.util.Collection;

//abstract public class CompoundObstacle extends Obstacle {
public class CompoundObstacle extends Obstacle {

    protected final Group container;
    protected final Collection<Obstacle> children;
    protected static final Color[] colorMapping = {
            Color.web("#8C13FB"),
            Color.web("#F6DF0E"),
            Color.web("#35E2F2"),
            Color.web("#FF0080")};

    public CompoundObstacle(Vector2D anchorPoint) {
        super(anchorPoint);

        this.children = new ArrayDeque<>();
        this.container = new Group();

        this.container.setLayoutX(anchorPoint.x);
        this.container.setLayoutY(anchorPoint.y);
    }

//    abstract void create();

    @Override
    public Group getNode() {
        return container;
    }

    @Override
    public void translate(Vector2D coordinates) {
        this.getAnchorPoint().x = coordinates.x;
        this.getAnchorPoint().y = coordinates.y;

        this.container.setLayoutX(coordinates.x);
        this.container.setLayoutY(coordinates.y);
    }

    public void addChild(Obstacle childObstacle) {
        this.children.add(childObstacle);
        container.getChildren().add(childObstacle.getNode());
    }

    public void remove(Obstacle obstacle) {
//        stub
    }
}
