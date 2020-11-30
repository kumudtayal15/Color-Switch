import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.util.List;

public class CollisionSystem extends BehaviourSystem {
    private Ball player;
    private ScrollingSystem scrollingSystem;

    public CollisionSystem(EntityManager entityManager, Pane sceneGraphRoot, Ball player, ScrollingSystem scrollingSystem) {
        super(entityManager, sceneGraphRoot);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update(l * Math.pow(10, -9));
            }
        };

        this.scrollingSystem = scrollingSystem;
        this.player = player;
    }

    @Override
    public void init() {
        assert player != null;
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

            Shape intersect = Shape.intersect(meshComponent.mesh, player.ballMesh);
            if (intersect.getBoundsInParent().getWidth() != -1) {
                if (gameObject instanceof PrimitiveObstacle) {
                    if (!meshComponent.mesh.getFill().equals(player.color)) {
                        player.isAlive = false;
                    }
                } else if (gameObject instanceof Star) {
                    player.setScore(player.score + 1);
                    sceneGraphRoot.getChildren().remove(((Star) gameObject).getNode());
                    scrollingSystem.remove(((Star) gameObject).getNode());
//                    entityManager.remove(gameObject);
                } else if (gameObject instanceof ColorSwitcher) {
                    player.setColor(((ColorSwitcher) gameObject).deltaColor);
                    sceneGraphRoot.getChildren().remove(((ColorSwitcher) gameObject).getNode());
                    scrollingSystem.remove(((ColorSwitcher) gameObject).getNode());
//                    entityManager.remove(gameObject);
                }
            }
        }
    }

    public void setPlayer(Ball player) {
        this.player = player;
    }
}
