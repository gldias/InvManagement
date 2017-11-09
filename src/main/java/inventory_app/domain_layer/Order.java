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

    public Order(){
        this.id = "0";
        this.items = new HashMap<>();
        this.destination = "ND";
    }

    public String getId() {
        return id;
    }

    public int getQuantity(String id){

        Part testPart = new Part(id,PartCategory.SCREEN,"name",0.0);

        if(items.get(testPart) != null)
            return items.get(testPart);

        Product testProduct = new Product("name",ProductCategory.FASHION,id,0.0);

        if(items.get(testProduct) != null){
            return items.get(testProduct);
        }

        return 0;
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
     * NOTE: currently not functional (no use for this yet)
     * @return totalWeight
     */
    public double getTotalWeight() {
        double totalWeight = 0;
        return totalWeight;
    }

    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(!(o instanceof Order)){
            return false;
        }

        Order order = (Order) o;
        return order.getId().equals(this.getId());
    }

    public int hashCode(){
        return id.hashCode();
    }
}
