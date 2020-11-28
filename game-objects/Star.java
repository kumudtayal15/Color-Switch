import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Translate;

public class Star extends GameObject {
//    static final double SCALE = 0.13;
    static final double SCALE = 0.15;
    protected final Group container;

    public Star(EntityManager entityManager, Vector2D anchorPoint) {
        this.container = new Group();
        SVGPath starPath = new SVGPath();
        starPath.setContent("M83.92,78.17,115.51,9.29a12.25,12.25,0,0,1,22.17-.22l32.94,68.15L244,87.58a12.25,12.25,0,0,1,7.13,20.61l-52.87,55.15,13.25,76.18a12.25,12.25,0,0,1-17.87,12.89l-65-34.9L64.4,253.81a12.25,12.25,0,0,1-18.14-12.52L58,164.87l-54-54a12.25,12.25,0,0,1,6.69-20.75Z");
        starPath.setFill(Color.WHITE);

        container.setScaleX(SCALE);
        container.setScaleY(SCALE);
        container.getTransforms().add(new Translate(-128 / SCALE, -128 / SCALE));
        container.getChildren().add(starPath);
        container.setTranslateX(anchorPoint.x);
        container.setTranslateY(anchorPoint.y);

        entityManager.register(this);
    }

    public Group getContainer() {
        return container;
    }
}
