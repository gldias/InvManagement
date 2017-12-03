package inventory_app;
import inventory_app.domain_layer.*;

import static spark.Spark.*;

/**
 * Created by Eric Tiano on 10/14/2017.
 */
public class Application{

    public static void main(String[] args){
        InventoryManager manager = InventoryManager.getStaticManager();

        Order order = new Order();
    }
}
