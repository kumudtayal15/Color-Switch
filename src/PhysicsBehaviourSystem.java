import java.util.List;

public class PhysicsBehaviourSystem extends BehaviourSystem {

    public PhysicsBehaviourSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(double t) {
        List<GameObject> gameObjects;

        MeshComponent meshComponent;
        TrajectoryComponent trajectoryComponent;
        RotationComponent rotationComponent;

        gameObjects = entityManager.getGameObjects(ComponentClass.valueOf("TRAJECTORY"));
        for (GameObject gameObject : gameObjects) {
            trajectoryComponent = (TrajectoryComponent) entityManager.getComponent(
                    ComponentClass.valueOf("TRAJECTORY"),
                    gameObject);

            meshComponent = (MeshComponent) entityManager.getComponent(
                    ComponentClass.valueOf("MESH"),
                    gameObject);

            meshComponent.getMesh().setTranslateX(trajectoryComponent.getPositionVector(t).x);
            meshComponent.getMesh().setTranslateY(trajectoryComponent.getPositionVector(t).y);
        }

        gameObjects = entityManager.getGameObjects(ComponentClass.valueOf("ROTATION"));
        for (GameObject gameObject : gameObjects) {
            rotationComponent = (RotationComponent) entityManager.getComponent(
                    ComponentClass.valueOf("ROTATION"),
                    gameObject);

            rotationComponent.rotate(t);
        }

    }
}
