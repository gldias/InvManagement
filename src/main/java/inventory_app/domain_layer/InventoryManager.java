package inventory_app.domain_layer;

import inventory_app.data_mappers.*;

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

    private static InventoryManager staticManager = new InventoryManager();

    public InventoryManager(){
        //maybe change these to hashmaps? with SKU as the key?
        parts = new HashSet<>();
        products = new HashSet<>();

        //todo replace these with actual Mappers
        partMapper = new ItemDM();
        productMapper = new ItemDM();
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
            if(SKU.equals(p.getSKU())){
                return p;
            }
        }
        return null;
    }

    /**
     * Shows all products
     */
    public Set<Product> getProducts(){
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

    public Part createPart(Part part){

        parts.add(part);
        partMapper.insert(part);

        return part;
    }

    /**
     * Creates and stores a new product with default attributes.
     */
    public Part createPart(){

        Part part = new Part();

        return createPart(part);
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

        Part part = new Part(id,category,name,weight);

        return createPart(part);
    }

    /**
     * Finds the product with the given SKU
     *
     * @param id
     */
    public Part getPart(String id){
        for(Part p : parts){
            if(p.getId().equals(id)){
                return p;
            }
        }

        return null;
    }

    /**
     * Shows all products
     */
    public Set<Part> getParts(){
        return parts;
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
        //todo low priority
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
        Part oldPart = getPart(id);

        if(oldPart == null){
            return null;
        }

        Part updatedPart = new Part(id, category, name, weight);

        parts.add(updatedPart);
        partMapper.update(updatedPart);

        return updatedPart;
    }

    /**
     * removes given product from the system
     * @param part the product to be removed
     */
    public Part deletePart(Part part){

        parts.add(part);
        partMapper.delete(part);

        return part;
    }

    /**
     * removes given product from the system
     * @param id identifier of the part to be removed
     */
    public Part deletePart(String id){
        Part part = getPart(id);

        return deletePart(part);
    }

    /**
     * Adds to the quantity of a product stored
     *
     * @param product the product being added to
     * @param quantity the quantity of the product added
     * @return the product whose quantity increased
     */
    public Product addProducts(Product product, int quantity){
        if(!products.contains(product)){
            //throw exception here?
            assert false;
        }

        product.addQuantity(quantity);

        products.add(product);
        productMapper.update(product);

        return product;
    }

    /**
     * Adds to the quantity of a product stored
     *
     * @param SKU the SKU of the product being added to
     * @param quantity the quantity of the product added
     * @return the product whose quantity increased
     */
    public Product addProducts(String SKU, int quantity){
        Product product = getProduct(SKU);

        if(product == null){
            return null;
        }

        return addProducts(product, quantity);
    }

    /**
     * Subtracts from the quantity of a product stored
     *
     * @param product the product being subtracted from
     * @param quantity the quantity of the product subtracted
     * @return the product whose quantity decreased
     */
    public Product removeProducts(Product product, int quantity){
        if(!products.contains(product)){
            //throw exception here?
            assert false;
        }

        product.subtractQuantity(quantity);

        products.add(product);
        productMapper.update(product);

        return product;
    }

    /**
     * Subtracts from the quantity of a product stored
     *
     * @param SKU the SKU of the product being subtracted from
     * @param quantity the quantity of the product subtracted
     * @return the product whose quantity decreased
     */
    public Product removeProduct(String SKU, int quantity){
        Product product = getProduct(SKU);

        if(product == null){
            return null;
        }

        return removeProducts(product, quantity);
    }

    /**
     * Adds to the quantity of a part stored
     *
     * @param part the part being added to
     * @param quantity the quantity of the part added
     * @return the part whose quantity increased
     */
    public Part addParts(Part part, int quantity){
        if(!parts.contains(part)){
            //throw exception here?
            assert false;
        }

        part.addQuantity(quantity);

        parts.add(part);
        partMapper.update(part);

        return part;
    }

    /**
     * Adds to the quantity of a part stored
     *
     * @param id the id of the part being added to
     * @param quantity the quantity of the part added
     * @return the part whose quantity increased
     */
    public Part addParts(String id, int quantity){
        Part part = getPart(id);

        if(part == null){
            return null;
        }

        return addParts(part, quantity);
    }

    /**
     * Subtracts from the quantity of a part stored
     *
     * @param part the part being subtracted from
     * @param quantity the quantity of the part subtracted
     * @return the part whose quantity decreased
     */
    public Part removeParts(Part part, int quantity){
        if(!parts.contains(part)){
            //throw exception here?
            assert false;
        }

        part.subtractQuantity(quantity);

        parts.add(part);
        partMapper.update(part);

        return part;
    }

    /**
     * Subtracts from the quantity of a part stored
     *
     * @param id the id of the part being subtracted from
     * @param quantity the quantity of the part subtracted
     * @return the part whose quantity decreased
     */
    public Part removeParts(String id, int quantity){
        Part part = getPart(id);

        if(part == null){
            return null;
        }

        return removeParts(part, quantity);
    }

    public static InventoryManager getStaticManager() {
        return staticManager;
    }
}
