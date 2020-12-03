/*
The only particulate obstacle which doesn't inherit from ParticulateObstacle,
primarily due to complexity of trajectory
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.security.InvalidParameterException;

public class Lemniscate extends CompoundObstacle {
    protected double trajectoryRadius;
    protected double trajectorySpeed;
    protected int particleCount;
    protected double particleRadius;

    public Lemniscate(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectoryRadius,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint, entityManager);

        if (particleCount % 4 != 0) {
            throw new InvalidParameterException("Number of particles should be divisible by 4");
        }

        this.entityManager = entityManager;
        this.trajectoryRadius = trajectoryRadius;
        this.trajectorySpeed = trajectorySpeed;
        this.particleCount = particleCount;
        this.particleRadius = particleRadius;
    }

    public Lemniscate(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);
        this.trajectoryRadius = 100;
        this.particleCount = 12;
        this.particleRadius = 18;

        switch (level) {
            case EASY:
                trajectorySpeed = 1;
                break;
            case MEDIUM:
                trajectorySpeed = 1.3;
                break;
            case HARD:
                trajectorySpeed = 1.8;
                break;
        }
    }

    public void create(int colorIdx) {
        for (int i = 0; i < particleCount; i++) {
            PrimitiveObstacle lParticle = new PrimitiveObstacle(null);
            PrimitiveObstacle rParticle = new PrimitiveObstacle(null);

            entityManager.register(lParticle);
            entityManager.register(rParticle);

            Circle lParticleMesh = new Circle(particleRadius);
            Circle rParticleMesh = new Circle(particleRadius);
            Color meshColor = getMeshColorSynced(i / (particleCount / 4), colorIdx);
            MeshComponent lMeshComponent = new MeshComponent(lParticleMesh, meshColor);
            MeshComponent rMeshComponent = new MeshComponent(rParticleMesh, meshColor);

            entityManager.addComponents(
                    lParticle,
                    lMeshComponent
            );
            lParticle.mesh = lParticleMesh;

            entityManager.addComponents(
                    rParticle,
                    rMeshComponent
            );
            rParticle.mesh = rParticleMesh;

            this.addChild(lParticle);
            this.addChild(rParticle);

            entityManager.addComponents(
                    lParticle, new LemniscateLTrajectory(
                            trajectoryRadius,
                            trajectorySpeed,
                            i * (Math.PI / particleCount)
                    )
            );
            entityManager.addComponents(
                    rParticle, new LemniscateRTrajectory(
                            trajectoryRadius,
                            trajectorySpeed,
                            i * (Math.PI / particleCount)
                    )
            );
        }
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[i % 4];
    }
}
