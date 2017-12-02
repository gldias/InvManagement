package inventory_app.data_mappers;


import inventory_app.domain_layer.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ProductDataMapperTest {

    private ProductDataMapper productDM = new ProductDataMapper();
    private Product product0 = new Product();

    @Before
    public void setUp() {
        product0.setName("product0");
        product0.setCategory(ProductCategory.FASHION);
        product0.setSKU("F0007N");
        product0.setWeight(1);
    }

    @Test
    public void testInsert() {
        assertEquals(true, productDM.insert(product0));
    }

    @Test
    public void testUpdate() {
        productDM.insert(product0);
        product0.setName("Electrostimulating nipple rings");
        assertEquals(true, productDM.update(product0));
    }

    @Test
    public void testFind() {
        productDM.insert(product0);
        assertEquals(product0, productDM.findBySKU(product0.getSKU()));
    }

    @Test
    @After
    public void testDelete() {
        productDM.insert(product0);
        assertEquals(true, productDM.delete(product0));
    }
}
