package inventory_app.data_mappers;


import inventory_app.domain_layer.*;
import org.junit.*;

import javax.validation.constraints.AssertFalse;

import static org.junit.Assert.*;

public class PartDataMapperTest {

    PartDataMapper partDM = new PartDataMapper();

    private Part part0 = new Part();

    @Before
    public void setUp() {

        part0.setName("part0");
        part0.setCategory(PartCategory.ANTENNA);
        part0.setId("7777");
        part0.setWeight(1);

    }

    @Test
    public void testInsert() {
        assertEquals(true, partDM.insert(part0));
    }

    @Test
    public void testUpdate() {
        partDM.insert(part0);
        part0.setName("antenna");
        assertEquals(true, partDM.update(part0));
    }

    @Test
    public void testFind() {
        partDM.insert(part0);
        assertEquals(part0, partDM.findById(part0.getId()));
    }

    @Test
    @After
    public void testDelete() {
        partDM.insert(part0);
        assertEquals(true, partDM.delete(part0));
    }
}
