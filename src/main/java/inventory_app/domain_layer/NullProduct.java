package inventory_app.domain_layer;

public class NullProduct extends Product {

    public String getSKU() {
        return null;
    }

    public ProductCategory getCategory() {
        return null;
    }

    public void setSKU(String _SKU){
    }

    public void setCategory(ProductCategory _category){
    }

    public boolean equals(Object o){
        return false;
    }

    public int hashCode(){
        return 0;
    }
}
