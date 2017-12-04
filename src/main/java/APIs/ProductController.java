package APIs;

import inventory_app.domain_layer.*;

import inventory_app.domain_layer.validation.ValidationResults;
import org.json.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Path("request")
public class ProductController {

    /**
     * Gets name and SKU for all products
     * @return product information in JSON format
     */
    @GET
    @Path("/products/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        ArrayList<Product> products = InventoryManager.getStaticManager().getProducts();

        if (products == null) {
            ValidationResults validationResults = new ValidationResults("No products available");
            return Response.status(400).entity(validationResults).build();
        }

        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        //HashMap<String, String> result = new HashMap<>();

        //for (Product p : products) {
        //    result.put(p.getName(), p.getSKU());
        //}
        for (int i = 0; i < products.size(); i++) {
            HashMap<String, String> productInfo = new HashMap<>();
            productInfo.put("Name", products.get(i).getName());
            productInfo.put("SKU", products.get(i).getSKU());
            result.add(i, productInfo);
        }

        return Response.status(200).entity(result).build();
    }

    /**
     * Gets product information for a given SKU
     * @param sku - SKU of product to look for
     * @return product information in JSON format
     */
    @GET
    @Path("/products/{sku}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductInfo(@PathParam("sku") String sku) {
        Product product = InventoryManager.getStaticManager().getProduct(sku);

        //If product does not exist (sku was invalid)
        if (product == null) {
            ValidationResults validationResults = new ValidationResults("Product could not be found");
            return Response.status(400).entity(validationResults).build();
        }

        return Response.status(200).entity(product).build();
    }

    /**
     * Notifies Inventory that an order has been fulfilled.
     * More of a product is added to our inventory.
     * @param jsonRequest - contains information about order fulfillment
     * @return product SKU in JSON format
     */
    @POST
    @Path("/orderFulfill/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendOrderFulfillment(String jsonRequest) {
        //Retrieve all of the information passed in via JSON
        JSONObject obj = new JSONObject(jsonRequest);
        String sku = obj.getString("product_sku");
        int quantity = obj.getInt("quantity");
        String flag = obj.getString("flag");

        //Adds more of specified product to our database
        ValidationResults validationResults = InventoryManager.getStaticManager().addProducts(sku, quantity);

        //If product does not exist (sku was invalid)
        if (!validationResults.isSuccess()) {
            return Response.status(400).entity(validationResults).build();
        }

        HashMap<String, String> result = new HashMap<>();
        Product product = InventoryManager.getStaticManager().getProduct(sku);
        result.put("productID", product.getSKU());
        return  Response.status(200).entity(result).build();
    }

    /**
     * Allows Manufacturing to get more parts from Inventory, if we have enough in stock.
     * @param partID - part to retrieve
     * @param quantity - quantity requested
     * @return success message in JSON format
     */
    @GET
    @Path("/parts/{id}/{quantity}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestParts(@PathParam("id") String partID, @PathParam("quantity") int quantity) {
        ValidationResults result = new ValidationResults("Oops! Something went wrong.");

        Part part = InventoryManager.getStaticManager().getPart(partID);
        //If part does not exist (id was invalid)
        if (part == null) {
            ValidationResults validationResults = new ValidationResults("Part could not be found");
            return Response.status(400).entity(validationResults).build();
        }

        int currentQuantity = part.getQuantity();

        //We can only send parts if we have the same amount, or more, in stock
        if (currentQuantity >= quantity) {
            InventoryManager.getStaticManager().removeParts(partID, quantity);
            ValidationResults validationResults = new ValidationResults();
            return Response.status(200).entity(validationResults).build();
        }
        return Response.status(400).entity(result).build();
    }

    /**
     * Creates a new order for a product.
     * @param jsonRequest - information about order
     * @return newly created Order's ID in JSON format
     */
    @POST
    @Path("/newOrder/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newOrder(String jsonRequest) {
        //Retrieve all of the information passed in via JSON
        JSONObject obj = new JSONObject(jsonRequest);
        String sku = obj.getString("product_sku");
        int quantity = obj.getInt("quantity");
        String purpose = obj.getString("purpose");
        String destination = obj.getString("destination");

        //Create HashMap of Product to Quantity
        HashMap<Item, Integer> toOrder = new HashMap<>();
        Product product = InventoryManager.getStaticManager().getProduct(sku);

        //If product does not exist (sku is invalid)
        if (product == null) {
            ValidationResults validationResults = new ValidationResults("Product could not be found");
            return Response.status(400).entity(validationResults).build();
        }

        toOrder.put(product, quantity);

        //Create the Order object and confirm with Sales
        ValidationResults validationResults = OrderManager.getStaticManager().createOrder(toOrder, destination);
        if (!validationResults.isSuccess()) {
            return Response.status(400).entity(validationResults).build();
        }

        Order order = (Order)validationResults.getValidatedObject();
        OrderManager.getStaticManager().confirmOrderWithSales(order.getId());

        return Response.status(200).entity(validationResults).build();
    }
}
