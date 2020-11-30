import javafx.scene.shape.Circle;
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

    public void create() {
        if (Double.isNaN(delayFactor)) {
            throw new NullPointerException("Specify a delay factor");
        }

        for (int i = 0; i < particleCount; i++) {
            PrimitiveObstacle particle = new PrimitiveObstacle(null);
            entityManager.register(particle);

            MeshComponent meshComponent = this.generateParticle();
            meshComponent.mesh.setFill(colorMapping[i / (particleCount / 4)]);
            entityManager.addComponents(
                    particle,
                    meshComponent::insertionCallback,
                    meshComponent
            );
            this.addChild(particle);

            TrajectoryComponent trajectoryComponent = generateTrajectory();
            trajectoryComponent.setDelay(i * delayFactor);
            entityManager.addComponents(
                    particle,
                    trajectoryComponent
            );
        }
    }

    public MeshComponent generateParticle() {
        return new MeshComponent(new Circle(particleRadius));
    }

    abstract public TrajectoryComponent generateTrajectory();

}
