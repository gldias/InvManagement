package inventory_app.domain_layer;

import java.util.List;

/**
 * This class is used to represent Orders made by customers.
 */
public class Order {
    private String id;
    private String destination;

    public Order(String id, String destination) {
        this.id = id;
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
}
