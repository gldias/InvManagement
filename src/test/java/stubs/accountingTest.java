package stubs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class accountingTest {

    accounting accountingStub;

    //this variable is assumed to be > 10
    static double baseMoney = 2000;

    @Before
    public void setup(){
        accountingStub = accounting.getAccounting();
    }

    @After
    public void teardown(){
        accounting.resetAccounting();
    }

    @Test
    public void checkFundsSingleCallPassZero() throws Exception {
        assertTrue(accountingStub.checkFunds(0));
    }

    @Test
    public void checkFundsSingleCallPassNominal() throws Exception {
        assertTrue(accountingStub.checkFunds(5.0));
    }

    @Test
    public void checkFundsSingleCallPassBorder() throws Exception {
        assertTrue(accountingStub.checkFunds(baseMoney));
    }

    @Test
    public void checkFundsSingleCallFailNominal() throws Exception {
        assertFalse(accountingStub.checkFunds(baseMoney + 100.0));
    }

    @Test
    public void checkFundsSingleCallFailBorder() throws Exception {
        assertFalse(accountingStub.checkFunds(baseMoney + .01));
    }

    @Test
    public void checkFundsSingleCallFailNegative() throws Exception {
        assertFalse(accountingStub.checkFunds(-3.0));
    }

    @Test
    public void checkFundsMultipleCallPass() throws Exception{
        accountingStub.checkFunds(baseMoney - 10);
        assertTrue(accountingStub.checkFunds(5));
    }

    @Test
    public void checkFundsMultipleCallFail() throws Exception{

        accountingStub.checkFunds(baseMoney - 10);
        assertFalse(accountingStub.checkFunds(30));

    }
}