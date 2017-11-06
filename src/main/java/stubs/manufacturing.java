package stubs;

import APIs.ProductController;

import static org.eclipse.jetty.webapp.Origin.API;

/**
 * Stub for API calls inventory_app will make to the manufacturing silo
 */
public class manufacturing {

    private static manufacturing singleManufacturing = null;

    private int orderSuccessCursor;

    private int sendPartsSuccessCursor;

    private manufacturing(){
        orderSuccessCursor = 0;
        sendPartsSuccessCursor = 0;
    }

    /**
     * Simulates deterministic occasional failure, given a cursor position
     *
     * @param cursor location in the array
     * @return result at cursor location
     */
    private boolean occasionalFailure(int cursor){
        boolean[] successArray = {true, true, false, true, true};

        return successArray[cursor];


    }

    /**
     * Requests an order to manufacturing, whether refurb, internal, or order ID
     * @param productID - the ID of the product
     * @param quantity - quantity to manufacture
     * @param orderType - "refurb" "internal" or an orderID
     * @return boolean representing whether manufacturing started or not
     */
    public boolean orderRequest(String productID, int quantity, String orderType){

        int index = orderSuccessCursor;

        orderSuccessCursor++;

        //loops cursor
        if(orderSuccessCursor > 4){
            orderSuccessCursor = 0;
        }

        boolean success = occasionalFailure(index);

        if(success){
            new ProductController().sendOrderFulfillment(String.format("%s/%d/internal",productID,quantity));
        }

        return success;
    }

    /**
     * Sends parts to manufacturing for use in products
     * @param partID - ID of the part
     * @param quantity - Quantity of the part
     * @return boolean representing whether they received parts or not
     */
    public boolean sendParts(String partID, int quantity){
        // Only fails when manufacturing can not confirm they received parts

        int index = sendPartsSuccessCursor;

        sendPartsSuccessCursor++;

        //loops cursor
        if(sendPartsSuccessCursor > 4){
            sendPartsSuccessCursor = 0;
        }

        return occasionalFailure(index);
    }

    public static manufacturing getManufacturing(){
        if(singleManufacturing == null){
            singleManufacturing = new manufacturing();
        }

        return singleManufacturing;
    }

    public static void resetManufacturing(){
        singleManufacturing = null;
    }
}
