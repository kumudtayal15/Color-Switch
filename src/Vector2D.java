public class Vector2D {
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

    public Vector2D add (Vector2D that) {
        Vector2D res = new Vector2D();
        res.x = this.x + that.x;
        res.y = this.y + that.y;

        return res;
    }
}
