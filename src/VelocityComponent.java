public class VelocityComponent extends Component {
    protected final Vector2D velocity;

    public VelocityComponent(double xComponent, double yComponent) {
        velocity = new Vector2D(xComponent, yComponent);
        setComponentClass(ComponentClass.valueOf("VELOCITY"));
    }
}
