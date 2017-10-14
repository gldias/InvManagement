package inventory_app.domain_layer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * Responsible for managing inventory objects and the data mapper
 *
 * All inventory CRUD operations are done through this class
 * All organizing of part-whole relationships between inventory objects is done through this class
 */
public class InventoryManager {
    Set<Part> parts;
    Set<Product> products;
    Set<Order> orders;

    public InventoryManager(){
        //maybe change these to hashmaps? with SKU as the key?
        parts = new HashSet<>();
        products = new HashSet<>();
        orders = new HashSet<>();
    }

    /**
     * Creates a new product with default attributes.
     */
    public void createProduct(){
        //will have to have a different SKU each time?
    }

    /**
     * Creates a new product with specified attributes.
     *
     * @param name product name
     * @param category product category (Healthy, Active, Brainy, etc.)
     * @param SKU product identifier
     * @param quantity number of given product stored
     * @param weight weight of given product in pounds(Ibs)
     */
    public void createProduct(String name, ProductCategory category, String SKU, int quantity, double weight){

    }

    /**
     * Finds the product with the given SKU
     *
     * @param SKU
     */
    public void getProduct(String SKU){

    }

    /**
     * Shows all products
     */
    public void getProducts(){

    }

    /**
     * returns a list of products based on narrowing keywords
     *
     * @param searchCriteria
     *
     *  for each key, only returns products with matching values
     *
     * @param partialMatch
     *
     *  for each key mapped to true, it will narrow based on partial keywords
     */
    public void getProducts(HashMap<String,String> searchCriteria, HashMap<String, Boolean> partialMatch){

    }

    /**
     * updates a given product. Rejects the update if the SKU does not match an existing product.
     *
     * @param name product name
     * @param category product category (Healthy, Active, Brainy, etc.)
     * @param SKU product identifier
     * @param quantity number of given product stored
     * @param weight weight of given product in pounds(Ibs)
     */
    public void updateProduct(String name, ProductCategory category, String SKU, int quantity, double weight){

    }

    /**
     * removes given product from the system
     * @param SKU identifier of the product to be removed
     */
    public void deleteProduct(String SKU){

    }

    /**
     * removes given product from the system
     * @param product the product to be removed
     */
    public void deleteProduct(Product product){

    }

}
