package stubs;

/**
 * A stub for API calls inventory_app would make to the Sales silo
 */

public class sales {

    private static sales singleSales = null;

    private sales(){}

    /**
     * Stub for confirming an order
     * @param id - the order ID
     */
    public void confirmOrder(String id){

    }

    public static sales getSales(){
        if(singleSales == null){
            singleSales = new sales();
        }

        return singleSales;
    }
}
