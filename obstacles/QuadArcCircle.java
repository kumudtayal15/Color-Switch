import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.util.HashMap;

public class QuadArcCircle extends CompoundObstacle {
    private final HashMap<String, String> ARC_WIDTH;
    protected double radius;
    protected double rotationSpeed;
    protected String width;

    public QuadArcCircle(
            Vector2D anchorPoint,
            EntityManager entityManager,
            double radius,
            String width,
            double rotationSpeed) {

        super(anchorPoint, entityManager);

        this.ARC_WIDTH = new HashMap<>(2);
        ARC_WIDTH.put("thin", "M113.36.21,128,0C128.74,69.73,71.85,127,.71,128H.21L0,113.64a4.17,4.17,0,0,0,.5,0C63.56,112.76,114,62,113.36.21Z");
        ARC_WIDTH.put("thick", "M127.75.25A128,128,0,0,1,.25,127.75v-28A100,100,0,0,0,99.74.25Z");
        this.radius = radius;
        this.width = width;
        this.rotationSpeed = rotationSpeed;
    }

    public QuadArcCircle(Vector2D anchorPoint, EntityManager entityManager, Level level) {
        super(anchorPoint, entityManager, level);

        this.ARC_WIDTH = new HashMap<>(2);
        ARC_WIDTH.put("thin", "M113.36.21,128,0C128.74,69.73,71.85,127,.71,128H.21L0,113.64a4.17,4.17,0,0,0,.5,0C63.56,112.76,114,62,113.36.21Z");
        ARC_WIDTH.put("thick", "M127.75.25A128,128,0,0,1,.25,127.75v-28A100,100,0,0,0,99.74.25Z");

        this.radius = 130;
        this.width = "thick";

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
    }

    public void create(int colorIdx) {
        Vector2D circleCenter = new Vector2D(0, 0);
        PrimitiveObstacle[] solidArc = new PrimitiveObstacle[4];

//        Arcs are numbered anticlockwise, from the first quadrant
        MeshComponent meshComponent;
        RotationComponent rotationComponent;
        for (int i = 0; i < 4; i++) {
            solidArc[i] = new PrimitiveObstacle(circleCenter);
            entityManager.register(solidArc[i]);

            Shape arcMesh = getSolidArc(circleCenter, radius, i * 90 - 45);
            meshComponent = new MeshComponent(
                    arcMesh,
                    getMeshColorSynced(i, colorIdx)
            );

            entityManager.addComponents(
                    solidArc[i],
//                    meshComponent::insertionCallback,
                    meshComponent
            );
            solidArc[i].mesh = arcMesh;
            this.addChild(solidArc[i]);

            rotationComponent = new RotationComponent(rotationSpeed, circleCenter.x, circleCenter.y);
            entityManager.addComponents(
                    solidArc[i],
                    rotationComponent
            );
            solidArc[i].mesh.getTransforms().add(rotationComponent.getRotateTransform());
        }
    }

    @Override
    public Color getMeshColorSynced(int i, int colorIdx) {
        Color c;
        try {
            c = colorMapping[(i + (colorIdx + 1)) % 4];
        } catch (NullPointerException e) {
            c = defaultColorMapping[(i + (colorIdx + 1)) % 4];
        }

        return c;
    }

    private Shape getSolidArc(Vector2D circleCenter, double radius, double startAngle) {
        SVGPath svgPath = new SVGPath();
        svgPath.setContent(ARC_WIDTH.get(width));

        final double SCALE_FACTOR = radius / 128;
        svgPath.getTransforms().addAll(
                new Rotate(startAngle, 0, 0),
                new Scale(SCALE_FACTOR, SCALE_FACTOR),
                new Translate(circleCenter.x, circleCenter.y)
        );
        return svgPath;
    }
}
