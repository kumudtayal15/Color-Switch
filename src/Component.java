public abstract class Component {
    private ComponentClass componentClass;

    public void setComponentClass(ComponentClass componentClass) {
        this.componentClass = componentClass;
    }

    public ComponentClass getComponentClass() {
        return componentClass;
    }
}