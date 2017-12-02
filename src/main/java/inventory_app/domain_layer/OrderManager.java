package inventory_app.domain_layer;

import inventory_app.data_mappers.OrderDataMapper;
import inventory_app.domain_layer.validation.ValidationResults;
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

    OrderDataMapper orderMapper;

    private static int idCount = 1;

    private static OrderManager staticManagerO = new OrderManager();

    public OrderManager(){
        orders = new HashSet<>();
        orderMapper = new OrderDataMapper();
    }

    /**
     * Creates and stores a new order with default values.
     *
     * @return created Order
     */
    public ValidationResults createOrder(int id){
        Order o = new Order();

        Order testOrder = getOrder(id);

        if(testOrder != null){
            return new ValidationResults(String.format("Order %s already exists",id));
        }


        o.setId(id);

        orders.add(o);
        orderMapper.insert(o);

        ValidationResults results = new ValidationResults();
        results.setValidatedObject(o);
        return results;
    }

    /**
     * Creates and stores a new order with given products and quantities.
     *
     *  IMPORTANT: Only test methods should use this method. To be replaced.
     *
     * @param destination a string signifying the order's destination
     * @param items a hashmap of Products in the order (key) and their quantities (value)
     * @return created Order
     */
    public ValidationResults createOrder(HashMap<Item,Integer> items, String destination){
        //TODO: there is no error validation yet
        List<Item> ItemList = new ArrayList<>();

        for(Item i: ItemList){

        }

        Order o = new Order(items, destination);

        orders.add(o);
        orderMapper.insert(o);

        ValidationResults results = new ValidationResults();
        results.setValidatedObject(o);
        return results;
    }

    /**
     * Returns the order with the given id.
     *
     * @param id an Order's identifier
     * @return identified order
     */
    public Order getOrder(int id){
        for(Order o : orders){
            if(o.getId() == id){
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
    private ValidationResults updateOrder(Order order, HashMap<Item,Integer> items){
        for(Order o : orders){
            if(order.getId() == o.getId()){
                o.setItems(items);
            }
        }

        //todo
        return new ValidationResults();
    }

    /**
     * Updates the identified order with the given product values.
     *
     * NOTE: Only use this is test methods
     *
     * @param items a hashmap listing the item ids the order is keeping track of
     * @return the updated order
     */
    public ValidationResults updateOrder(int id, HashMap<Item,Integer> items){
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
    private ValidationResults addProductToOrder(Order order, Product product,int quantity){

        if(quantity < 0){
            return new ValidationResults(String.format("Removal quantity %d is negative",quantity));
        }

        if(quantity == 0){
            return new ValidationResults(String.format("Removal quantity %d is 0",quantity));
        }

        if(order.getItems().containsKey(product)){
            order.getItems().put(product, order.getItems().get(product) + quantity);
        }
        else{
            order.getItems().put(product, quantity);
        }

        //todo
        return new ValidationResults();
    }

    /**
     * Adds given quantity of an identified product to an identified order.
     *
     * @param orderId the id of the order to add to
     * @param SKU the product to add a quantity of
     * @param quantity the quantity of the product to add
     * @return the updated order
     */
    public ValidationResults addProductToOrder(int orderId, String SKU, int quantity){
        Order order = getOrder(orderId);

        if(order == null){
            return new ValidationResults(String.format("Order %s is invalid",orderId));
        }

        InventoryManager inventoryManager = InventoryManager.getStaticManager();

        Product product = inventoryManager.getProduct(SKU);

        if(product == null){
            return new ValidationResults(String.format("SKU %s is invalid",SKU));
        }

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
    private ValidationResults removeProductFromOrder(Order order, Product product, int quantity){

        if(quantity < 0){
            return new ValidationResults(String.format("Removal quantity %d is negative",quantity));
        }

        if(quantity == 0){
            return new ValidationResults(String.format("Removal quantity %d is 0",quantity));
        }

        int orderQuantity = order.getItems().get(product);

        if(orderQuantity < quantity){
            return new ValidationResults(String.format("Removal quantity %d is greater than stored quantity %d",quantity, orderQuantity));
        }

        if(orderQuantity <= quantity){
            order.getItems().remove(product);
        }
        else{
            order.getItems().put(product, orderQuantity - quantity);
        }

        //todo
        return new ValidationResults();
    }

    /**
     * Removes given quantity of an identified product from an identified order.
     *
     * @param orderId the order to remove from
     * @param SKU the product to remove a quantity of
     * @param quantity the quantity of the product to remove
     * @return the updated order
     */
    public ValidationResults removeProductFromOrder(int orderId, String SKU, int quantity){
        Order order = getOrder(orderId);

        if(order == null){
            return new ValidationResults(String.format("Order %s is invalid",orderId));
        }

        InventoryManager inventoryManager = InventoryManager.getStaticManager();

        Product product = inventoryManager.getProduct(SKU);

        if(product == null){
            return new ValidationResults(String.format("SKU %s is invalid",SKU));
        }

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
    private ValidationResults addPartToOrder(Order order, Part part,int quantity){

        if(quantity < 0){
            return new ValidationResults(String.format("Removal quantity %d is negative",quantity));
        }

        if(quantity == 0){
            return new ValidationResults(String.format("Removal quantity %d is 0",quantity));
        }

        if(order.getItems().containsKey(part)){
            order.getItems().put(part, order.getItems().get(part) + quantity);
        }
        else{
            order.getItems().put(part, quantity);
        }

        //todo
        return new ValidationResults();
    }

    /**
     * Adds given quantity of an identified part to an identified order.
     *
     * @param orderId the id of the order to add to
     * @param partId the product to add a quantity of
     * @param quantity the quantity of the product to add
     * @return the updated order
     */
    public ValidationResults addPartToOrder(int orderId, String partId, int quantity){
        Order order = getOrder(orderId);

        if(order == null){
            return new ValidationResults(String.format("Order %s is invalid",orderId));
        }

        InventoryManager inventoryManager = InventoryManager.getStaticManager();

        Part part = inventoryManager.getPart(partId);

        if(part == null){
            return new ValidationResults(String.format("Part ID %s is invalid",partId));
        }

        //todo
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
    private ValidationResults removePartFromOrder(Order order, Part part, int quantity){

        if(quantity < 0){
            return new ValidationResults(String.format("Removal quantity %d is negative",quantity));
        }

        if(quantity == 0){
            return new ValidationResults(String.format("Removal quantity %d is 0",quantity));
        }

        int orderQuantity = order.getItems().get(part);

        if(orderQuantity < quantity){
            return new ValidationResults(String.format("Removal quantity %d is greater than stored quantity %d",quantity, orderQuantity));
        }

        if(orderQuantity == quantity){
            order.getItems().remove(part);
        }
        else{
            order.getItems().put(part, orderQuantity - quantity);
        }

        return new ValidationResults();
    }

    /**
     * Removes given quantity of an identified part from an identified order.
     *
     * @param orderId the order to remove from
     * @param partId the part to remove a quantity of
     * @param quantity the quantity of the part to remove
     * @return the updated order
     */
    public ValidationResults removePartFromOrder(int orderId, String partId, int quantity){
        Order order = getOrder(orderId);

        if(order == null){
            return new ValidationResults(String.format("Order %s is invalid",orderId));
        }

        InventoryManager inventoryManager = InventoryManager.getStaticManager();

        Part part = inventoryManager.getPart(partId);

        if(part == null){
            return new ValidationResults(String.format("Part ID %s is invalid",partId));
        }

        return removePartFromOrder(order,part,quantity);
    }

    /**
     * Removes a given order from the system
     *
     * @param order the order to remove
     * @return the removed order
     */
    private ValidationResults removeOrder(Order order){
        boolean orderExisted = this.orders.remove(order);

        if(!orderExisted){
            return new ValidationResults(String.format("Given order does not exist"));
        }

        return new ValidationResults();
    }

    /**
     * Removes a given order from the system
     *
     * @param orderId the id of the order to remove
     * @return the removed order
     */
    public ValidationResults removeOrder(int orderId){
        Order order = getOrder(orderId);

        return removeOrder(order);
    }

    public void confirmOrderWithSales(int order_id){
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
