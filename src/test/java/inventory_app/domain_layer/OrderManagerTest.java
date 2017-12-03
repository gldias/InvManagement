package inventory_app.domain_layer;

import inventory_app.domain_layer.validation.ValidationResults;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

public class OrderManagerTest extends ManagerTest{

    protected OrderManager orders;

    @Before
    public void setUp2() throws Exception {
        orders = new OrderManager();
        System.out.println("setup complete");
    }

    @Test
    public void testCreateEmptyOrder() throws Exception {
        orders.createOrder(1);
        assertTrue(orders.getOrder(1).getItems().isEmpty());
        orders.removeOrder(1);
    }

    private Part createPartOrder(){
        HashMap<Item,Integer> items = new HashMap<>();

        Part testPart = new Part();
        items.put(testPart,3);


        orders.createOrder(items,"Grandma's house");

        return testPart;
    }

    private Product createProductOrder(){
        HashMap<Item,Integer> items = new HashMap<>();

        Product testProduct = new Product();
        items.put(testProduct,3);


        orders.createOrder(items,"Grandma's house");

        return testProduct;
    }

    @Test
    public void testCreateOrderCreates() throws Exception {
        Item testItem = createPartOrder();

        assertTrue(orders.getOrder(1).getItems().containsKey(testItem));

        orders.removeOrder(1);
    }

    @Test
    public void testCreateOrderQuantity() throws Exception {

        createPartOrder();

        //NOTE: using "0000" for getQuantity is bad because it couples the test to implementation details
        //      (default construction)
        orders.getOrder(1);
        assertEquals(3,orders.getOrder(1).getQuantity("0000"));

        orders.removeOrder(1);

    }

    @Test
    public void testCreateOrderNoExtra() throws Exception {

        createPartOrder();

        assertEquals(1,orders.getOrder(1).getItems().size());
    }

    private Part updatePartOrder(){
        createPartOrder();

        HashMap<Item,Integer> items = new HashMap<>();

        Part testPart = new Part("0002",PartCategory.SCREEN,
                "Name",.0);

        items.put(testPart,12);

        orders.updateOrder(1, items);

        return testPart;

    }

    @Test
    public void testUpdateOrderAddsNewItems() throws Exception {

        Item testItem = updatePartOrder();

        assertTrue(orders.getOrder(1).getItems().containsKey(testItem));

    }

    @Test
    public void testUpdateOrderReplacesOldItems() throws Exception {

        Item testItem = updatePartOrder();

        assertEquals(1 ,orders.getOrder(1).getItems().size());
    }

    @Test
    public void testAddProductToOrderOnce() throws Exception {

        createProductOrder();

        orders.addProductToOrder(2,"F0001N",21);

        assertEquals(21 ,orders.getOrder(2).getQuantity("F0001N"));

    }

    @Test
    public void testAddProductToOrderTwice() throws Exception {

        createProductOrder();

        orders.addProductToOrder(2,"F0001N",21);

        orders.addProductToOrder(2,"F0001N",12);

        assertEquals(33 ,orders.getOrder(2).getQuantity("F0001N"));

    }

    @Test
    public void testAddPartToOrderOnce() throws Exception {

        createPartOrder();

        orders.addPartToOrder(2,"0002",21);

        assertEquals(21 ,orders.getOrder(1).getQuantity("0002"));

    }

    @Test
    public void testAddPartToOrderTwice() throws Exception {

        createPartOrder();

        orders.addPartToOrder(1,"0002",21);

        orders.addPartToOrder(1,"0002",12);

        assertEquals(33 ,orders.getOrder(1).getQuantity("0002"));

    }

    @Test
    public void testRemoveProductFromOrderPartially() throws Exception {

        createProductOrder();

        orders.addProductToOrder(2,"F0001N",21);

        orders.removeProductFromOrder(2,"F0001N",10);

        assertEquals(11 ,orders.getOrder(2).getQuantity("F0001N"));

    }

    @Test
    public void testRemoveProductFromOrderCompletely() throws Exception {

        createProductOrder();

        orders.addProductToOrder(2,"F0001N",3);

        orders.removeProductFromOrder(2,"F0001N",3);

        //1 is expected because createProductOrder adds a product we don't use to the order
        assertEquals(1 ,orders.getOrder(2).getItems().size());

    }

    @Test
    public void testRemoveProductFromOrderBadId() throws Exception {

        createProductOrder();

        orders.addProductToOrder(2,"F0001N",3);

        try {
            orders.removeProductFromOrder(2, "C0001N", 3);
        } catch(Exception e){

        }

        assertEquals(2 ,orders.getOrder(2).getItems().size());

    }

    @Test
    public void testRemovePartFromOrderPartially() throws Exception {

        createPartOrder();

        orders.addPartToOrder(1,"0001",21);

        orders.removePartFromOrder(1,"0001",10);

        assertEquals(11 ,orders.getOrder(1).getQuantity("0001"));
    }

    @Test
    public void testRemovePartFromOrderCompletely() throws Exception {

        createPartOrder();

        orders.addPartToOrder(1,"0001",3);

        orders.removePartFromOrder(1,"0001",3);

        //1 is expected because createPartOrder adds a part we don't use to the order
        assertEquals(1 ,orders.getOrder(1).getItems().size());
    }

