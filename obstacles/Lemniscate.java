/*
The only particulate obstacle which doesn't inherit from ParticulateObstacle,
primarily due to complexity of trajectory
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.security.InvalidParameterException;

public class Lemniscate extends CompoundObstacle {
    public Lemniscate(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double trajectoryRadius,
            double trajectorySpeed,
            int particleCount,
            double particleRadius) {

        super(anchorPoint);
        container.setLayoutX(anchorPoint.x);
        container.setLayoutY(anchorPoint.y);

        if (particleCount % 4 != 0) {
            throw new InvalidParameterException("Number of particles should be divisible by 4");
        }

        for (int i = 0; i < particleCount; i++) {
            PrimitiveObstacle lParticle = new PrimitiveObstacle(null);
            PrimitiveObstacle rParticle = new PrimitiveObstacle(null);

            Circle lParticleMesh = new Circle(particleRadius);
            Circle rParticleMesh = new Circle(particleRadius);

            Color meshColor = colorMapping[i / (particleCount / 4)];
            MeshComponent lMeshComponent = new MeshComponent(lParticleMesh, meshColor);
            MeshComponent rMeshComponent = new MeshComponent(rParticleMesh, meshColor);

            entityManager.addComponents(
                    lParticle,
//                    lMeshComponent::insertionCallback,
                    lMeshComponent
            );
            lParticle.mesh = lParticleMesh;

            entityManager.addComponents(
                    rParticle,
//                    rMeshComponent::insertionCallback,
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
}
