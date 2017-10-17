package APIs;

import inventory_app.domain_layer.InventoryManager;
import inventory_app.domain_layer.OrderManager;
import inventory_app.domain_layer.PartCategory;
import inventory_app.domain_layer.ProductCategory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/inventory")
public class MyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();

        //TODO: THIS IS ONLY FOR TESTING PURPOSES!! DELETE AS SOON AS DATABASE IS WORKING
        InventoryManager.getStaticManager().createProduct("Test", ProductCategory.FASHION, "0001", 0.01);
        InventoryManager.getStaticManager().createProduct("Test2", ProductCategory.FASHION, "0002", 0.01);
        InventoryManager.getStaticManager().createProduct("Test3", ProductCategory.FASHION, "0003", 0.01);

        InventoryManager.getStaticManager().createPart("TestPart", PartCategory.CASING, "0001", 0.01);
        InventoryManager.getStaticManager().addParts("0001", 10);
        InventoryManager.getStaticManager().createPart("TestPart2", PartCategory.SCREEN, "0002", 0.01);
        InventoryManager.getStaticManager().createPart("TestPart3", PartCategory.ANTENNA, "0003", 0.01);

        OrderManager.getStaticManager().createOrder("01");
        OrderManager.getStaticManager().addPartToOrder("01","0001",200);
        OrderManager.getStaticManager().addPartToOrder("01","0002",150);


        OrderManager.getStaticManager().createOrder("02");
        OrderManager.getStaticManager().addProductToOrder("02","0001",20);
        OrderManager.getStaticManager().addProductToOrder("02","0002",3);

        classes.add(ProductController.class);
        return classes;
    }
}
