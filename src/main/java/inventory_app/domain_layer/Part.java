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

    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(!(o instanceof Part)){
            return false;
        }

        Part p = (Part) o;
        return p.getId().equals(this.getId());
    }

    public int hashCode(){
        return id.hashCode();
    }
}
