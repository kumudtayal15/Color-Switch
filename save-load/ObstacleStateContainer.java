import java.io.Serializable;

public class ObstacleStateContainer implements Serializable {
    String className;
    Vector2D position;
    Level level;

    public ObstacleStateContainer(String className, Vector2D position, Level level) {
        this.className = className;
        this.position = position;
        this.level = level;
    }

    @Override
    public String toString() {
        return "ObstacleStateContainer{" +
                "className='" + className + '\'' +
                ", position=" + position +
                ", level=" + level +
                '}';
    }
}
