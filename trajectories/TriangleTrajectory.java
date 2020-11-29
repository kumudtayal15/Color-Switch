public class TriangleTrajectory extends PolygonTrajectory {

    public TriangleTrajectory(double size, double speed, double delay) {
        super(size, speed, delay);
        this.numOfSides = 3;
        this.speedScaleFactor = 0.5;

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        (speed * speedScaleFactor) * timeInSeconds
                                + size / 2,
                        Math.tan(Math.PI / 3) * ((speed * speedScaleFactor) * timeInSeconds)
                                - size * Math.sin(Math.PI / 3)
                )
        );

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        -speed * timeInSeconds
                                + size,
                        0
                )
        );

        sideTrajectories.add(
                (timeInSeconds) -> new Vector2D(
                        (speed * speedScaleFactor) * timeInSeconds,
                        -(speed * speedScaleFactor) * timeInSeconds * Math.tan(Math.PI / 3)
                )
        );

    }
}
