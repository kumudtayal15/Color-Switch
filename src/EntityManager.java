import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityManager {
    private int lowestUnassignedEid;
    private final HashMap<ComponentClass, HashMap<GameObject,
            Component>> componentMap;
    private final ArrayList<GameObject> gameObjectList;

    public EntityManager() {
        gameObjectList = new ArrayList<>();
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

    public void addComponents(GameObject gameObject, Component... components) {
        for (Component component : components) {
            componentMap.putIfAbsent(component.getComponentClass(), new HashMap<>());
            componentMap.get(component.getComponentClass()).put(gameObject, component);
        }
    }

    public void addComponents(GameObject gameObject, ComponentCallback callBack, Component... components) {
        /*
        Overloads the addComponents method to support component callbacks
         */

        for (Component component : components) {
            addComponents(gameObject, component);
            callBack.initialize(this, gameObject);
        }
    }

    public Component getComponent(ComponentClass componentClass, GameObject gameObject) {
        return componentMap.get(componentClass).get(gameObject);
    }

    public List<GameObject> getGameObjects(ComponentClass componentClass) {
        ArrayList<GameObject> gameObjectList = new ArrayList<>(0);
        HashMap<GameObject, Component> entityComponentMap = componentMap.get(componentClass);

        if (entityComponentMap != null) {
            gameObjectList.addAll(entityComponentMap.keySet());
        }

        return gameObjectList;
    }

}
