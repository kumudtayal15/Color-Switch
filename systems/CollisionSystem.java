import javafx.animation.AnimationTimer;

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

//            Shape intersect = Shape.intersect(meshComponent.mesh, player.ballMesh);

//            if (intersect.getBoundsInParent().getWidth() != -1) {
            if (meshComponent.isCollide(player.ballMesh)) {
                if (gameObject instanceof PrimitiveObstacle) {
                    if (!meshComponent.mesh.getFill().equals(player.color)) {
                        player.isAlive = false;
                    }
                } else if (gameObject instanceof Star) {

                } else if (gameObject instanceof ColorSwitcher) {

                }
            }
        }
    }

    public void setPlayer(Ball player) {
        this.player = player;
    }
}