    @Test
    public void testRemovePartFromOrderBadId() throws Exception {

        createPartOrder();

        orders.addPartToOrder(1,"0001",3);

        try {
            orders.removePartFromOrder(1, "0002", 3);
        } catch(Exception e){

        }

        assertEquals(2 ,orders.getOrder(1).getItems().size());

    }

    @Test
    public void testRemoveOrder() throws Exception {

        createPartOrder();

        orders.removeOrder(1);

        assertEquals(0,orders.getOrders().size());

    }

    @Test
    public void testRemoveOrderBadId() throws Exception {

        createPartOrder();

        orders.removeOrder(2);

        assertEquals(1,orders.getOrders().size());
    }

    @Test
    public void removeOrderValid(){

        orders.createOrder(1);

        ValidationResults vr = orders.removeOrder(1);

        assertTrue(vr.isSuccess());
    }

    @Test
    public void removeOrderInvalid(){

        orders.createOrder(1);

        ValidationResults vr = orders.removeOrder(2);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void testAddNewOrderValid(){

        ValidationResults vr = orders.createOrder(1);

        assertTrue(vr.isSuccess());

        orders.removeOrder(1);

    }

    @Test
    public void testAddExistingOrderInvalid(){

        orders.createOrder(1);

        ValidationResults vr = orders.createOrder(1);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);

    }

    @Test
    public void testAddProductToOrderValidSku(){

        orders.createOrder(1);

        ValidationResults vr = orders.addProductToOrder(1,"F0001N",1);

        assertTrue(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void testAddProductToOrderInvalidSku(){

        orders.createOrder(1);

        ValidationResults vr = orders.addProductToOrder(1,"0", 1);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void testAddProductToOrderInvalidOrder(){

        ValidationResults vr = orders.addProductToOrder(0,"F0001N",1);

        assertFalse(vr.isSuccess());
    }

    @Test
    public void testAddProductToOrderQuantity0Invalid(){

        orders.createOrder(1);

        ValidationResults vr = orders.addProductToOrder(1,"F0001N",0);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void testAddProductToOrderQuantityNegativeInvalid(){

        orders.createOrder(1);

        ValidationResults vr = orders.addProductToOrder(1,"F0001N",-1);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void addPartToOrderValidId(){

        orders.createOrder(1);

        ValidationResults vr = orders.addPartToOrder(1,"0001",1);

        assertTrue(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void addPartToOrderInvalidId(){

        orders.createOrder(1);

        ValidationResults vr = orders.addPartToOrder(1,"0",1);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void addPartToOrderInvalidOrder(){

        ValidationResults vr = orders.addPartToOrder(1,"0001",1);

        assertFalse(vr.isSuccess());
    }

    @Test
    public void addPartToOrderQuantity0Invalid(){

        orders.createOrder(1);

        ValidationResults vr = orders.addPartToOrder(1,"0001",0);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void addPartToOrderQuantityNegativeInvalid(){

        orders.createOrder(1);

        ValidationResults vr = orders.addPartToOrder(1,"0001",-1);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removeProductFromOrderValidSku(){

        orders.createOrder(1);

        orders.addProductToOrder(1,"F0001N",1);

        ValidationResults vr = orders.removeProductFromOrder(1, "F0001N", 1);

        assertTrue(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removeProductFromOrderInvalidSku(){

        orders.createOrder(1);

        ValidationResults vr = orders.removeProductFromOrder(1, "0", 1);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removeProductFromOrderInvalidOrder(){

        ValidationResults vr = orders.removeProductFromOrder(0, "F0001N", 1);

        assertFalse(vr.isSuccess());
    }

    @Test
    public void removeProductFromOrderQuantity0Invalid(){

        orders.createOrder(1);

        orders.addProductToOrder(1,"F0001N",1);

        ValidationResults vr = orders.removeProductFromOrder(1, "F0001N", 0);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removeProductFromOrderQuantityNegativeInvalid(){

        orders.createOrder(1);

        orders.addProductToOrder(1,"F0001N",1);

        ValidationResults vr = orders.removeProductFromOrder(1, "F0001N", -1);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removeProductFromOrderQuantityGreaterThanStoredInvalid(){

        orders.createOrder(1);

        orders.addProductToOrder(1,"F0001N",1);

        ValidationResults vr = orders.removeProductFromOrder(1, "F0001N", 2);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removePartFromOrderValidId(){

        orders.createOrder(1);

        orders.addPartToOrder(1,"0001",1);

        ValidationResults vr = orders.removePartFromOrder(1,"0001", 1);

        assertTrue(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removePartFromOrderInvalidId(){

        orders.createOrder(1);

        ValidationResults vr = orders.removePartFromOrder(1,"0", 1);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removePartFromOrderQuantity0Invalid(){

        orders.createOrder(1);

        orders.addPartToOrder(1,"0001",1);

        ValidationResults vr = orders.removePartFromOrder(1,"0001", 0);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removePartFromOrderQuantityNegativeInvalid(){

        orders.createOrder(1);

        orders.addPartToOrder(1,"0001",1);

        ValidationResults vr = orders.removePartFromOrder(1,"0001", -1);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);
    }

    @Test
    public void removePartFromOrderQuantityGreaterThanStoredInvalid(){

        orders.createOrder(1);

        orders.addPartToOrder(1,"0001",1);

        ValidationResults vr = orders.removePartFromOrder(1,"0001", 2);

        assertFalse(vr.isSuccess());

        orders.removeOrder(1);

    }

}