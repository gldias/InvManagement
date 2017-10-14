package inventory_app.domain_layer;

import java.util.HashSet;
import java.util.Set;

public class InventoryManager {
    Set<Part> parts;
    Set<Product> products;
    Set<Order> orders;

    public InventoryManager(){
        parts = new HashSet<>();
        products = new HashSet<>();
        orders = new HashSet<>();
    }

}
