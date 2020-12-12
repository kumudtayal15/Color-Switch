public class SinWaveTrajectory extends TrajectoryComponent {

    public SinWaveTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        double x = size * (speed  * timeInSeconds - delay);
        return new Vector2D(
                // TODO: 12-12-2020 voodoo constant used
                x % (SCENE_WIDTH + 230) , size * Math.sin(x / size)
        );
    }
}
