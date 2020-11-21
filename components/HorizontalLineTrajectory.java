public class HorizontalLineTrajectory extends TrajectoryComponent {

    public HorizontalLineTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
                (speed * timeInSeconds - delay) % SCENE_WIDTH, 0
//                20 * Math.sin(speed * timeInSeconds - delay)
        );
    }
}

