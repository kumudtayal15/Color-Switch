import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Cartwheel extends CompoundObstacle {

    protected double length;
    protected double rotationSpeed;

    // TODO: 02-12-2020 length is a redundant parameter
    public Cartwheel(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double length,
            double rotationSpeed) {

        super(anchorPoint, entityManager);

        this.entityManager = entityManager;
        this.length = length;
        this.rotationSpeed = rotationSpeed;
    }

    public Cartwheel(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);
        this.length = 100;
        switch (level) {
            case EASY:
                this.rotationSpeed = 100;
                break;
            case MEDIUM:
                this.rotationSpeed = 150;
                break;
            case HARD:
                this.rotationSpeed = 200;
                break;
        }
    }

    public void create(int colorIdx) {
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
                    getMeshColorSynced(i, colorIdx)
//                    colorMapping[(i + 1) % 4]
            );

            entityManager.addComponents(
                    wheelArm[i],
//                    meshComponent::insertionCallback,
                    meshComponent
            );

            wheelArm[i].mesh = wheelArmMesh;

            /*
            addChild method not used, since the mesh is wrapped in a Group
             */
            this.children.add(wheelArm[i]);
            container.getChildren().add(armMeshContainer);

            rotationComponent = new RotationComponent(rotationSpeed, wheelCenter.x, wheelCenter.y);
            entityManager.addComponents(
                    wheelArm[i],
                    rotationComponent
            );
            armMeshContainer.getTransforms().add(rotationComponent.getRotateTransform());
        }
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        return colorMapping[(i + (colorIdx + 2)) % 4];
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
