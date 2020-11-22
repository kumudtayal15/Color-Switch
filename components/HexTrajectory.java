public class HexTrajectory extends PolygonTrajectory {

    public HexTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
        this.numOfSides = 6;
        this.speedScaleFactor = 0.5;

        final double SIXTY = Math.PI / 3;

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        speed * timeInSeconds,
                        0
                )
        );

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        (speed * speedScaleFactor) * timeInSeconds + size,
                        -(speed * speedScaleFactor) * timeInSeconds * Math.tan(SIXTY)
                )
        );

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        -(speed * speedScaleFactor) * timeInSeconds + 1.5 * size,
                        -(speed * speedScaleFactor) * timeInSeconds * Math.tan(SIXTY) - size * Math.sin(SIXTY)
                )
        );

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        -speed * timeInSeconds + size,
                        -2 * size * Math.sin(SIXTY)
                )
        );

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        -(speed * speedScaleFactor) * timeInSeconds,
                        (speed * speedScaleFactor) * timeInSeconds * Math.tan(SIXTY) - 2 * size * Math.sin(SIXTY)
                )
        );

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        (speed * speedScaleFactor) * timeInSeconds - size / 2,
                        (speed * speedScaleFactor) * timeInSeconds * Math.tan(SIXTY) - size * Math.sin(SIXTY)
                )
        );
    }
}
