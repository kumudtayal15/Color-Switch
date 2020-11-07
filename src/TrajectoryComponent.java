// TODO: 08/11/20 600 is a VOODOO constant! Replace with SCREEN_WIDTH

public class TrajectoryComponent extends Component {
    protected final double size;
    protected final double speed;
    protected final double delay;

    public TrajectoryComponent(double size, double speed, double delay) {
        this.size = size;
        this.speed = speed;
        this.delay = delay;
    }

    public Vector2D getPositionVector(double timeInSeconds) {
//        stub implementation
        return new Vector2D();
    }
}

class SinWaveTrajectory extends TrajectoryComponent {

    public SinWaveTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
                (size * (speed * timeInSeconds - delay)) % 600,
                size * Math.sin(speed * timeInSeconds - delay)
        );
    }
}

class StraightLineTrajectory extends TrajectoryComponent {

    public StraightLineTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        return new Vector2D(
//                (speed * timeInSeconds - delay) % 600,
//                20 * Math.sin(speed * timeInSeconds - delay),
//                size
        );
    }
}

class CircleTrajectory extends TrajectoryComponent {

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
