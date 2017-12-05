package inventory_app;
import inventory_app.domain_layer.*;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Eric Tiano on 10/14/2017.
 */
public class Application{

    public static void main(String[] args){
        /*boolean success = false;
        try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://accounting.kennuware.com/api/balance/3");

            String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";

            ClientResponse response = webResource.type("application/xml")
                    .post(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Output from Server .... \n");
            String output = response.getEntity(String.class);
            System.out.println(output);

            /*Retrieve response and parses XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();


        } catch (Exception e) {

            e.printStackTrace();

        }*/

        try {
            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://inventory.kennuware.com/inventory/request/newOrder");

            String input = "{\"product_sku\":\"A0002N\",\"quantity\": 4,\"purpose\":\"business\",\"destination\":\"nanya\"}";

            ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, input);

            if (response.getStatus() != 200 && response.getStatus() != 400) {
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
}
