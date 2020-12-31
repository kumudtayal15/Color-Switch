import javafx.scene.paint.Color;

public class NestedParticleCircles extends CompoundObstacle {

    private static final double BUFFER = 55;

    public NestedParticleCircles(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);
    }

    @Override
    public void create(int colorIdx) {
        Level level_neg = Level.valueOf(level.toString() + "_NEG");

        /*
        Circles are numbered starting from the smallest to the largest
         */
        ParticulateCircle particulateCircle0 = new ParticulateCircle(
                new Vector2D(),
                entityManager,
                level_neg
        );
        particulateCircle0.setColorMapping(this.colorMapping);
        particulateCircle0.trajectorySize *= 0.75;
        particulateCircle0.particleRadius *= 0.85;
        particulateCircle0.create(colorIdx);
        addChild(particulateCircle0);

        ParticulateCircle particulateCircle1 = new ParticulateCircle(
                new Vector2D(),
                entityManager,
                level
        );
        particulateCircle1.setColorMapping(this.colorMapping);
        particulateCircle1.trajectorySize = particulateCircle0.trajectorySize + BUFFER;
        particulateCircle1.particleCount = particulateCircle0.particleCount;
        particulateCircle1.particleRadius = particulateCircle0.particleRadius * 1.5;
        particulateCircle1.create(colorIdx);
        addChild(particulateCircle1);
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return null;
    }
}
