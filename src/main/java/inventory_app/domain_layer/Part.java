package inventory_app.domain_layer;

/**
 * This class represents a raw part that will be manufactured into a product.
 */
public class Part extends Item {
    private String id;
    private PartCategory category;
    private int price;

    //TODO delete after fixing classes that use this constructor
    public Part() {
        id = "0000";
        category = PartCategory.CASING;
    }

    /**
     * Used to make a new product to add to the database
     *
     * @param _id - 4-character part identifier
     * @param _name - part name. Used in Item superclass
     * @param _price - part purchase price
     * @param _weight - part weight. Used in Item superclass
     */
    public Part(String _id, String _name, /*PartCategory _category,*/ int _price, double _weight) {
        super(_name, _weight);
        id = _id;
        //category = _category;
        price = _price;
    }

    //TODO delete after fixing classes that use this constructor
    public Part(String _id, PartCategory _category, String _name, double _weight) {
        super(_name, _weight);
        id = _id;
        category = _category;
        price = 0;
    }

    /**
     * Used to recreate a line from the database easier
     */
    public Part(String _id, String _name, int _quantity, /*PartCategory _category,*/ int _price, double _weight) {
        super(_name, _weight, _quantity);
        id = _id;
        //category = _category;
        price = _price;
    }

    public String getId() {
        return id;
    }

    public PartCategory getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    //TODO delete after fixing classes that use this setter
    public void setId(String _id){
        id = _id;
    }

    //TODO delete after fixing classes that use this setter
    public void setCategory(PartCategory _category){
        category = _category;
    }

    //TODO delete after fixing classes that use this setter
    public void setprice(int _price){
        price = _price;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Part)) {
            return false;
        }

        Part p = (Part) o;
        return p.getId().equals(this.getId());
    }

    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString(){
        return "" + id + ", " + getName() + ", " + getQuantity() + ", " + price + ", " + getWeight();
    }
}
