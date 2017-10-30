package inventory_app;
import inventory_app.domain_layer.*;

import static spark.Spark.*;

/**
 * Created by Eric Tiano on 10/14/2017.
 */
public class Application{

    public static void main(String[] args){
        InventoryManager manager = InventoryManager.getStaticManager();
        manager.createProduct("Test", ProductCategory.FASHION, "0001", 0.01);
        manager.createProduct("Test2", ProductCategory.FASHION, "0002", 0.01);
        manager.createProduct("Test3", ProductCategory.FASHION, "0003", 0.01);

        manager.createPart("TestPart", PartCategory.CASING, "0001", 0.01);
        manager.addParts("0001", 10);
        manager.createPart("TestPart2", PartCategory.SCREEN, "0002", 0.01);
        manager.createPart("TestPart3", PartCategory.ANTENNA, "0003", 0.01);

        Order order = new Order();
    }
}
