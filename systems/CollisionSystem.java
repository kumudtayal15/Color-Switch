import javafx.animation.AnimationTimer;
import javafx.scene.shape.Shape;

import java.util.List;

public class CollisionSystem extends BehaviourSystem {
    private Ball player;

    public CollisionSystem(EntityManager entityManager) {
        super(entityManager);
        timer = new AnimationTimer() {
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
        gameObjects = entityManager.getGameObjects(ComponentClass.valueOf("MESH"));

        for (GameObject gameObject : gameObjects) {
            meshComponent = (MeshComponent) entityManager.getComponent(
                    ComponentClass.valueOf("MESH"),
                    gameObject
            );

            Shape intersect = Shape.intersect(meshComponent.mesh, player.skin);

            if (intersect.getBoundsInLocal().getWidth() != -1) {
                if (gameObject instanceof PrimitiveObstacle) {
                    if (!meshComponent.mesh.getFill().equals(player.color)) {
                        player.isAlive = false;
                    }
                }
            }
        }
    }

    public void setPlayer(Ball player) {
        this.player = player;
    }
}
