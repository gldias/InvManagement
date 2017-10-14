package inventory_app.domain_layer;

/**
 * Object representation of a Pallet
 */
public class Pallet {
    private int quantity;
    private Item item;
    private String id;

    public Pallet(int quantity, Item item, String id) {
        this.quantity = quantity;
        this.item = item;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item getItem() {
        return item;
    }
}
