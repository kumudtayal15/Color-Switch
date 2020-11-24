import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import java.util.List;

public class CollisionBehaviourSystem extends BehaviourSystem {
    private boolean objectTracking;
    private Ball trackedBall;
    private GraphicsContext graphicsContext;

    public CollisionBehaviourSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(double t) {
        List<GameObject> gameObjects;

        if (objectTracking) {
            graphicsContext.clearRect(
                    0, 0,
                    graphicsContext.getCanvas().getWidth(),
                    graphicsContext.getCanvas().getHeight()
            );
        }

        MeshComponent meshComponent;
        gameObjects = entityManager.getGameObjects(ComponentClass.valueOf("MESH"));

        for (GameObject gameObject : gameObjects) {
            meshComponent = (MeshComponent) entityManager.getComponent(
                    ComponentClass.valueOf("MESH"),
                    gameObject
            );

            Shape intersect = Shape.intersect(meshComponent.mesh, trackedBall.skin);
            if (intersect.getBoundsInLocal().getWidth() != -1) {
                if (!meshComponent.mesh.getFill().equals(trackedBall.color)) {
                    trackedBall.isAlive = false;
                }
            }

            if (objectTracking) {
                displayBoundingRect(trackedBall.skin.getBoundsInParent());
                displayBoundingRect(meshComponent.getAbsoluteBounds());
            }
        }
    }

    private void displayBoundingRect(Bounds bounds) {
        graphicsContext.strokeRect(
                bounds.getMinX(),
                bounds.getMinY(),
                bounds.getWidth(),
                bounds.getHeight()
        );
    }

    public void setObjectTracking(boolean objectTracking) {
        this.objectTracking = objectTracking;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        graphicsContext.setStroke(Color.WHITE);
    }

    public void setTrackedBall(Ball trackedBall) {
        this.trackedBall = trackedBall;
    }
}
