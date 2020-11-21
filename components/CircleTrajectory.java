public class CircleTrajectory extends TrajectoryComponent {

    public CircleTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
                size * Math.cos(speed * timeInSeconds - delay),
                size * Math.sin(speed * timeInSeconds - delay)
        );
    }
}

