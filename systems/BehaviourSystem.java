import javafx.animation.Animation;
import javafx.animation.AnimationTimer;

abstract public class BehaviourSystem {
    protected EntityManager entityManager;
    protected AnimationTimer timer;

    public BehaviourSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    abstract public void init();

    abstract public void update(double t);
}
