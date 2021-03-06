package inventory_app.domain_layer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to represent Orders.
 */
public class Order {
    private static int id = 0;
    private int ownId;
    private Map<Item, Integer> items = new HashMap<>();
    private String destination;

    public Order(HashMap<Item, Integer> items, String destination) {
        ownId = ++id;
        this.items = items;
        this.destination = destination;
    }

    public Order(){
        ownId = ++id;
        this.items = new HashMap<>();
        this.destination = "ND";
    }

    public Order(int id){
        ownId = id;
        this.items = new HashMap<>();
        this.destination = "ND";
    }

    public int getId() {
        return ownId;
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

    public void setId(int id) {
        ownId = id;
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

    public void addItems(Item item, Integer quantity){
        if(items.containsKey(item)){
            getItems().put(item, items.get(item) + quantity);
        }
        else{
            getItems().put(item, quantity);
        }
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
        return order.getId() == this.getId();
    }

    //TODO: Is the method below necessary?
    /*
    public int hashCode(){
        return id.hashCode();
    }*/
}
