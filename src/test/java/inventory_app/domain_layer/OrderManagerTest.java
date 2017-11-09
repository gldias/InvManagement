package inventory_app.domain_layer;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

public class OrderManagerTest extends ManagerTest{

    protected OrderManager orders;

    @Before
    public void setup() throws Exception {
        orders = new OrderManager();
        System.out.println("setup complete");
    }

    @Test
    public void createEmptyOrder() throws Exception {
        orders.createOrder("001");
        assertTrue(orders.getOrder("001").getItems().isEmpty());
    }

    private Part createPartOrder(){
        HashMap<Item,Integer> items = new HashMap<>();

        Part testPart = new Part();
        items.put(testPart,3);


        orders.createOrder("001", items,"Grandma's house");

        return testPart;
    }

    private Product createProductOrder(){
        HashMap<Item,Integer> items = new HashMap<>();

        Product testProduct = new Product();
        items.put(testProduct,3);


        orders.createOrder("002", items,"Grandma's house");

        return testProduct;
    }

    @Test
    public void createOrderCreates() throws Exception {
        Item testItem = createPartOrder();

        assertTrue(orders.getOrder("001").getItems().containsKey(testItem));
    }

    @Test
    public void createOrderQuantity() throws Exception {

        createPartOrder();

        //NOTE: using "0000" for getQuantity is bad because it couples the test to implementation details
        //      (default construction)
        orders.getOrder("001");
        assertEquals(3,orders.getOrder("001").getQuantity("0000"));

    }

    @Test
    public void createOrderNoExtra() throws Exception {

        createPartOrder();

        assertEquals(1,orders.getOrder("001").getItems().size());
    }

    private Part updatePartOrder(){
        createPartOrder();

        HashMap<Item,Integer> items = new HashMap<>();

        Part testPart = new Part("0002",PartCategory.SCREEN,"Name",.0);

        items.put(testPart,12);

        orders.updateOrder("001", items);

        return testPart;

    }

    @Test
    public void updateOrderAddsNewItems() throws Exception {

        Item testItem = updatePartOrder();

        assertTrue(orders.getOrder("001").getItems().containsKey(testItem));

    }

    @Test
    public void updateOrderReplacesOldItems() throws Exception {

        Item testItem = updatePartOrder();

        assertEquals(1 ,orders.getOrder("001").getItems().size());
    }

    @Test
    public void addProductToOrderOnce() throws Exception {

        createProductOrder();

        orders.addProductToOrder("002","F0001N",21);

        assertEquals(21 ,orders.getOrder("002").getQuantity("F0001N"));

    }

    @Test
    public void addProductToOrderTwice() throws Exception {

        createProductOrder();

        orders.addProductToOrder("002","F0001N",21);

        orders.addProductToOrder("002","F0001N",12);

        assertEquals(33 ,orders.getOrder("002").getQuantity("F0001N"));

    }

    @Test
    public void addPartToOrderOnce() throws Exception {

        createPartOrder();

        orders.addPartToOrder("001","0002",21);

        assertEquals(21 ,orders.getOrder("001").getQuantity("0002"));

    }

    @Test
    public void addPartToOrderTwice() throws Exception {

        createPartOrder();

        orders.addPartToOrder("001","0002",21);

        orders.addPartToOrder("001","0002",12);

        assertEquals(33 ,orders.getOrder("001").getQuantity("0002"));

    }

    @Test
    public void removeProductFromOrderPartially() throws Exception {

        createProductOrder();

        orders.addProductToOrder("002","F0001N",21);

        orders.removeProductFromOrder("002","F0001N",10);

        assertEquals(11 ,orders.getOrder("002").getQuantity("F0001N"));

    }

    @Test
    public void removeProductFromOrderCompletely() throws Exception {

        createProductOrder();

        orders.addProductToOrder("002","F0001N",3);

        orders.removeProductFromOrder("002","F0001N",3);

        //1 is expected because createProductOrder adds a product we don't use to the order
        assertEquals(1 ,orders.getOrder("002").getItems().size());

    }

    @Test
    public void removeProductFromOrderBadId() throws Exception {

        createProductOrder();

        orders.addProductToOrder("002","F0001N",3);

        try {
            orders.removeProductFromOrder("002", "C0001N", 3);
        } catch(Exception e){

        }

        assertEquals(2 ,orders.getOrder("002").getItems().size());

    }

    @Test
    public void removePartFromOrderPartially() throws Exception {

        createPartOrder();

        orders.addPartToOrder("001","0001",21);

        orders.removePartFromOrder("001","0001",10);

        assertEquals(11 ,orders.getOrder("001").getQuantity("0001"));
    }

    @Test
    public void removePartFromOrderCompletely() throws Exception {

        createPartOrder();

        orders.addPartToOrder("001","0001",3);

        orders.removePartFromOrder("001","0001",3);

        //1 is expected because createPartOrder adds a part we don't use to the order
        assertEquals(1 ,orders.getOrder("001").getItems().size());
    }

    @Test
    public void removePartFromOrderBadId() throws Exception {

        createPartOrder();

        orders.addPartToOrder("001","0001",3);

        try {
            orders.removePartFromOrder("001", "0002", 3);
        } catch(Exception e){

        }

        assertEquals(2 ,orders.getOrder("001").getItems().size());

    }

    @Test
    public void removeOrder() throws Exception {

        createPartOrder();

        orders.removeOrder("001");

        assertEquals(0,orders.getOrders().size());

    }

    @Test
    public void removeOrderBadId() throws Exception {

        createPartOrder();

        orders.removeOrder("002");

        assertEquals(1,orders.getOrders().size());

    }



}