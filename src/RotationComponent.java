import javafx.scene.transform.Rotate;

public class RotationComponent extends Component {
    private double angularVelocity;
    private Vector2D pivot;
    private final Rotate rotateTransform;

    public RotationComponent(double angularVelocity, double x, double y) {
        this.angularVelocity = angularVelocity;
        pivot = new Vector2D(x, y);
        rotateTransform = new Rotate(0, x, y);

        setComponentClass(ComponentClass.valueOf("ROTATION"));
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public Vector2D getPivot() {
        return pivot;
    }

    public Rotate getRotateTransform() {
        return rotateTransform;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void setPivot(double x, double y) {
        this.pivot = new Vector2D(x, y);
        this.rotateTransform.setPivotX(x);
        this.rotateTransform.setPivotY(y);
    }

    public void rotate(double timeInSeconds) {
        rotateTransform.setAngle(angularVelocity * timeInSeconds);
    }

    public void insertionCallback(EntityManager entityManager, GameObject gameObject) {

    }

    public void deletionCallback(EntityManager entityManager, GameObject gameObject) {

    }
}
