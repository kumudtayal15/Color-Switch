import javafx.scene.Group;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Cartwheel extends CompoundObstacle {

    protected EntityManager entityManager;
    protected double length;
    protected double rotationSpeed;

    public Cartwheel(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double length,
            double rotationSpeed) {

        super(anchorPoint);
        container.setLayoutX(anchorPoint.x);
        container.setLayoutY(anchorPoint.y);

        this.entityManager = entityManager;
        this.length = length;
        this.rotationSpeed = rotationSpeed;
    }

    public void create() {
        Vector2D wheelCenter = new Vector2D(0, 0);
        PrimitiveObstacle[] wheelArm = new PrimitiveObstacle[4];

        MeshComponent meshComponent;
        RotationComponent rotationComponent;

        for (int i = 0; i < 4; i++) {
            wheelArm[i] = new PrimitiveObstacle(wheelCenter);
            entityManager.register(wheelArm[i]);

            Group armMeshContainer = getArmContainer(wheelCenter, length, i * 90);
            Shape wheelArmMesh = (SVGPath) armMeshContainer.getChildren().get(0);

            meshComponent = new MeshComponent(
                    wheelArmMesh,
                    colorMapping[(i + 1) % 4]
            );

            entityManager.addComponents(
                    wheelArm[i],
                    meshComponent::insertionCallback,
                    meshComponent
            );
            container.getChildren().add(armMeshContainer);

            rotationComponent = new RotationComponent(rotationSpeed, wheelCenter.x, wheelCenter.y);
            entityManager.addComponents(
                    wheelArm[i],
                    rotationComponent
            );
            armMeshContainer.getTransforms().add(rotationComponent.getRotateTransform());

        }
    }

    private Group getArmContainer(Vector2D wheelCenter, double length, double orientation) {
        Group container = new Group();

        SVGPath svgPath = new SVGPath();
        svgPath.setContent("M28,14V86A14,14,0,0,1,0,86V14a14.6,14.6,0,0,1,.14-2,.78.78,0,0,0,0-.14L14,0,27.84,11.86a.78.78,0,0,0,0,.14A14.6,14.6,0,0,1,28,14Z");
        /*
        Arm is 28 pixels wide, so an offset of -14 translates it to
        the container's origin
         */
        svgPath.getTransforms().add(new Translate(-14, 0));

        container.getTransforms().add(new Rotate(orientation, 0, 0));
        container.getChildren().add(svgPath);
        return container;
    }
}
