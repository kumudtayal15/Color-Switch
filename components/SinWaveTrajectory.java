public class SinWaveTrajectory extends TrajectoryComponent {

    public SinWaveTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
                (size * (speed * timeInSeconds - delay)) % SCENE_WIDTH,
                size * Math.sin(speed * timeInSeconds - delay)
        );
    }
}

