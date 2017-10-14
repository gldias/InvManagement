package inventory_app.domain_layer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to represent Orders.
 */
public class Order {
    private String id;
    private Map<Item, Integer> items = new HashMap<>();
    private String destination;

    public Order(String id, HashMap<Item, Integer> items, String destination) {
        this.id = id;
        this.items = items;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    /**
     * Calculates the total weight of the items in the order
     * NOTE: Functionality is currently stubbed out
     * @return totalWeight
     */
    public double getTotalWeight() {
        double totalWeight = 0;
        return totalWeight;
    }
}
