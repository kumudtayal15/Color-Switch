public abstract class Component {

}

class TextureComponent extends Component {
    private final String textureName;

    public TextureComponent(String textureName) {
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }
}
