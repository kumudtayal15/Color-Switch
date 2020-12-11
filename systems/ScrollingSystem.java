import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ScrollingSystem extends BehaviourSystem implements PlayerDeathSubscriber {
    private Ball player;
    protected final List<Node> nodes;
    private final double SCROLL_THRESHOLD;
    private double SCROLL_AMT;

    public ScrollingSystem(EntityManager entityManager, Pane sceneGraphRoot, Bounds rootPaneBounds) {
        super(entityManager, sceneGraphRoot);

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update(l * Math.pow(10, -9));
            }
        };

        try (InputStream input = new FileInputStream("hyperparameters/anim.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            SCROLL_AMT = Double.parseDouble(properties.getProperty("scroll.amount"));
        } catch (IOException e) {
            System.out.println("IO Exception occurred");
            e.printStackTrace();
        }

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
//        if (player.getVelocity() <= 0 && player.skin.getTranslateY() <= SCROLL_THRESHOLD) {
        if (player.getVelocity() <= 0 && player.ballMeshWrapper.getTranslateY() <= SCROLL_THRESHOLD) {
            for (Node node : nodes) {
                node.setTranslateY(node.getTranslateY() + SCROLL_AMT);
            }
        }
    }

    @Override
    public void onPlayerDeath() {
        this.timer.stop();
    }

    public void add(Node e) {
        nodes.add(e);
    }

    public void remove(Node e) {
        nodes.remove(e);
    }

    public void addAll(Node... es) {
        this.nodes.addAll(Arrays.asList(es));
    }

    public void setPlayer(Ball player) {
        this.player = player;
    }
}
