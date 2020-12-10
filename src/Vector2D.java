import javafx.geometry.Point2D;

import java.io.Serializable;

public class Vector2D implements Serializable {
    protected double x;
    protected double y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point2D point2D) {
        this.x = point2D.getX();
        this.y = point2D.getY();
    }

    public Vector2D add (Vector2D that) {
        Vector2D res = new Vector2D();
        res.x = this.x + that.x;
        res.y = this.y + that.y;

        return res;
    }

    public double distance(double x1, double y1) {
        double deltaX = this.x - x1;
        double deltaY = this.y - y1;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public double distance(Point2D that) {
        return distance(that.getX(), that.getY());
    }

    @Override
    public String toString() {
        return "Vector2D [" +
                "x = " + x +
                ", y = " + y +
                ']';
    }
}
