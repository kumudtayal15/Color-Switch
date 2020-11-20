public class TextureComponent extends Component {
    private final String textureName;

    public TextureComponent(String textureName) {
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }

    public void insertionCallback(EntityManager entityManager, GameObject gameObject) {
//        Dummy callback handle
        System.out.println("Callback handle for TextureComponent!");
    }

}
