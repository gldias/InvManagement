package inventory_app.domain_layer;

import org.junit.Before;

public class ManagerTest {

    protected InventoryManager manager;

    @Before
    public void setUp(){
        manager = InventoryManager.getTestManager();
        manager.createProduct("Light Up Bracelet", ProductCategory.FASHION,"F0001N",0.2);
        manager.createProduct("Reversible Jacket", ProductCategory.COMFORT,"C0001N",8.1);
        manager.createProduct("Fitness Bracelet", ProductCategory.HEALTHY,"H0001N", 0.3);
        manager.createProduct("Loogle Glass", ProductCategory.ACTIVE,"A0002N",0.2);
        manager.createProduct("Necklace Thing", ProductCategory.BRAINY, "B0001N", 0.9);

        manager.createPart("LCD Screen", PartCategory.SCREEN, "0001", 0.1);
        manager.createPart("Type A Battery", PartCategory.BATTERY, "0002", 0.1);
        manager.createPart("Type B Battery",PartCategory.BATTERY, "0003", 0.2);
    }

}
