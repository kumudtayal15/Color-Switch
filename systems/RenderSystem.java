import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.List;

public class RenderSystem extends BehaviourSystem {
    private boolean objectTracking;
    private Ball player;
    private GraphicsContext graphicsContext;
    private final Font BIG_FONT;
    private final Font SMALL_FONT;

    public RenderSystem(EntityManager entityManager, GraphicsContext graphicsContext) {
        super(entityManager);

        this.graphicsContext = graphicsContext;
        BIG_FONT = new Font("Consolas", 30);
        SMALL_FONT = new Font("Consolas", 12);

        timer = new AnimationTimer() {
            double prevTimeStamp = System.nanoTime();

            @Override
            public void handle(long l) {
                update(l * Math.pow(10, -9));

                final Canvas canvas = graphicsContext.getCanvas();
                graphicsContext.setStroke(Color.LIGHTGREEN);
                graphicsContext.setLineWidth(1);
                graphicsContext.strokeRect(
                        5, 5,
                        canvas.getWidth() - 5, canvas.getHeight() - 5
                );

                graphicsContext.setFill(Color.RED);
                graphicsContext.setFont(BIG_FONT);
                long fps = (long) (Math.pow(10, 9) / (l - prevTimeStamp));
                graphicsContext.fillText(fps + " FPS", 20, 30);

                graphicsContext.setFill(Color.WHITE);
                graphicsContext.setFont(SMALL_FONT);

                graphicsContext.fillText("ball height: " + (int) player.skin.getTranslateY(), 20, 50);
//                graphicsContext.fillText("objects on screen: " + );

                prevTimeStamp = l;
            }
        };

    }

    @Override
    public void init() {
        timer.start();
    }

    @Override
    public void update(double t) {
        graphicsContext.clearRect(
                0, 0,
                graphicsContext.getCanvas().getWidth(),
                graphicsContext.getCanvas().getHeight()
        );



        if (objectTracking) {
            graphicsContext.setStroke(Color.GREEN);
            graphicsContext.strokeLine(
                    0, graphicsContext.getCanvas().getHeight() / 2,
                    graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight() / 2
            );

            List<GameObject> gameObjects;
            gameObjects = entityManager.getGameObjects(ComponentClass.valueOf("MESH"));
            MeshComponent meshComponent;

            for (GameObject gameObject : gameObjects) {
                meshComponent = (MeshComponent) entityManager.getComponent(
                        ComponentClass.valueOf("MESH"),
                        gameObject
                );

                displayBoundingRect(meshComponent.getAbsoluteBounds());
                displayBoundingRect(player.skin.getBoundsInParent());
                displayLocationCrosshairs();
            }
        }
    }

    private void displayBoundingRect(Bounds bounds) {
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.strokeRect(
                bounds.getMinX(),
                bounds.getMinY(),
                bounds.getWidth(),
                bounds.getHeight()
        );
    }

    private void displayLocationCrosshairs() {
        graphicsContext.setStroke(Color.RED);
        graphicsContext.strokeLine(
                0, player.skin.getTranslateY(),
                graphicsContext.getCanvas().getWidth(), player.skin.getTranslateY()
        );
        graphicsContext.strokeLine(
                player.skin.getTranslateX(), 0,
                player.skin.getTranslateX(), graphicsContext.getCanvas().getHeight()
        );
    }

    public void setObjectTracking(boolean objectTracking) {
        this.objectTracking = objectTracking;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        graphicsContext.setStroke(Color.WHITE);
    }

    public void setPlayer(Ball player) {
        this.player = player;
    }

}
