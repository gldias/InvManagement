package stubs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class manufacturingTest {

    manufacturing manufacturingStub;

    @Before
    public void setup(){
        manufacturingStub = manufacturing.getManufacturing();
    }

    @After
    public void teardown(){
        manufacturing.resetManufacturing();
    }

    @Test
    public void orderRequestFirstSuccess() throws Exception {
        //first call is always a success
        assertTrue(manufacturingStub.orderRequest("0001",20, "parts"));
    }

    @Test
    public void orderRequestFirstFailure() throws Exception {
        //third call is always a failure
        manufacturingStub.orderRequest("0001",20, "parts");
        manufacturingStub.orderRequest("0002",20, "products");
        assertFalse(manufacturingStub.orderRequest("0003",20, "parts"));
    }

    @Test
    public void orderRequestSecondFailure() throws Exception {
        //8th call is always a failure
        manufacturingStub.orderRequest("0001",20, "parts");
        manufacturingStub.orderRequest("0002",20, "products");
        manufacturingStub.orderRequest("0003",20, "parts");
        manufacturingStub.orderRequest("0004",20, "products");
        manufacturingStub.orderRequest("0005",20, "products");
        manufacturingStub.orderRequest("0006",20, "parts");
        manufacturingStub.orderRequest("0007",20, "products");
        assertFalse(manufacturingStub.orderRequest("0008",20, "parts"));
    }

    @Test
    public void orderRequestSeventhSuccess() throws Exception {
        //9th call is always a success
        manufacturingStub.orderRequest("0001",20, "parts");
        manufacturingStub.orderRequest("0002",20, "products");
        manufacturingStub.orderRequest("0003",20, "parts");
        manufacturingStub.orderRequest("0004",20, "products");
        manufacturingStub.orderRequest("0005",20, "products");
        manufacturingStub.orderRequest("0006",20, "parts");
        manufacturingStub.orderRequest("0007",20, "products");
        manufacturingStub.orderRequest("0008",20, "products");
        assertTrue(manufacturingStub.orderRequest("0009",20, "parts"));
    }

    @Test
    public void sendPartsFirstSuccess() throws Exception {
        //first call is always a success
        assertTrue(manufacturingStub.sendParts("0001",20));
    }

    @Test
    public void sendPartsFirstFailure() throws Exception {
        //third call is always a failure
        manufacturingStub.sendParts("0001",20);
        manufacturingStub.sendParts("0002",20);
        assertFalse(manufacturingStub.sendParts("0003",20));
    }

    @Test
    public void sendPartsSecondFailure() throws Exception {
        //8th call is always a failure
        manufacturingStub.sendParts("0001",20);
        manufacturingStub.sendParts("0002",20);
        manufacturingStub.sendParts("0003",20);
        manufacturingStub.sendParts("0004",20);
        manufacturingStub.sendParts("0005",20);
        manufacturingStub.sendParts("0006",20);
        manufacturingStub.sendParts("0007",20);
        assertFalse(manufacturingStub.sendParts("0008",20));
    }

    @Test
    public void sendPartsSeventhSuccess() throws Exception {
        //9th call is always a success
        manufacturingStub.sendParts("0001",20);
        manufacturingStub.sendParts("0002",20);
        manufacturingStub.sendParts("0003",20);
        manufacturingStub.sendParts("0004",20);
        manufacturingStub.sendParts("0005",20);
        manufacturingStub.sendParts("0006",20);
        manufacturingStub.sendParts("0007",20);
        manufacturingStub.sendParts("0008",20);
        assertTrue(manufacturingStub.sendParts("0009",20));
    }

}