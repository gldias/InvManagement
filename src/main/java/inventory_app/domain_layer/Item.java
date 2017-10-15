package inventory_app.domain_layer;

/**
 * This abstract class represents an item, which can either be a product or part.
 */
public abstract class Item {
    private double weight;
    private String name;
    private int quantity;
}
