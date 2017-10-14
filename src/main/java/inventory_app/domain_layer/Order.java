package inventory_app.domain_layer;

import java.util.List;

/**
 * This class is used to represent Orders made by customers.
 */
public class Order {
    private String id;
    private List<Pallet> pallets;
    private String destination;

    public Order(String id, List<Pallet> pallets, String destination) {
        this.id = id;
        this.pallets = pallets;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Pallet> getPallets() {
        return pallets;
    }

    public void setPallets(List<Pallet> pallets) {
        this.pallets = pallets;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
