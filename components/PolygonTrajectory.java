// TODO: 22/11/20 impose check on numOfSides

import java.util.ArrayList;
import java.util.function.Function;

abstract public class PolygonTrajectory extends TrajectoryComponent {
    protected final double sideTraversalTime;
    protected int numOfSides;
    /*
    actual speed along a side, scaled by this factor
    produces the apparent speed along the X/Y axis
     */
    protected double speedScaleFactor;
    protected final ArrayList<Function<Double, Vector2D>> sideTrajectories;

    public PolygonTrajectory(double size, double speed, double delay) {
//        size: length of a side
        super(size, speed, delay);

        sideTraversalTime = size / speed;
        sideTrajectories = new ArrayList<>();
    }

    @Override
    public Vector2D getPositionVector(double timeInSeconds) {
        int trajectoryIdx = (int) ((timeInSeconds - delay) % (numOfSides * sideTraversalTime) / sideTraversalTime);
        return sideTrajectories.get(trajectoryIdx).apply((timeInSeconds - delay) % sideTraversalTime);
    }

}
