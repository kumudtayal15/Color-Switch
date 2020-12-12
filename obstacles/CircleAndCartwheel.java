import javafx.scene.paint.Color;

public class CircleAndCartwheel extends CompoundObstacle {

    public CircleAndCartwheel(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);
    }

    @Override
    public void create(int colorIdx) {
        Level level_neg = Level.valueOf(level.toString() + "_NEG");
        Cartwheel cartwheel = new Cartwheel(
                new Vector2D(-50, 0),
                entityManager,
                level_neg
        );
        cartwheel.setColorMapping(this.colorMapping);
        cartwheel.create(colorIdx);
        addChild(cartwheel);

        QuadArcCircle circle = new QuadArcCircle(
                new Vector2D(),
                entityManager,
                level
        );
        circle.setColorMapping(this.colorMapping);
        circle.create(colorIdx);
        addChild(circle);
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return null;
    }
}
