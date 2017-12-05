package stubs;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import org.json.JSONObject;

/**
 * A stub for API calls inventory_app would make to the Sales silo
 */

public class sales {

    private static sales singleSales = null;

    private sales(){}

    /**
     * Stub for confirming an order
     * @param id - the order ID
     */
    public void confirmOrder(int id){
    }


    /**
     * Updates an order status when is it being shipped
     * @param id - the order ID
     */
    public void updateOrderStatusShipping(int id){
        try {
            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://sales.kennuware.com/api/salesorder/" + id);

            String input = "{\"status\":\"Shipping\"}";

            ClientResponse response = webResource.type("application/json")
                    .put(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Output from Server .... \n");
            String output = response.getEntity(String.class);
            System.out.println(output);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    /**
     * Updates an order status when it is delivered
     * @param id - the order ID
     */
    public void updateOrderStatusDelivered(int id){
        try {
            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://sales.kennuware.com/api/salesorder/" + id);

            String input = "{\"status\":\"Delivered\"}";

            ClientResponse response = webResource.type("application/json")
                    .put(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Output from Server .... \n");
            String output = response.getEntity(String.class);
            System.out.println(output);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    public static sales getSales(){
        if(singleSales == null){
            singleSales = new sales();
        }

        return singleSales;
    }
}
