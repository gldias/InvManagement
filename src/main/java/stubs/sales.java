package stubs;

/**
 * A stub for API calls inventory_app would make to the sales silo
 */

public class sales {
    /**
     * Stub for confirming an order
     * @param id - the order ID
     * @return boolean - whether the order is confirmed or not
     */
    public boolean confirmOrder(String id){
        return true;
    }

    /**
     * Stub for updating sales on the price of a product based on the components
     * @param id - the ID of the product
     * @param price - the base price
     */
    public void getProductBasePrice(String id, double price){}

}
