import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrollingSystem extends BehaviourSystem {
    private Ball player;
    private final List<Node> nodes;
    private final double SCROLL_THRESHOLD;
    private static final double SCROLL_AMT = 5;

    public ScrollingSystem(EntityManager entityManager, Bounds rootPaneBounds) {
        super(entityManager);

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update(l * Math.pow(10, -9));
            }
        };

        this.nodes = new ArrayList<>();
        this.SCROLL_THRESHOLD = rootPaneBounds.getHeight() / 2;
    }

    @Override
    public void init() {
        assert player != null;
        timer.start();
    }

    @Override
    public void update(double t) {
        /*
        Condition for scrolling: Ball has an upward velocity, and is
        above the threshold.
         */
        if (player.getVelocity() <= 0 && player.skin.getTranslateY() <= SCROLL_THRESHOLD) {
            for (Node node : nodes) {
                node.setTranslateY(node.getTranslateY() + SCROLL_AMT);
            }
        }
    }

    public void add(Node e) {
        nodes.add(e);
    }

    public void addAll(Node ... es) {
        this.nodes.addAll(Arrays.asList(es));
    }

    public void setPlayer(Ball player) {
        this.player = player;
    }
}
