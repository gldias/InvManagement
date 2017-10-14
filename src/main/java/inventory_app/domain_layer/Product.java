package inventory_app.domain_layer;

/**
 * This class represents a finished product
 */
public class Product extends Item {
    private String SKU;
    private ProductCategory category;

    public Product(String SKU, ProductCategory cat) {
        this.SKU = SKU;
        this.category = cat;
    }

    public String getSKU() {
        return SKU;
    }

    public ProductCategory getCategory() {
        return category;
    }
}
