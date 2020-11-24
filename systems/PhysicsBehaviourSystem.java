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

//            if (gameObject instanceof Obstacle) {
                Obstacle obstacle = (Obstacle) gameObject;
                obstacle.getNode().setTranslateX(trajectoryComponent.getPositionVector(t).x);
                obstacle.getNode().setTranslateY(trajectoryComponent.getPositionVector(t).y);
//            }
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
