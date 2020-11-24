import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class BoundsDisplay extends Rectangle {
    final Shape boundShape;
    private ChangeListener<Bounds> boundsChangeListener;

    public BoundsDisplay(final Shape shape) {
        setFill(Color.LIGHTGRAY.deriveColor(1, 1, 1, 0.35));
        setStroke(Color.LIGHTGRAY.deriveColor(1, 1, 1, 0.5));
        setStrokeType(StrokeType.INSIDE);
        setStrokeWidth(3);

        boundShape = shape;
    }
}
