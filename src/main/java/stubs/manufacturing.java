package stubs;

import APIs.ProductController;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONObject;

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
            new ProductController().sendOrderFulfillment(String.format("{ " +
                    "\"product_sku\": \"%s\", \"quantity\": %d, \"flag\": \"internal\"}",productID,quantity));
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
        boolean success = false;
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://manufacturing.kennuware.com:8080/ReceiveMaterials?rid=" + partID +
                            "&q=" + quantity);

            String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";

            ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Output from Server .... \n");
            String output = response.getEntity(String.class);
            System.out.println(output);

            //Retrieve response boolean
            JSONObject obj = new JSONObject(output);
            success = obj.getBoolean("confirm");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return success;
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
