import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollisionSystem extends BehaviourSystem implements PlayerDeathSubscriber {
    private Ball player;
    private final ScrollingSystem scrollingSystem;

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
                } else if (gameObject instanceof Collectible) {
                    Class<?> gameObjectClass = gameObject.getClass();
                    Random random = new Random(System.currentTimeMillis());

                    if (gameObjectClass.equals(Star.class)) {
                        player.setScore(player.score + 1);
                    } else if (gameObjectClass.equals(RandomRotate.class)) {
                        Rotate rotate = (Rotate) sceneGraphRoot.getTransforms().get(0);
                        final int lowerBound = 60;
                        rotate.setAngle(lowerBound + random.nextInt(361 - lowerBound));
//                        rotate.setAngle(90 * random.nextInt(4));
                    } else if (gameObjectClass.equals(ColorSwitcher.class)) {
                        player.setColor(((ColorSwitcher) gameObject).deltaColor);
                    } else if (gameObjectClass.equals(SkinChanger.class)) {
                        SVGPath path = (SVGPath) player.ballMesh;
                        ArrayList<String> skinSVGList = player.getSkinSVGList();

                        String playerSkinSVG;
                        do {
                            playerSkinSVG = skinSVGList.get(random.nextInt(skinSVGList.size()));
                        } while (playerSkinSVG.equals(path.getContent()));
                        path.setContent(playerSkinSVG);
                    }

                    Group node = ((Collectible) gameObject).getNode();
                    sceneGraphRoot.getChildren().remove(node);
                    scrollingSystem.remove(node);
                    entityManager.deregister(gameObject);
                }
            }
        }

    }

    @Override
    public void onPlayerDeath() {
        this.timer.stop();
    }

    public void setPlayer(Ball player) {
        this.player = player;
    }
}
