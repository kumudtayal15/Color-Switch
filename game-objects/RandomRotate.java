import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class RandomRotate extends SVGCollectible {
    private static final double SCALE = 0.2;

    public RandomRotate(EntityManager entityManager, Vector2D anchorPoint) {
        super(entityManager, anchorPoint);
    }

    @Override
    SVGPath getPath() {
        SVGPath path = new SVGPath();
        path.setContent("M240.49,188a7.3,7.3,0,0,0-7.4-7.5H210.52a7.68,7.68,0,1,0,0,15.35h7.07A112.88,112.88,0,0,1,128,240.65C66.06,240.65,15.36,190,15.36,128a113.36,113.36,0,0,1,3.83-28.09A7.68,7.68,0,1,0,4.32,96.08,128.84,128.84,0,0,0,0,128c0,70.41,57.59,128,128,128,37.8,0,73.05-17.4,97.09-45.75v.38a7.68,7.68,0,0,0,15.36,0l0-21.56A6.24,6.24,0,0,0,240.49,188ZM128,0C90.2,0,54.88,17.4,30.83,45.75v-.27a7.68,7.68,0,0,0-15.35,0v22.6A7.36,7.36,0,0,0,24,75.43H45.56c4.18.59,7.67-3.43,7.67-7.68a7.66,7.66,0,0,0-7.67-7.67H38.41A112.85,112.85,0,0,1,128,15.35C190,15.35,240.65,66.05,240.65,128a113.37,113.37,0,0,1-3.84,28.09,7.68,7.68,0,1,0,14.87,3.83A128.37,128.37,0,0,0,256,128C256,57.59,198.41,0,128,0Zm3.49,53.84a7.55,7.55,0,0,0-7,0L68.23,83.39,128,119.25l59.77-35.86Zm4,78.41v67.42L192,164.36a7.49,7.49,0,0,0,3.52-6.35V96.24Zm-75-36V158A7.49,7.49,0,0,0,64,164.36l56.49,35.31V132.25Z");
        return path;
    }

    @Override
    protected double getScale() {
        return SCALE;
    }

    @Override
    protected Color getColor() {
        return Color.WHITE;
    }
}
