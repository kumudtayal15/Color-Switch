import java.util.List;

public class RenderBehaviourSystem extends BehaviourSystem {

    public RenderBehaviourSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(double t) {
        List<GameObject> gameObjects =
                entityManager.getGameObjects(ComponentClass.valueOf("TRAJECTORY"));

        MeshComponent meshComponent;
        TrajectoryComponent trajectoryComponent;
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
    }
}
