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

    public void init() {
        timer.start();
    }

    abstract public void update(double t);
}
