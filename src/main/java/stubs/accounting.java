package stubs;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Stub for API calls inventory_app would make to the accounting silo
 */
public class accounting {

    private int funds;
    private static double epsilon = .000001;
    public static accounting singleAccounting = null;

    private accounting(){
        funds = 2000;
    }

    /**
     * Stub for checking funds when making a parts order
     * @param amount - the amount of money being requested
     * @return bool - whether there are enough funds do the order or not
     */
    public boolean checkFunds(double amount){

        boolean success = false;
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://accounting.kennuware.com/api/balance/3");

            ClientResponse response = webResource.type("application/xml")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Output from Server .... \n");
            String output = response.getEntity(String.class);
            System.out.println(output);

            String balance = output.substring(13, output.length()-2);
            double result = Double.parseDouble(balance);

            if (result >= amount) {
                success = true;
            }


        } catch (Exception e) {

            e.printStackTrace();

        }

        return success;
    }

    public static accounting getAccounting(){
        if(singleAccounting == null){
            singleAccounting  = new accounting();
        }

        return singleAccounting;
    }

    public static void resetAccounting(){
        singleAccounting = null;
    }
}
