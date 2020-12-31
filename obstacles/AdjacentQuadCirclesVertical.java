import javafx.scene.paint.Color;

public class AdjacentQuadCirclesVertical extends CompoundObstacle {

    public AdjacentQuadCirclesVertical(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);
        this.isHollow = false;
    }

    @Override
    public void create(int colorIdx) {
        // TODO: 17-12-2020 dynamic radius calculation
        final double RADIUS = 130 * 0.6;

        QuadArcCircle quadArcCircle0 = new QuadArcCircle(
                new Vector2D(0, -RADIUS),
                entityManager,
                level
        );
        quadArcCircle0.radius = RADIUS;
        quadArcCircle0.width = "thin";
        quadArcCircle0.setColorMapping(this.colorMapping);
        quadArcCircle0.create(colorIdx + 2);
        addChild(quadArcCircle0);

        QuadArcCircle quadArcCircle1 = new QuadArcCircle(
                new Vector2D(0, RADIUS),
                entityManager,
                level
        );
        quadArcCircle1.radius = RADIUS;
        quadArcCircle1.width = "thin";
        quadArcCircle1.setColorMapping(this.colorMapping);
        quadArcCircle1.create(colorIdx);
        addChild(quadArcCircle1);
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return null;
    }
}
