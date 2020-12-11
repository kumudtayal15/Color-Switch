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
        path.setContent("M96.13,4A128.76,128.76,0,0,0,4,96.13l14.53,3.74A113.72,113.72,0,0,1,99.87,18.54ZM237.46,99.87,252,96.13A128.76,128.76,0,0,0,159.87,4l-3.74,14.53A113.72,113.72,0,0,1,237.46,99.87ZM159.87,252A128.72,128.72,0,0,0,252,159.87l-14.53-3.74a113.68,113.68,0,0,1-81.33,81.32ZM18.54,156.13,4,159.87A128.72,128.72,0,0,0,96.13,252l3.74-14.53A113.68,113.68,0,0,1,18.54,156.13ZM128,105.5A22.5,22.5,0,1,0,150.5,128,22.52,22.52,0,0,0,128,105.5Zm7.5,30h-15v-15h15ZM128,0,99.19,99.19,0,128l99.19,28.8L128,256l28.81-99.2L256,128,156.81,99.19Zm0,165.5A37.5,37.5,0,1,1,165.5,128,37.54,37.54,0,0,1,128,165.5Zm56.55-73.83,20.22-40.44L164.33,71.45,168.9,87.1ZM168.9,168.9l-4.57,15.65,40.44,20.22-20.22-40.44Zm-97.45-4.57L51.23,204.77l40.44-20.22L87.1,168.9ZM87.1,87.1l4.57-15.65L51.23,51.23,71.45,91.67Z");
        return path;
    }

    @Override
    protected double getScale() {
        return SCALE;
    }
}
