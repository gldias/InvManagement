package APIs;

import inventory_app.domain_layer.*;

import org.json.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;

@Path("request")
public class ProductController {

    //TODO: Error Responses

    @GET
    @Path("/products/{sku}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductInfo(@PathParam("sku") String sku) {
        Product product = InventoryManager.getStaticManager().getProduct(sku);
        return product;
    }

    @POST
    @Path("/orderFulfill/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendOrderFulfillment(String jsonRequest) {
        JSONObject obj = new JSONObject(jsonRequest);
        String sku = obj.getString("product_sku");
        int quantity = obj.getInt("quantity");
        String flag = obj.getString("flag");

        Product product = InventoryManager.getStaticManager().addProducts(sku, quantity);

        HashMap<String, String> result = new HashMap<>();
        result.put("productID", product.getSKU());
        return  Response.status(200).entity(result).build();
    }

    @GET
    @Path("/parts/{id}/{quantity}")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, Boolean> requestMaterials(@PathParam("id") String partID, @PathParam("quantity") int quantity) {
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("confirmed", false);
        int currentQuantity = InventoryManager.getStaticManager().getPart(partID).getQuantity();

        if (currentQuantity >= quantity) {
            InventoryManager.getStaticManager().removeParts(partID, quantity);
            result.put("confirmed", true);
            return result;
        }
        return result;
    }

    @POST
    @Path("/newOrder/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newOrder(String jsonRequest) {
        JSONObject obj = new JSONObject(jsonRequest);
        String sku = obj.getString("product_sku");
        int quantity = obj.getInt("quantity");
        String purpose = obj.getString("purpose");
        String destination = obj.getString("destination");

        //Create HashMap of Product to Quantity
        HashMap<Item, Integer> toOrder = new HashMap<>();
        Product product = InventoryManager.getStaticManager().getProduct(sku);
        toOrder.put(product, quantity);

        Order order = OrderManager.getStaticManager().createOrder(toOrder, destination);

        HashMap<String, String> result = new HashMap<>();
        result.put("orderID", order.getId());
        return Response.status(200).entity(result).build();
    }
}
