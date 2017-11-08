package inventory_app.data_mappers;


import inventory_app.domain_layer.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ItemDMTest {

    ItemDM dm = new ItemDM();

    Product product0 = new Product();
    Part part0 = new Part();

    @Before
    public void setUp() {

        product0.setName("product0");
        product0.setCategory(ProductCategory.FASHION);
        product0.setSKU("0007");
        product0.setWeight(1);

        part0.setName("part0");
        part0.setCategory(PartCategory.ANTENNA);
        part0.setId("0007");
        part0.setWeight(0);
    }

    @Test
    public void testInsert() {
        assertEquals(true, dm.insert(product0));
        assertEquals(true, dm.insert(part0));
    }

    @Test
    public void testUpdate() {
        product0.setName("Electrostimulating nipple rings");
        part0.setName("Battery");
        assertEquals(true, dm.update(product0));
        assertEquals(true, dm.update(part0));
    }

    @Test
    public void testDelete() {
        assertEquals(true, dm.delete(product0));
        assertEquals(true, dm.delete(part0));
    }
}
