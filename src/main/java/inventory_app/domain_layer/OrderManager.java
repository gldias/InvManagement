package inventory_app.domain_layer;

import inventory_app.data_mappers.OrderDM;
import inventory_app.data_mappers.mapperInterface;
import stubs.manufacturing;
import stubs.sales;

import java.util.*;

/**
 * Responsible for managing Order objects and their data mapper
 *
 * All order related CRUD operations are done through this class
 */
public class OrderManager {
    Set<Order> orders;

    mapperInterface orderMapper;

    private static int idCount = 1;

    private static OrderManager staticManagerO = new OrderManager();

    public OrderManager(){
        orders = new HashSet<>();
        orderMapper = new OrderDM();
    }

    /**
     * Creates and stores a new order with default values.
     *
     * @return created Order
     */
    public Order createOrder(String id){
        Order o = new Order();
        o.setId(id);

        orders.add(o);
        orderMapper.insert(o);

        return o;
    }

    /**
     * Creates and stores a new order with given products and quantities.
     *
     * @param destination a string signifying the order's destination
     * @param items a hashmap of Products in the order (key) and their quantities (value)
     * @return created Order
     */
    public Order createOrder(HashMap<Item,Integer> items, String destination){
        Order o = new Order("" + idCount, items, destination);
        idCount++;

        orders.add(o);
        orderMapper.insert(o);

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
     * Updates the identified order with the given product values.
     *
     * @param items a hashmap listing the item ids the order is keeping track of
     * @return the updated order
     */
    public Order updateOrder(String id, HashMap<Item,Integer> items){
        Order order = getOrder(id);

        return updateOrder(order,items);
    }

    /**
     * Adds given quantity of a given product to a given order.
     *
     * @param order the order to add to
     * @param product the product to add a quantity of
     * @param quantity the quantity of the product to add
     * @return the updated order
     */
    public Order addProductToOrder(Order order, Product product,int quantity){
        if(order.getItems().containsKey(product)){
            order.getItems().put(product, order.getItems().get(product) + quantity);
        }
        else{
            order.getItems().put(product, quantity);
        }
        return order;
    }

    /**
     * Adds given quantity of an identified product to an identified order.
     *
     * @param orderId the id of the order to add to
     * @param SKU the product to add a quantity of
     * @param quantity the quantity of the product to add
     * @return the updated order
     */
    public Order addProductToOrder(String orderId, String SKU, int quantity){
        Order order = getOrder(orderId);

        InventoryManager inventoryManager = InventoryManager.getStaticManager();

        Product product = inventoryManager.getProduct(SKU);

        return addProductToOrder(order, product, quantity);
    }

    /**
     * Removes given quantity of a given product from a given order.
     *
     * @param order the order to remove from
     * @param product the product to remove a quantity of
     * @param quantity the quantity of the product to remove
     * @return the updated order
     */
    public Order removeProductFromOrder(Order order, Product product, int quantity){
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
     * Removes given quantity of an identified product from an identified order.
     *
     * @param orderId the order to remove from
     * @param SKU the product to remove a quantity of
     * @param quantity the quantity of the product to remove
     * @return the updated order
     */
    public Order removeProductFromOrder(String orderId, String SKU, int quantity){
        Order order = getOrder(orderId);

        InventoryManager inventoryManager = InventoryManager.getStaticManager();

        Product product = inventoryManager.getProduct(SKU);

        return removeProductFromOrder(order,product,quantity);
    }

    /**
     * Adds given quantity of a given part to a given order.
     *
     * @param order the order to add to
     * @param part the part to add a quantity of
     * @param quantity the quantity of the part to add
     * @return the updated order
     */
    public Order addPartToOrder(Order order, Part part,int quantity){
        if(order.getItems().containsKey(part)){
            order.getItems().put(part, order.getItems().get(part) + quantity);
        }
        else{
            order.getItems().put(part, quantity);
        }
        return order;
    }

    /**
     * Adds given quantity of an identified part to an identified order.
     *
     * @param orderId the id of the order to add to
     * @param partId the product to add a quantity of
     * @param quantity the quantity of the product to add
     * @return the updated order
     */
    public Order addPartToOrder(String orderId, String partId, int quantity){
        Order order = getOrder(orderId);

        InventoryManager inventoryManager = InventoryManager.getStaticManager();

        Part part = inventoryManager.getPart(partId);

        return addPartToOrder(order, part, quantity);
    }

    /**
     * Removes given quantity of a given part from a given order.
     *
     * @param order the order to remove from
     * @param part the part to remove a quantity of
     * @param quantity the quantity of the part to remove
     * @return the updated order
     */
    public Order removePartFromOrder(Order order, Part part, int quantity){
        int orderQuantity = order.getItems().get(part);
        if(orderQuantity < quantity){
            order.getItems().remove(part);
        }
        else{
            order.getItems().put(part, orderQuantity - quantity);
        }
        return order;
    }

    /**
     * Removes given quantity of an identified part from an identified order.
     *
     * @param orderId the order to remove from
     * @param partId the part to remove a quantity of
     * @param quantity the quantity of the part to remove
     * @return the updated order
     */
    public Order removePartFromOrder(String orderId, String partId, int quantity){
        Order order = getOrder(orderId);

        InventoryManager inventoryManager = InventoryManager.getStaticManager();

        Part part = inventoryManager.getPart(partId);

        return removePartFromOrder(order,part,quantity);
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

    /**
     * Removes a given order from the system
     *
     * @param orderId the id of the order to remove
     * @return the removed order
     */
    public Order removeOrder(String orderId){
        Order order = getOrder(orderId);

        return removeOrder(order);
    }

    public void confirmOrderWithSales(String order_id){
        //confirm order?
        sales.getSales().confirmOrder(order_id);
    }

    public boolean orderProductsFromManufacturing(String sku, int quantity, String orderType){
        return manufacturing.getManufacturing().orderRequest(sku,quantity,orderType);
    }

    public boolean sendPartsToManufacturing(String part_id, int quantity){
        boolean success =  manufacturing.getManufacturing().sendParts(part_id,quantity);

        if(success){
            InventoryManager.getStaticManager().removeParts(part_id,quantity);
        }

        return success;
    }

    public static OrderManager getStaticManager(){
        return staticManagerO;
    }
}
