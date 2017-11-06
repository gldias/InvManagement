package stubs;

/**
 * Stub for API calls inventory_app would make to the accounting silo
 */
public class accounting {

    private int funds;
    private static double epsilon = .000001;
    public static accounting singleAccounting = null;

    private accounting(){
        funds = 2000;
    }

    /**
     * Stub for checking funds when making a parts order
     * @param amount - the amount of money being requested
     * @return bool - whether there are enough funds do the order or not
     */
    public boolean checkFunds(double amount){

        //check to make sure amount positive
        if(amount < 0 - epsilon) {
            return false;
        }

        //make sure there is enough left in the budget
        if(amount <= funds){
            funds -= amount;
            return true;
        }

        return false;
    }

    public static accounting getAccounting(){
        if(singleAccounting == null){
            singleAccounting  = new accounting();
        }

        return singleAccounting;
    }
}
