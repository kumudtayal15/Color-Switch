import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

public class ParticulateSinWave extends ParticulateObstacle {
    public ParticulateSinWave(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectoryAmplitude,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager, trajectoryAmplitude, trajectorySpeed, particleCount, particleRadius);
        this.delayFactor = 2 * Math.PI /  particleCount;
    }

    public ParticulateSinWave(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.trajectorySize = 150;
        this.particleCount = 20;
        this.particleRadius = 20;
        this.delayFactor = 2 * Math.PI /  particleCount;

        switch (level) {
            case EASY:
                this.trajectorySpeed = 1.5;
                break;
            case EASY_NEG:
                break;
            case MEDIUM:
                break;
            case HARD:
                break;
        }

        this.container.getTransforms().add(new Translate(-generateTrajectory().SCENE_WIDTH / 2, 0));
    }

    @Override
    public TrajectoryComponent generateTrajectory() {
        return new SinWaveTrajectory(trajectorySize, trajectorySpeed, 0);
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[i % 4];
    }

    @Override
    public Vector2D getCentre() {
        Vector2D v = super.getCentre();
        v.x += generateTrajectory().SCENE_WIDTH / 2;

        return v;
    }
}
