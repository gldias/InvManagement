package stubs;

/**
 * Stub for API calls inventory_app will make to the manufacturing silo
 */
public class manufacturing {
    /**
     * Requests an order to manufacturing, whether refurb, internal, or order ID
     * @param productID - the ID of the product
     * @param quantity - quantity to manufacture
     * @param orderType - "refurb" "internal" or an orderID
     * @return boolean representing whether manufacturing started or not
     */
    public boolean orderRequest(String productID, int quantity, String orderType){
        // Would only fail in cases where manufacturing could not receive the request
        return true;
    }

    /**
     * Sends parts to manufacturing for use in products
     * @param partID - ID of the part
     * @param quantity - Quantity of the part
     * @return boolean representing whether they received parts or not
     */
    public boolean sendParts(String partID, int quantity){
        // Only fails when manufacturing can not confirm they received parts
        // for sake of simulation manufacturing has 100% uptime
        return true;
    }
}
