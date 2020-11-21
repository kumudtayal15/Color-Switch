public class SquareTrajectory extends PolygonTrajectory{

    public SquareTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
        this.numOfSides = 4;

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        size,
                        -speed * (timeInSeconds)
                )
        );
        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        -speed * (timeInSeconds) + size,
                        -size
                )
        );
        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        0,
                        speed * (timeInSeconds) - size
                )
        );
        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        speed * (timeInSeconds),
                        0
                )
        );
    }
}