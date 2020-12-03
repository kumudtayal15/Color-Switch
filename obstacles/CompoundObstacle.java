import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;

abstract public class CompoundObstacle extends Obstacle {

    protected final Group container;
    protected final Collection<Obstacle> children;
    protected EntityManager entityManager;

    protected static final Color[] colorMapping = {
            Color.web("#8C13FB"),
            Color.web("#F6DF0E"),
            Color.web("#35E2F2"),
            Color.web("#FF0080")};

    public CompoundObstacle(Vector2D anchorPoint, EntityManager entityManager) {
        super(anchorPoint);

        this.entityManager = entityManager;
        this.children = new ArrayList<>();
        this.container = new Group();

        this.container.setLayoutX(anchorPoint.x);
        this.container.setLayoutY(anchorPoint.y);
    }

    public CompoundObstacle(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        /*
        Constructor to facilitate level design
         */
        this(anchorPoint, entityManager);
    }

    abstract public void create(int colorIdx);

    public void create() {
        create(0);
    }

    abstract public Color getMeshColorSynced(int i, int colorIdx);

    @Override
    void markForDeletion(Pane sceneGraphRoot, ScrollingSystem scrollingSystem, EntityManager entityManager) {
        for (Obstacle o : children) {
            o.markForDeletion(sceneGraphRoot, scrollingSystem, entityManager);
        }

        sceneGraphRoot.getChildren().remove(this.container);
        scrollingSystem.remove(this.container);
    }

    @Override
    public Group getNode() {
        return container;
    }

    public void addChild(Obstacle childObstacle) {
        this.children.add(childObstacle);
        container.getChildren().add(childObstacle.getNode());
    }

    public void remove(Obstacle obstacle) {
//        stub
    }
}
