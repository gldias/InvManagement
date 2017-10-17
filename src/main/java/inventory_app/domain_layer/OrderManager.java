package inventory_app.domain_layer;

import java.util.*;

/**
 * Responsible for managing Order objects and their data mapper
 *
 * All order related CRUD operations are done through this class
 */
public class OrderManager {
    Set<Order> orders;

    private static int idCount = 1;

    private static OrderManager staticManagerO = new OrderManager();

    public OrderManager(){
        orders = new HashSet<>();
    }

    /**
     * Creates and stores a new order with default values.
     *
     * @return created Order
     */
    public Order CreateOrder(){
        Order o = new Order();

        orders.add(o);

        return o;
    }

    /**
     * Creates and stores a new order with given products and quantities.
     *
     * @param destination a string signifying the order's destination
     * @param items a hashmap of Products in the order (key) and their quantities (value)
     * @return created Order
     */
    public Order CreateOrder(HashMap<Item,Integer> items, String destination){
        Order o = new Order("" + idCount, items, destination);
        idCount++;

        orders.add(o);

        return o;
        //autogen id?
    }

    /**
     * Returns the order with the given id.
     *
     * @param id an Order's identifier
     * @return identified order
     */
    public Order getOrder(String id){
        for(Order o : orders){
            if(o.getId().equals(id)){
                return o;
            }
        }

        return null;
    }

    /**
     * Returns all the orders.
     * @return a collection of orders
     */
    public Collection<Order> getOrders(){
        return this.orders;
    }

    /**
     * Updates a given order with the given product values.
     *
     * @param items a hashmap listing the items the order is keeping track of
     * @return the updated order
     */
    public Order updateOrder(Order order, HashMap<Item,Integer> items){
        for(Order o : orders){
            if(order.getId().equals(o.getId())){
                o.setItems(items);
            }
        }

        return null;
    }

    /**
     * Adds given quantity of a given product to a given order.
     *
     * @param order the order to add to
     * @param product the product to add a quantity of
     * @param quantity the quantity of the product to add
     * @return the updated order
     */
    public Order addToOrder(Order order, Product product,int quantity){
        if(order.getItems().containsKey(product)){
            order.getItems().put(product, order.getItems().get(product) + quantity);
        }
        else{
            order.getItems().put(product, quantity);
        }
        return order;
    }

    /**
     * Removes given quantity of a given product from a given order.
     *
     * @param order the order to remove from
     * @param product the product to remove a quantity of
     * @param quantity the quantity of the product to remove
     * @return the updated order
     */
    public Order removeFromOrder(Order order, Product product, int quantity){
        int orderQuantity = order.getItems().get(product);
        if(orderQuantity < quantity){
            order.getItems().remove(product);
        }
        else{
            order.getItems().put(product, orderQuantity - quantity);
        }
        return order;
    }

    /**
     * Removes a given order from the system
     *
     * @param order the order to remove
     * @return the removed order
     */
    public Order removeOrder(Order order){
        this.orders.remove(order);
        return order;
    }

    public static OrderManager getStaticManager(){
        return staticManagerO;
    }
}
