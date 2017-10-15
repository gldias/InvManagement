package inventory_app.domain_layer;

/**
 * This class represents a raw part that will be manufactured into a product.
 */
public class Part extends Item {
    private String id;

    public Part(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String _id){
        id = _id;
    }
}
