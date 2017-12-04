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

        classes.add(ProductController.class);
        return classes;
    }
}
