package APIs;

import inventory_app.domain_layer.InventoryManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import static spark.Spark.*;

@Path("products")
public class ProductController {

    @GET
    @Path("/info/{i}")
    public String productInfo(@PathParam("i") String i) {
        String info = i;
        return "<User>" + "<Name>" + info + "</Name>" + "</User>";
    }
}
