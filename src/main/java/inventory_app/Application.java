package inventory_app;
import inventory_app.domain_layer.*;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Created by Eric Tiano on 10/14/2017.
 */
public class Application{

    public static void main(String[] args){
       /* try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://manufacturing.kennuware.com:8080/ReceiveMaterials?rid=C0001N&q=20");

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

        } catch (Exception e) {

            e.printStackTrace();

        }*/
    }
}
