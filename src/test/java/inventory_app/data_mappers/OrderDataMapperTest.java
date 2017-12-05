package inventory_app.data_mappers;

import inventory_app.domain_layer.*;
import inventory_app.domain_layer.validation.ValidationResults;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class OrderDataMapperTest {

    private OrderDataMapper dm = new OrderDataMapper();

    private Order order0 = new Order();
    private Map<Item, Integer> items0 = new HashMap<Item, Integer>();
    private Product product0 = new Product();
    private Part part0 = new Part();

    @Before
    public void setUp() {
        product0.setSKU("F0001N");
        part0.setId("0001");

        items0.put(product0, 50);
        items0.put(part0, 100);

        order0.setId(9999);
        order0.setItems(items0);
    }

    @Test
    public void testInsertOrder() {
        assertEquals(true, dm.insert(order0));
    }

    @Test
    public void testUpdateOrder() {
        dm.insert(order0);
        order0.setId(9998);
        assertEquals(true, dm.update(order0));
    }

    @Test
    public void testFind() {
        dm.insert(order0);
        assertEquals(order0, dm.findOrder(order0.getId()));
    }

    @Test
    public void testGetTablesIncludesAllTables() {

        Map<Item,Integer> orderContents = new HashMap<>();
        orderContents.put(new Part("0001","p1",1,1.0),1);

        Order o1 = new Order();
        o1.setItems(orderContents);

        dm.insert(o1);

        Order o2 = new Order();
        o2.setItems(orderContents);

        dm.insert(o2);

        ArrayList<Order> orderTable = dm.getTable();

        assertTrue(orderTable.contains(o1));
        assertTrue(orderTable.contains(o2));

        dm.delete(o1);
        dm.delete(o2);
    }

    @Test
    @After
    public void testDeleteOrder() {
        assertEquals(true, dm.delete(order0));
    }
}
