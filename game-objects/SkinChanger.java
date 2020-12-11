import javafx.scene.shape.SVGPath;

public class SkinChanger extends SVGCollectible {
    private static final double SCALE = 0.2;

    public SkinChanger(EntityManager entityManager, Vector2D anchorPoint) {
        super(entityManager, anchorPoint);
    }

    @Override
    SVGPath getPath() {
        SVGPath path = new SVGPath();
        path.setContent("M214.25,153.11l-39.59-15.23a47.6,47.6,0,0,0-8-37.84L208,58.66c20.9,12.72,48-2.37,48-27A31.66,31.66,0,0,0,224.38,0c-24.69,0-39.75,27.13-27,48L156,89.39A47.58,47.58,0,0,0,97,91.78L76,73.88C86.46,53,71.18,28.11,47.69,28.11A31.66,31.66,0,0,0,16.06,59.73c0,25.88,29.45,40.61,50.14,25.62l21.06,17.9A47.56,47.56,0,0,0,89.39,156L61.85,183.5C35.8,165.84,0,184.46,0,216.35A39.7,39.7,0,0,0,39.65,256c31.86,0,50.53-35.77,32.85-61.85L100,166.61a47.57,47.57,0,0,0,39.38,7.69l17.11,40.47c-16.19,14.36-6,41.23,15.64,41.23a23.59,23.59,0,1,0-1.77-47.11l-17.12-40.47a48.16,48.16,0,0,0,16-16.49l39.6,15.23a23.59,23.59,0,1,0,47.16,1c0-21.89-27.6-32.09-41.75-15Z");
        return path;
    }

    @Override
    protected double getScale() {
        return SCALE;
    }
}
