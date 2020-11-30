import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

abstract public class BehaviourSystem {
    protected EntityManager entityManager;
    protected AnimationTimer timer;
    protected Pane sceneGraphRoot;

    public BehaviourSystem(EntityManager entityManager, Pane sceneGraphRoot) {
        this.entityManager = entityManager;
        this.sceneGraphRoot = sceneGraphRoot;
    }

    abstract public void init();

    abstract public void update(double t);
}
