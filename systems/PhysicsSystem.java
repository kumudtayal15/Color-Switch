import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.util.List;

public class PhysicsSystem extends BehaviourSystem {

    public PhysicsSystem(EntityManager entityManager, Pane sceneGraphRoot) {
        super(entityManager, sceneGraphRoot);
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update(l * Math.pow(10, -9));
            }
        };
    }

    @Override
    public void init() {
        timer.start();
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
