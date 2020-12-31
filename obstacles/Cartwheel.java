import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.HashMap;

public class Cartwheel extends CompoundObstacle {

    private final HashMap<String, String> ARM_SIZE;
    protected double rotationSpeed;
    protected String armSize;

    // TODO: 02-12-2020 length is a redundant parameter
    public Cartwheel(
            Vector2D anchorPoint,
            EntityManager entityManager,
            String armSize,
            double rotationSpeed) {

        super(anchorPoint, entityManager);

        this.ARM_SIZE = new HashMap<>(2);
        ARM_SIZE.put("small", "M14,7.2V53.52A6.5,6.5,0,0,1,7.52,60h-1A6.5,6.5,0,0,1,0,53.52V7.2L7,0Z");
        ARM_SIZE.put("large", "M28,14V86A14,14,0,0,1,0,86V14a14.6,14.6,0,0,1,.14-2,.78.78,0,0,0,0-.14L14,0,27.84,11.86a.78.78,0,0,0,0,.14A14.6,14.6,0,0,1,28,14Z");

        this.armSize = "large";

        this.armSize = armSize;
        this.rotationSpeed = rotationSpeed;
        this.isHollow = false;
    }

    public Cartwheel(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.ARM_SIZE = new HashMap<>(2);
        ARM_SIZE.put("small", "M14,7.2V53.52A6.5,6.5,0,0,1,7.52,60h-1A6.5,6.5,0,0,1,0,53.52V7.2L7,0Z");
        ARM_SIZE.put("large", "M28,14V86A14,14,0,0,1,0,86V14a14.6,14.6,0,0,1,.14-2,.78.78,0,0,0,0-.14L14,0,27.84,11.86a.78.78,0,0,0,0,.14A14.6,14.6,0,0,1,28,14Z");

        this.armSize = "large";

        switch (level) {
            case EASY:
                this.rotationSpeed = 100;
                break;
            case EASY_NEG:
                this.rotationSpeed = -100;
                break;
            case MEDIUM:
                this.rotationSpeed = 150;
                break;
            case HARD:
                this.rotationSpeed = 200;
                break;
        }
        this.isHollow = false;
    }

    public void create(int colorIdx) {
        Vector2D wheelCentre = new Vector2D(0, 0);
        PrimitiveObstacle[] wheelArm = new PrimitiveObstacle[4];

        MeshComponent meshComponent;
        RotationComponent rotationComponent;

        for (int i = 0; i < 4; i++) {
            wheelArm[i] = new PrimitiveObstacle(wheelCentre);
            entityManager.register(wheelArm[i]);

            Group armMeshContainer = getArmContainer(wheelCentre,i * 90);
            Shape wheelArmMesh = (SVGPath) armMeshContainer.getChildren().get(0);

            meshComponent = new MeshComponent(
                    wheelArmMesh,
                    getMeshColorSynced(i, colorIdx)
            );

            entityManager.addComponents(
                    wheelArm[i],
                    meshComponent
            );

            wheelArm[i].mesh = wheelArmMesh;

            /*
            addChild method not used, since the mesh is wrapped in a Group
             */
            this.children.add(wheelArm[i]);
            container.getChildren().add(armMeshContainer);

            rotationComponent = new RotationComponent(rotationSpeed, wheelCentre.x, wheelCentre.y);
            entityManager.addComponents(
                    wheelArm[i],
                    rotationComponent
            );
            armMeshContainer.getTransforms().add(rotationComponent.getRotateTransform());
        }
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        Color c;
        try {
            c = colorMapping[(i + (colorIdx + 2)) % 4];
        } catch (NullPointerException e) {
            c = defaultColorMapping[(i + (colorIdx + 2)) % 4];
        }

        return c;
    }

    private Group getArmContainer(Vector2D wheelCenter, double orientation) {
        Group container = new Group();

        SVGPath svgPath = new SVGPath();
        svgPath.setContent(ARM_SIZE.get(armSize));
//        svgPath.setContent("M28,14V86A14,14,0,0,1,0,86V14a14.6,14.6,0,0,1,.14-2,.78.78,0,0,0,0-.14L14,0,27.84,11.86a.78.78,0,0,0,0,.14A14.6,14.6,0,0,1,28,14Z");
        /*
        Arm is 28/14 pixels wide, so an offset of -14/-7 translates it to
        the container's origin
         */
        final double offset = (this.armSize.equals("large")) ? -14 : -7;
        svgPath.getTransforms().add(new Translate(offset, 0));

        container.getTransforms().add(new Rotate(orientation, 0, 0));
        container.getChildren().add(svgPath);
        return container;
    }
}
