package stubs;

/**
 * Stub for API calls inventory_app would make to the accounting silo
 */
public class accounting {
    /**
     * Stub for checking funds when making a parts order
     * @param amount - the amount of money being requested
     * @return bool - whether there are enough funds do the order or not
     */
    public boolean checkFunds(double amount){
        return true;
    }
}
