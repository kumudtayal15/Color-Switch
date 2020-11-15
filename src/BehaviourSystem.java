abstract public class BehaviourSystem {
    protected EntityManager entityManager;

    public BehaviourSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    abstract public void update(double t);
}
