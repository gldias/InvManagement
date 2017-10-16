package inventory_app.domain_layer;

import inventory_app.data_mappers.mapperInterface;

import java.util.*;

/**
 * Responsible for managing item objects and their data mappers
 *
 * All item related CRUD operations are done through this class
 */
public class InventoryManager {

    //data duplication necessary for performance
    Set<Part> parts;
    Set<Product> products;

    mapperInterface partMapper;
    mapperInterface productMapper;

    public InventoryManager(){
        //maybe change these to hashmaps? with SKU as the key?
        parts = new HashSet<>();
        products = new HashSet<>();

        //todo replace these with actual Mappers
        partMapper = null;
        productMapper = null;
    }

    /**
     * Creates and stores a new product with default attributes.
     */
    public Product createProduct(){
        Product p = new Product();

        products.add(p);
        productMapper.insert(p);
        return p;
    }

    /**
     * Creates and stores a new product with specified attributes.
     *
     * @param name product name
     * @param category product category (Healthy, Active, Brainy, etc.)
     * @param SKU product identifier
     * @param weight weight of given product in pounds(Ibs)
     */
    public Product createProduct(String name, ProductCategory category, String SKU, double weight){
        Product p = new Product(name, category, SKU, weight);

        products.add(p);
        productMapper.insert(p);
        return p;
    }

    /**
     * Finds the product with the given SKU
     *
     * @param SKU
     */
    public Product getProduct(String SKU){

        for(Product p : products){
            if(SKU == p.getSKU()){
                return p;
            }
        }
        return null;
    }

    /**
     * Shows all products
     */
    public Collection<Product> getProducts(){
        return products;
    }

    /**
     * returns a collection of products based on narrowing keywords
     *
     * @param searchCriteria
     *
     *  for each key, only returns products with matching values
     *
     *  valid keys: name, category, sku
     *
     *
     * @param partialMatch
     *
     *  for each key mapped to true, it will narrow based on partial keywords
     */
    public Collection<Product> getProducts(HashMap<String,String> searchCriteria, HashMap<String, Boolean> partialMatch){
        //
        return null;
        //todo low priority
    }

    /**
     * updates a given product. Rejects the update if the SKU does not match an existing product.
     *
     * @param name product name
     * @param category product category (Healthy, Active, Brainy, etc.)
     * @param SKU product identifier
     * @param weight weight of given product in pounds(Ibs)
     */
    public Product updateProduct(String name, ProductCategory category, String SKU, double weight){

        /**
        Product p = new Product(SKU, category, name, weight);

        if(products.contains(p)) {
            deleteProduct(p);
            createProduct(SKU, category, name, weight);
        }
         */
        Product product = getProduct(SKU);

        if(product == null){
            return null;
        }

        deleteProduct(SKU);

        product = createProduct(name, category, SKU, weight);
        productMapper.update(product);
        return product;
    }

    /**
     * removes given product from the system
     * @param SKU identifier of the product to be removed
     */
    public Product deleteProduct(String SKU){
        Product p = getProduct(SKU);
        deleteProduct(p);
        return p;
    }

    /**
     * removes given product from the system
     * @param product the product to be removed
     */
    public Product deleteProduct(Product product){

        products.remove(product);
        productMapper.delete(product);

        return product;
    }

    /**
     * Creates and stores a new product with default attributes.
     */
    public Part createPart(){
        return null;
        //todo
    }

    /**
     * Creates and stores a new product with specified attributes.
     *
     * @param name part name
     * @param category part category (Antenna, RAM, HD, etc.)
     * @param id part identifier
     * @param weight weight of given part in pounds(Ibs)
     */
    public Part createPart(String name, PartCategory category, String id, double weight){
        return null;
        //todo
    }

    /**
     * Finds the product with the given SKU
     *
     * @param id
     */
    public Part getPart(String id){
        return null;
        //todo
    }

    /**
     * Shows all products
     */
    public Collection<Part> getParts(){
        return null;
        //todo
    }

    /**
     * returns a collection of products based on narrowing keywords
     *
     * @param searchCriteria
     *
     *  for each key, only returns products with matching values
     *
     *  valid keys: name, category, id
     *
     *
     * @param partialMatch
     *
     *  for each key mapped to true, it will narrow based on partial keywords
     */
    public Collection<Part> getParts(HashMap<String,String> searchCriteria, HashMap<String, Boolean> partialMatch){
        return null;
        //todo
    }

    /**
     * updates a given product. Rejects the update if the SKU does not match an existing product.
     *
     * @param name part name
     * @param category part category
     * @param id part identifier
     * @param weight weight of given part in pounds(Ibs)
     */
    public Part updatePart(String name, PartCategory category, String id, double weight){
        return null;
        //todo
    }

    /**
     * removes given product from the system
     * @param id identifier of the part to be removed
     */
    public Part deletePart(String id){
        return null;
        //todo
    }

    /**
     * removes given product from the system
     * @param part the product to be removed
     */
    public Part deletePart(Part part){
        return null;
    }

    public void addProducts(Product product, int quantity){
        //todo
    }

    public void removeProducts(Product product, int quantity){
        //todo
    }

    public void addParts(Part part, int quantity){
        //todo
    }

    public void removeParts(Part part, int quantity){
        //todo
    }

}
