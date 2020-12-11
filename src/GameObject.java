import javafx.scene.Group;
import javafx.scene.Node;

abstract public class GameObject {

    private int ID;

    public int getID() {
        return ID;
    }

    public void setGameObjectID(int ID) {
        this.ID = ID;
    }
}
