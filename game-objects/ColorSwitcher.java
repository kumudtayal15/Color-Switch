import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

import java.io.File;

public class ColorSwitcher extends GameObject {
    static final String imageLocation = "assets/colorswitcher.png";
    static final Image image = new Image(new File(imageLocation).toURI().toString());
    static final double rotationSpeed = 100;
    static final double radius = 50;
    protected final ImageView imageView;
    protected final Group container;
    protected Color color;

    public ColorSwitcher(EntityManager entityManager, Vector2D anchorPoint) {
        container = new Group();
        imageView = new ImageView(image);
        imageView.setFitWidth(radius);
        imageView.setFitHeight(radius);
        imageView.getTransforms().add(new Translate(-radius / 2, -radius / 2));
        container.getChildren().add(imageView);
        container.setTranslateX(anchorPoint.x);
        container.setTranslateY(anchorPoint.y);

        entityManager.register(this);
        RotationComponent rotationComponent = new RotationComponent(rotationSpeed, 0, 0);
        entityManager.addComponents(this, rotationComponent);
        container.getTransforms().add(rotationComponent.getRotateTransform());
    }

    public static double getRotationSpeed() {
        return rotationSpeed;
    }

    public static double getRadius() {
        return radius;
    }

    public Group getContainer() {
        return container;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
