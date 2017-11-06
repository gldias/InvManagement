package inventory_app.data_mappers;

import inventory_app.domain_layer.*;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class OrderDMTest {

    OrderDM dm = new OrderDM();

    Order order0 = new Order();
    Map<Item, Integer> items0 = new HashMap<Item, Integer>();
    Product product0 = new Product();
    Part part0 = new Part();

    @Before
    public void setUp() {
        product0.setSKU("0001");
        part0.setId("0001");

        items0.put(product0, 50);
        items0.put(part0, 100);

        order0.setId("03");
        order0.setItems(items0);
    }

    @Test
    public void testInsertOrder() {
        assertEquals(true, dm.insert(order0));
    }

    @Test
    public void testUpdateOrder() {

    }

    @Test
    public void testDeleteOrder() {
        //dm.insert(this.order0);
        //Order orderToDelete = this.order0;
        //assertEquals(true, dm.delete(orderToDelete));
    }
}
