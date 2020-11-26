import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class Particle extends Circle {
    protected static final Color[] colorMapping = {
            Color.web("#8C13FB"),
            Color.web("#F6DF0E"),
            Color.web("#35E2F2"),
            Color.web("#FF0080")};

    static Random random = new Random();
    protected double MAX_PARTICLE_RADIUS;
    protected double dx;
    protected double dy;
    protected double GRAVITY;
    private final Bounds bounds;

    public Particle(Bounds bounds) {
        double vx = 0, vy = 0;

        try (InputStream input = new FileInputStream("hyperparameters/particle.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            this.MAX_PARTICLE_RADIUS = Double.parseDouble(properties.getProperty("radius"));
            this.GRAVITY = Double.parseDouble(properties.getProperty("gravity"));
            vx = Double.parseDouble(properties.getProperty("x.velocity")) * random.nextDouble();
            vy = Double.parseDouble(properties.getProperty("y.velocity")) * random.nextDouble();
        } catch (IOException e) {
            System.out.println("IO Exception occurred");
            e.printStackTrace();
        }

        this.bounds = bounds;

        double theta = random.nextFloat() * (2 * Math.PI);
        this.dx = Math.cos(theta) * vx;
        this.dy = Math.sin(theta) * vy;

        this.setRadius(MAX_PARTICLE_RADIUS * Math.max(random.nextFloat(), 0.4));
        this.setFill(colorMapping[random.nextInt(4)]);
    }

    public void doStep() {
        this.setTranslateX(this.getTranslateX() + dx);
        this.setTranslateY(this.getTranslateY() + dy);
        dy += GRAVITY;

        final boolean atRightBorder = this.getTranslateX() >= (bounds.getMaxX() - this.getRadius());
        final boolean atLeftBorder = this.getTranslateX() <= (bounds.getMinX() + this.getRadius());
//        final boolean atBottomBorder = this.getTranslateY() >= (bounds.getMaxY() - this.getRadius());
        final boolean atTopBorder = this.getTranslateY() <= (bounds.getMinY() + this.getRadius());

        if (atLeftBorder || atRightBorder) {
            dx = -dx;
        }

        if (atTopBorder) {
            dy = -dy;
        }
    }
}
