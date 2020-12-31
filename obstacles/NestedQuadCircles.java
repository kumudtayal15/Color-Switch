import javafx.scene.paint.Color;

public class NestedQuadCircles extends CompoundObstacle {
    private static final double BUFFER = 50;

    public NestedQuadCircles(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);
    }

    @Override
    public void create(int colorIdx) {
        Level level_neg = Level.valueOf(level.toString() + "_NEG");

        /*
        Circles are numbered starting from the smallest to the largest
         */
        QuadArcCircle quadArcCircle0 = new QuadArcCircle(
                new Vector2D(),
                entityManager,
                level_neg
        );
        quadArcCircle0.setColorMapping(this.colorMapping);
        quadArcCircle0.create(colorIdx);
        addChild(quadArcCircle0);

        QuadArcCircle quadArcCircle1 = new QuadArcCircle(
                new Vector2D(),
                entityManager,
                level
        );
        quadArcCircle1.setColorMapping(this.colorMapping);
        quadArcCircle1.radius = quadArcCircle0.radius + BUFFER;
        quadArcCircle1.create(colorIdx);
        addChild(quadArcCircle1);
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return null;
    }
}
