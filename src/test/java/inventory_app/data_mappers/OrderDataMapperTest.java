package inventory_app.data_mappers;

import inventory_app.domain_layer.*;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.HashMap;
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
    @After
    public void testDeleteOrder() {
        assertEquals(true, dm.delete(order0));
    }
}
