package inventory_app.domain_layer;

/**
 * This class represents a finished product
 */
public class Product extends Item {
    private String SKU;
    private ProductCategory category;

    //TODO delete after fixing classes that use this constructor
    public Product() {
        super();
        SKU = "F0000N";
        category = ProductCategory.FASHION;
    }

    /**
     * Used to make a new product to add to the database
     *
     * @param _name - product name. Used in Item superclass
     * @param _category - product category as an enum
     * @param _SKU - 6-character product identifier
     * @param _weight - product weight. Used in Item superclass
     */
    public Product(String _name, ProductCategory _category, String _SKU, double _weight) {
        super(_name, _weight);
        SKU = _SKU;
        category = _category;
    }

    /**
     * Used to recreate a line from the database easier
     */
    public Product(String _name, ProductCategory _category, String _SKU, double _weight, int _quantity) {
        super(_name, _weight, _quantity);
        SKU = _SKU;
        category = _category;
    }

    public String getSKU() {
        return SKU;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public boolean getRefurbished(){
        if(SKU.charAt(5) == 'R') {
            return true;
        }
        return false;
    }

    //TODO delete after fixing classes that use this setter
    public void setSKU(String _SKU){
        SKU = _SKU;
    }

    //TODO delete after fixing classes that use this setter
    public void setCategory(ProductCategory _category){
        category = _category;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Product)) {
            return false;
        }

        Product p = (Product) o;
        return p.getSKU().equals(this.getSKU());
    }

    public int hashCode() {
        return SKU.hashCode();
    }

    @Override
    public String toString(){
        return "" + SKU + ", " + getName() + ", " + getQuantity() + ", " + getWeight();
    }
}
