import javafx.scene.paint.Color;

public class CartwheelPair extends CompoundObstacle {

    public CartwheelPair(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);
    }

    @Override
    public void create(int colorIdx) {
        Cartwheel cartwheelL = new Cartwheel(
                new Vector2D(-100, 0),
                entityManager,
                level
        );
        cartwheelL.create(colorIdx);
        addChild(cartwheelL);

        Level level_neg = Level.valueOf(level.toString() + "_NEG");
        Cartwheel cartwheelR = new Cartwheel(
                new Vector2D(100, 0),
                entityManager,
                level_neg
        );
        cartwheelR.create(colorIdx);
        addChild(cartwheelR);
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return null;
    }
}
