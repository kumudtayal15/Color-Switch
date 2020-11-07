import java.util.ArrayList;
import java.util.HashMap;

public class EntityManager {
    private int lowestUnassignedEid;
    private final HashMap<String, HashMap<GameObject,
            Component>> componentMap;
    private final ArrayList<GameObject> gameObjectList;

    public EntityManager() {
        gameObjectList = new ArrayList<GameObject>();
        componentMap = new HashMap<>();
        lowestUnassignedEid = 1;
    }

    private int generateNewEid() {
        return lowestUnassignedEid++;
    }

    public void register(GameObject gameObject) {
        gameObject.setGameObjectID(generateNewEid());
        gameObjectList.add(gameObject);
    }

    public void registerAll(GameObject... gameObjects) {
        for (GameObject g : gameObjects) {
            g.setGameObjectID(generateNewEid());
            gameObjectList.add(g);
        }
    }

    public void addComponent(Component component, GameObject gameObject) {
        String componentName = component.getClass().toString();
        componentMap.putIfAbsent(componentName, new HashMap<>());
        componentMap.get(componentName).put(gameObject, component);
    }

    public Component getComponentByClass(Class<?> componentClass, GameObject gameObject) {
        return componentMap.get(componentClass.toString()).get(gameObject);
    }

    public static void main(String[] args) {
        EntityManager entityManager = new EntityManager();

        GameObject ball1 = new GameObject();
        GameObject ball2 = new GameObject();

        entityManager.registerAll(ball1, ball2);
        entityManager.addComponent(new TextureComponent("Carbon"), ball1);

        System.out.println(ball1.getID());
        System.out.println(ball2.getID());

        TextureComponent tex = (TextureComponent) entityManager.getComponentByClass(
                TextureComponent.class,
                ball1);
        System.out.println(tex.getTextureName());
    }
}
