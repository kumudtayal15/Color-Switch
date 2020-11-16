import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.security.InvalidParameterException;

public class ParticulateCircle extends CompoundObstacle {

    public ParticulateCircle(
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
            Vector2D particleCenter = new Vector2D(0, 0);
            PrimitiveObstacle particle = new PrimitiveObstacle(particleCenter);

            entityManager.register(particle);

            Circle particleMesh = new Circle(
                    particleCenter.x, particleCenter.y,
                    particleRadius, Color.RED);
            container.getChildren().add(particleMesh);
            Color meshColor;
            int ballsPerColor = particleCount / 4;
            if (i < ballsPerColor) {
                meshColor = Color.web("#8C13FB");
            } else if (i < 2 * ballsPerColor) {
                meshColor = Color.web("#F6DF0E");
            } else if (i < 3 * ballsPerColor) {
                meshColor = Color.web("#35E2F2");
            } else {
                meshColor = Color.web("#FF0080");
            }

            MeshComponent meshComponent = new MeshComponent(particleMesh, meshColor);

            entityManager.addComponents(
                    particle,
                    meshComponent::insertionCallback,
                    meshComponent);

            TrajectoryComponent circleTrajectory = new CircleTrajectory(
                    trajectoryRadius, trajectorySpeed, i * 2 * Math.PI / particleCount);
            entityManager.addComponents(
                    particle,
                    circleTrajectory);

        }
    }
}
