package inventory_app.domain_layer;

/**
 * This class represents a finished product
 */
public class Product extends Item {
    private String SKU;
    private ProductCategory category;

    //todo eventually we may wish to make every identifier on a default construction unique
    public Product(){
        super();
        SKU = "000";
        category = ProductCategory.FASHION;
    }

    public Product(String _name, ProductCategory _category, String _SKU, double _weight) {
        super(_name,_weight);
        SKU = _SKU;
        category = _category;
    }

    public String getSKU() {
        return SKU;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setSKU(String _SKU){
        SKU = _SKU;
    }

    public void setCategory(ProductCategory _category){
        category = _category;
    }

    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(!(o instanceof Product)){
            return false;
        }

        Product p = (Product) o;
        return p.getSKU().equals(this.getSKU());
    }

    public int hashCode(){
        return SKU.hashCode();
    }

}
