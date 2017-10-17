package inventory_app.domain_layer;

import org.junit.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class InventoryManagerTest {

    InventoryManager manager;

    @Before
    public void setUp(){
        manager = new InventoryManager();
        manager.createProduct("Light Up Bracelet", ProductCategory.FASHION,"F0001N",0.2);
        manager.createProduct("Reversible Jacket", ProductCategory.COMFORT,"C0001N",8.1);
        manager.createProduct("Fitness Bracelet", ProductCategory.HEALTHY,"H0001N", 0.3);
        manager.createProduct("Loogle Glass", ProductCategory.ACTIVE,"A0002N",0.2);
        manager.createProduct("Necklace Thing", ProductCategory.BRAINY, "B0001N", 0.9);

        manager.createPart("LCD Screen", PartCategory.SCREEN, "0001", 0.1);
        manager.createPart("Type A Battery", PartCategory.BATTERY, "0002", 0.1);
        manager.createPart("Type B Battery",PartCategory.BATTERY, "0003", 0.2);

    }

    @Test
    public void testGetProductSuccess() throws Exception {
        Product product = manager.getProduct("B0001N");
        assertEquals("Necklace Thing", product.getName());
    }

    @Test
    public void testGetProductFailure() throws Exception {
        Product product = manager.getProduct("B0012N");
        assertEquals(null, product);
    }

    @Test
    public void testGetProducts() throws Exception {
        Set<String> receivedProductNames = new HashSet<>();
        for(Product p :manager.getProducts()){
            receivedProductNames.add(p.getName());
        }

        Set<String> expectedProductNames = new HashSet<>();

        expectedProductNames.add("Light Up Bracelet");
        expectedProductNames.add("Reversible Jacket");
        expectedProductNames.add("Fitness Bracelet");
        expectedProductNames.add("Loogle Glass");
        expectedProductNames.add("Necklace Thing");

        assertEquals(expectedProductNames,receivedProductNames);
    }

    @Test
    public void testUpdateProductSuccess() throws Exception {
        manager.updateProduct("Reversible Jacket", ProductCategory.COMFORT, "C0001N",10.0);
        Product product = manager.getProduct("C0001N");
        assertEquals("Reversible Jacket", product.getName());
        assertEquals(10.0,product.getWeight(),.1);
    }

    @Test
    public void testUpdateProductFailure() throws Exception {
        //Uses an unused SKU in update
        manager.updateProduct("Reversible Jacket", ProductCategory.COMFORT, "C0001R",10.0);
        Product product = manager.getProduct("C0001R");
        assertEquals(null, product);
    }

    @Test
    public void testDeleteProductSuccess() throws Exception {
        manager.deleteProduct("H0001N");
        Product product = manager.getProduct("H0001N");

        assertEquals(null, product);
    }

    @Test
    public void testDeleteProductFailure() throws Exception {
        //SKU does not exist in system
        manager.deleteProduct("H0123N");
        //ensures nothing got deleted
        int numberOfProducts = manager.getProducts().size();

        assertEquals(5,numberOfProducts);
    }

    @Test
    public void testGetPartSuccess() throws Exception {
        Part part = manager.getPart("0001");
        assertEquals("LCD Screen", part.getName());
    }

    @Test
    public void testGetPartFailure() throws Exception {
        Part part = manager.getPart("0012");
        assertEquals(null, part);
    }

    @Test
    public void testGetParts() throws Exception {
        Set<String> receivedPartNames = new HashSet<>();
        for(Part p :manager.getParts()){
            receivedPartNames.add(p.getName());
        }

        Set<String> expectedPartNames = new HashSet<>();

        expectedPartNames.add("LCD Screen");
        expectedPartNames.add("Type A Battery");
        expectedPartNames.add("Type B Battery");

        assertEquals(expectedPartNames, receivedPartNames);
    }

    @Test
    public void testUpdatePartSuccess() throws Exception {
        manager.updatePart("Type A Battery", PartCategory.BATTERY, "0002", 0.3);
        Part part = manager.getPart("0002");
        assertEquals("Type A Battery", part.getName());
        assertEquals(.3, part.getWeight(),.01);
    }

    @Test
    public void testUpdatePartFailure() throws Exception {
        manager.updatePart("Type A Battery", PartCategory.BATTERY, "0012", 0.3);
        Part part = manager.getPart("0012");
        assertEquals(null, part);
    }

    @Test
    public void deletePartSuccess() throws Exception {
        manager.deletePart("0003");
        Part part = manager.getPart("0003");

        assertEquals(null, part);
    }

    @Test
    public void deletePartFailure() throws Exception {
        manager.deletePart("0004");

        int numberOfParts = manager.getParts().size();

        assertEquals(3, numberOfParts);
    }

    @Test
    public void testAddProducts() throws Exception {
        manager.addProducts("F0001N",20);
        Product product = manager.getProduct("F0001N");

        assertEquals(20,product.getQuantity());
    }

    @Test
    public void testRemoveProducts() throws Exception {
        manager.addProducts("F0001N",20);
        manager.removeProducts("F0001N",5);

        Product product = manager.getProduct("F0001N");

        assertEquals(15,product.getQuantity());
    }

    @Test
    public void testAddParts() throws Exception {
        manager.addParts("0001",20);
        Part part = manager.getPart("0001");

        assertEquals(20,part.getQuantity());
    }

    @Test
    public void testRemoveParts() throws Exception {
        manager.addParts("0001",20);
        manager.removeProducts("0001",5);

        Part part = manager.getPart("0001");

        assertEquals(20,part.getQuantity());
    }

    @After
    public void tearDown(){
        manager.deleteProduct("F0001N");
        manager.deleteProduct("C0001N");
        manager.deleteProduct("H0001N");
        manager.deleteProduct("A0002N");
        manager.deleteProduct("B0001");

        manager.deletePart("0001");
        manager.deletePart("0002");
        manager.deletePart("0003");
    }

}