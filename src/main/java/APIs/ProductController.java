package APIs;

import inventory_app.domain_layer.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static spark.Spark.*;

@Path("request")
public class ProductController {


    @GET
    @Path("/products/{sku}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductInfo(@PathParam("sku") String sku) {
        Product product = InventoryManager.getStaticManager().getProduct(sku);
        return product;
    }

    @GET
    @Path("/orderFulfill/{sku}/{quantity}/{flag}")
    public String sendOrderFulfillment(@PathParam("sku") String sku, @PathParam("quantity") int quantity, @PathParam("flag") String flag) {
        //TODO: validation (like do we have the product already?)
        InventoryManager.getStaticManager().addProducts(sku, quantity);

        String name = InventoryManager.getStaticManager().getProduct(sku).getName();
        return "<User>" + "<Name>" + sku + " " + name + " added" + "</Name>" + "</User>";
    }

    @GET
    @Path("/parts/{id}/{quantity}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean requestMaterials(@PathParam("id") String partID, @PathParam("quantity") int quantity) {
        //TODO: EDGE CASES NOT FUNCTIONING WELL
        boolean confirmed = false;
        int currentQuantity = InventoryManager.getStaticManager().getPart(partID).getQuantity();

        if (currentQuantity > quantity) {
            InventoryManager.getStaticManager().removeParts(partID, quantity);
            confirmed = true;
            return confirmed;
        }
        return confirmed;
    }

    @GET
    @Path("/newOrder/{prodID}/{orderID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newOrder(@PathParam("prodID") String sku, @PathParam("orderID") String orderID) {
        //TODO: Create Order through OrderManager when it is complete. Also, refine parameters in Order creation
        Order order = new Order();
        String result = "Order saved: " + order.getId();
        return Response.status(201).entity(result).build();
    }
}
