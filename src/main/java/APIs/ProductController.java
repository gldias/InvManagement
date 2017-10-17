package APIs;

import inventory_app.domain_layer.InventoryManager;
import inventory_app.domain_layer.OrderManager;
import inventory_app.domain_layer.ProductCategory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import static spark.Spark.*;

@Path("request")
public class ProductController {


    @GET
    @Path("/products/{sku}")
    public String getProductInfo(@PathParam("sku") String sku) {
        String name = InventoryManager.getStaticManager().getProduct(sku).getName();
        return "<User>" + "<Name>" + sku + " " + name + "</Name>" + "</User>";
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
    public String requestMaterials(@PathParam("id") String partID, @PathParam("quantity") int quantity) {
        //TODO: EDGE CASES NOT FUNCTIONING WELL
        int currentQuantity = InventoryManager.getStaticManager().getPart(partID).getQuantity();
        String name = InventoryManager.getStaticManager().getPart(partID).getName();
        if (currentQuantity > quantity) {
            InventoryManager.getStaticManager().removeParts(partID, quantity);
            return "<User>" + "<Name>" + partID + " " + name + " removed" + " " + quantity + " times" + "</Name>" + "</User>";
        }
        return "<User>" + "<Name>" + "Not enough " + partID + " " + name + "</Name>" + "</User>";
    }

    @GET
    @Path("/newOrder/{prodID}/{orderID}")
    public String newOrder(@PathParam("prodID") String sku, @PathParam("orderID") String orderID) {

        return "<User>" + "<Name>" + sku + "</Name>" + "</User>";
    }
}
