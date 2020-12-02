import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.security.InvalidParameterException;

abstract public class ParticulateObstacle extends CompoundObstacle {
    protected final Vector2D anchorPoint;
    protected final EntityManager entityManager;
    protected final double trajectorySize;
    protected final double trajectorySpeed;
    protected final int particleCount;
    protected final double particleRadius;
    protected double delayFactor;

    public ParticulateObstacle(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectorySize,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint);

        if (particleCount % 4 != 0) {
            throw new InvalidParameterException("Number of particles should be divisible by 4");
        }

        this.anchorPoint = anchorPoint;
        this.entityManager = entityManager;
        this.trajectorySize = trajectorySize;
        this.trajectorySpeed = trajectorySpeed;
        this.particleCount = particleCount;
        this.particleRadius = particleRadius;

        this.delayFactor = Double.NaN;
    }

    public void create(int colorIdx) {
        if (Double.isNaN(delayFactor)) {
            throw new NullPointerException("Specify a delay factor");
        }

        for (int i = 0; i < particleCount; i++) {
            PrimitiveObstacle particle = new PrimitiveObstacle(null);
            entityManager.register(particle);

            Shape particleMesh = this.generateParticle();
            MeshComponent meshComponent = new MeshComponent(
                    particleMesh,
                    getMeshColorSynced(i, colorIdx)
            );
            entityManager.addComponents(
                    particle,
                    meshComponent
            );
            particle.mesh = particleMesh;
            this.addChild(particle);

            TrajectoryComponent trajectoryComponent = generateTrajectory();
            trajectoryComponent.setDelay(i * delayFactor);
            entityManager.addComponents(
                    particle,
                    trajectoryComponent
            );
        }
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[i / (particleCount / 4)];
    }

    public Shape generateParticle() {
        return new Circle(particleRadius);
    }

    abstract public TrajectoryComponent generateTrajectory();
}
