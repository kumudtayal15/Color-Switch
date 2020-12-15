import javafx.scene.shape.SVGPath;

public class Immunity extends SVGCollectible {
    private static final double SCALE = 0.2;

    public Immunity(EntityManager entityManager, Vector2D anchorPoint) {
        super(entityManager, anchorPoint);
    }

    @Override
    SVGPath getPath() {
        SVGPath path = new SVGPath();
        path.setContent("M182.56,111.77a7.5,7.5,0,0,0-6.51-3.79H140.5V50.89a7.5,7.5,0,0,0-14-3.82l-53,89.63A7.5,7.5,0,0,0,80,148H115.5V205.1a7.5,7.5,0,0,0,13.95,3.83L182.5,119.3A7.48,7.48,0,0,0,182.56,111.77ZM34.49,120.5H7.5a7.5,7.5,0,0,0,0,15h27a7.5,7.5,0,1,0,0-15Zm214,0h-27a7.5,7.5,0,0,0,0,15h27a7.5,7.5,0,0,0,0-15ZM85,202.6a7.49,7.49,0,0,0-10.24,2.75l-13.5,23.4a7.5,7.5,0,1,0,13,7.49l13.49-23.4A7.49,7.49,0,0,0,85,202.6ZM192,17a7.51,7.51,0,0,0-10.25,2.75L168.26,43.16a7.5,7.5,0,1,0,13,7.49l13.5-23.4A7.51,7.51,0,0,0,192,17Zm2.75,211.75-13.5-23.4a7.5,7.5,0,1,0-13,7.49l13.49,23.4a7.5,7.5,0,1,0,13-7.49ZM87.74,43.16,74.25,19.75a7.5,7.5,0,1,0-13,7.5l13.5,23.4a7.5,7.5,0,0,0,13-7.49Z");
        return path;
    }

    @Override
    protected double getScale() {
        return SCALE;
    }
}
