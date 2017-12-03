package inventory_app.domain_layer;

import inventory_app.data_mappers.*;
import inventory_app.domain_layer.validation.*;
import stubs.FakePartMapper;
import stubs.FakeProductMapper;

import javax.validation.Valid;
import java.sql.Connection;
import java.util.*;

/**
 * Responsible for managing item objects and their data mappers
 *
 * All item related CRUD operations are done through this class
 */
public class InventoryManager {

    //data duplication necessary for performance
    //Set<Part> parts;

    PartDataMapper partMapper;
    ProductDataMapper productMapper;

    private static InventoryManager staticManager = new InventoryManager(new PartDataMapper(),new ProductDataMapper());

    private static InventoryManager testManager = new InventoryManager(new FakePartMapper(), new FakeProductMapper());

    public InventoryManager(){
        //maybe change these to hashmaps? with SKU as the key?
        //parts = new HashSet<>();
        //products = new HashSet<>();

        //todo replace these with actual Mappers
        partMapper = new PartDataMapper();
        productMapper = new ProductDataMapper();
    }

    /**
     * Constructor with dependency injection
     *
     * @param _partMapper
     */
    public InventoryManager(PartDataMapper _partMapper, ProductDataMapper _productMapper){
        //maybe change these to hashmaps? with SKU as the key?

        //todo replace these with actual Mappers
        partMapper = _partMapper;
        productMapper = _productMapper;
    }

    /**
     * Creates and stores a new product with default attributes.
     */
    public Product createProduct(){
        Product p = new Product();

        productMapper.insert(p);
        return p;
    }

    private ValidationResults createProduct(Product product){

        ValidationResults vr = validateProduct(product);

        if(vr.isSuccess()){
            productMapper.insert(product);
        }

        vr.setValidatedObject(product);

        return vr;
    }

    /**
     * Creates and stores a new product with specified attributes.
     *
     * @param name product name
     * @param category product category (Healthy, Active, Brainy, etc.)
     * @param SKU product identifier
     * @param weight weight of given product in pounds(Ibs)
     */
    public ValidationResults createProduct(String name, ProductCategory category, String SKU, double weight){
        Product p = new Product(name, category, SKU, weight);

        return createProduct(p);
    }

    /**
     * Finds the product with the given SKU
     *
     * @param SKU
     */
    public Product getProduct(String SKU){

        return productMapper.findBySKU(SKU);
    }

    /**
     * Shows all products
     */
    public ArrayList<Product> getProducts(){
        return productMapper.getTable();
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
    private Collection<Product> getProducts(HashMap<String,String> searchCriteria, HashMap<String, Boolean> partialMatch){
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
    public ValidationResults updateProduct(String name, ProductCategory category, String SKU, double weight){

        /**
        Product p = new Product(SKU, category, name, weight);

        if(products.contains(p)) {
            deleteProduct(p);
            createProduct(SKU, category, name, weight);
        }
         */
        Product product = getProduct(SKU);

        if(product == null){
            return new ValidationResults(String.format("Product with SKU %s does not exist",SKU));
        }

        deleteProduct(SKU);

        product = new Product(name, category, SKU, weight);

        createProduct(product);
        productMapper.update(product);

        ValidationResults vr = new ValidationResults();

        vr.setValidatedObject(product);

        return vr;
    }

    /**
     * removes given product from the system
     * @param product the product to be removed
     */
    private ValidationResults deleteProduct(Product product){

        productMapper.delete(product);

        ValidationResults vr = new ValidationResults();

        vr.setValidatedObject(product);

        return vr;
    }

    /**
     * removes given product from the system
     * @param SKU identifier of the product to be removed
     */
    public ValidationResults deleteProduct(String SKU){
        Product p = getProduct(SKU);

        if(p == null){
            return new ValidationResults(String.format("Product with SKU %s does not exist",SKU));
        }

        return deleteProduct(p);
    }

    private ValidationResults createPart(Part part){

        ValidationResults vr = validatePart(part);

        if(vr.isSuccess()) {
            partMapper.insert(part);
        }

        vr.setValidatedObject(part);

        return vr;
    }


    /**
     * Creates and stores a new product with specified attributes.
     *
     * @param name part name
     * @param category part category (Antenna, RAM, HD, etc.)
     * @param id part identifier
     * @param weight weight of given part in pounds(Ibs)
     */
    public ValidationResults createPart(String name, PartCategory category, String id, double weight){

        Part part = new Part(id,category,name,weight);

        return createPart(part);
    }

    /**
     * Finds the product with the given SKU
     *
     * @param id
     */
    public Part getPart(String id){

        return partMapper.findById(id);
    }

    /**
     * Shows all products
     */
    public ArrayList<Part> getParts(){
        return partMapper.getTable();
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
    private Collection<Part> getParts(HashMap<String,String> searchCriteria, HashMap<String, Boolean> partialMatch){
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
    public ValidationResults updatePart(String name, PartCategory category, String id, double weight){
        Part oldPart = getPart(id);

        if(oldPart == null){
            return new ValidationResults(String.format("Part with ID %s does not exist",id));
        }

        Part updatedPart = new Part(id, category, name, weight);

        partMapper.update(updatedPart);

        ValidationResults vr = new ValidationResults();

        vr.setValidatedObject(updatedPart);

        return vr;
    }

    /**
     * removes given product from the system
     * @param part the product to be removed
     */
    private ValidationResults deletePart(Part part){

        partMapper.delete(part);

        ValidationResults vr = new ValidationResults();

        vr.setValidatedObject(part);

        return vr;
    }

    /**
     * removes given product from the system
     * @param id identifier of the part to be removed
     */
    public ValidationResults deletePart(String id){
        Part part = getPart(id);

        if(part == null){
            return new ValidationResults(String.format("Part with ID %s does not exist",id));
        }

        return deletePart(part);
    }

    /**
     * Adds to the quantity of a product stored
     *
     * @param product the product being added to
     * @param quantity the quantity of the product added
     * @return the product whose quantity increased
     */
    private ValidationResults addProducts(Product product, int quantity){
        if(!productMapper.getTable().contains(product)){
            //throw exception here?
            return new ValidationResults("Product does not exist");
        }

        product.addQuantity(quantity);

        productMapper.update(product);

        return new ValidationResults();
    }

    /**
     * Adds to the quantity of a product stored
     *
     * @param SKU the SKU of the product being added to
     * @param quantity the quantity of the product added
     * @return the product whose quantity increased
     */
    public ValidationResults addProducts(String SKU, int quantity){
        Product product = getProduct(SKU);

        if(product == null){
            return new ValidationResults(String.format("Product with SKU %s does not exist",SKU));
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
    private ValidationResults removeProducts(Product product, int quantity){
        if(!productMapper.getTable().contains(product)){
            //throw exception here?
            return new ValidationResults("Product does not exist");
        }

        product.subtractQuantity(quantity);

        productMapper.update(product);

        return new ValidationResults();
    }

    /**
     * Subtracts from the quantity of a product stored
     *
     * @param SKU the SKU of the product being subtracted from
     * @param quantity the quantity of the product subtracted
     * @return the product whose quantity decreased
     */
    public ValidationResults removeProducts(String SKU, int quantity){
        Product product = getProduct(SKU);

        if(product == null){
            return new ValidationResults(String.format("Product with SKU %s does not exist",SKU));
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
    private ValidationResults addParts(Part part, int quantity){
        if(!getParts().contains(part)){
            //throw exception here?
            return new ValidationResults(String.format("Part does not exist"));
        }

        part.addQuantity(quantity);
        partMapper.update(part);

        return new ValidationResults();
    }

    /**
     * Adds to the quantity of a part stored
     *
     * @param id the id of the part being added to
     * @param quantity the quantity of the part added
     * @return the part whose quantity increased
     */
    public ValidationResults addParts(String id, int quantity){
        Part part = getPart(id);

        if(part == null){
            return new ValidationResults(String.format("Part with ID %s does not exist",id));
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
    private ValidationResults removeParts(Part part, int quantity){
        if(!getParts().contains(part)){
            return new ValidationResults("Part does not exist");
        }

        part.subtractQuantity(quantity);
        partMapper.update(part);

        return new ValidationResults();
    }

    /**
     * Subtracts from the quantity of a part stored
     *
     * @param id the id of the part being subtracted from
     * @param quantity the quantity of the part subtracted
     * @return the part whose quantity decreased
     */
    public ValidationResults removeParts(String id, int quantity){
        Part part = getPart(id);

        if(part == null){
            return new ValidationResults(String.format("Part with ID %s does not exist",id));
        }

        return removeParts(part, quantity);
    }

    private ValidationResults validateItem(Item i){
        StringLengthFloorValidator slfv = new StringLengthFloorValidator(1);
        DoubleNotNegativeValidator dnnv = new DoubleNotNegativeValidator();

        List<ValidationResults> resultList = new ArrayList<>();

        resultList.add(slfv.validate(i.getName()));
        resultList.add(dnnv.validate(i.getWeight()));

        ValidationResults result = new ValidationResults();

        for(ValidationResults vr : resultList){
            if(!vr.isSuccess()){
                return vr;
            }
        }

        return result;
    }

    private ValidationResults validateProduct(Product p){
        SKUValidator skuv = new SKUValidator();
        NotNullValidator nnv = new NotNullValidator();

        List<ValidationResults> resultList = new ArrayList<>();

        resultList.add(skuv.validate(p.getSKU()));
        resultList.add(nnv.validate(p.getCategory()));
        resultList.add(validateItem(p));

        ValidationResults result = new ValidationResults();

        for(ValidationResults vr : resultList){
            if(!vr.isSuccess()){
                return vr;
            }
        }

        return result;
    }

    private ValidationResults validatePart(Part p){
        IDValidator idv = new IDValidator();
        NotNullValidator nnv = new NotNullValidator();

        List<ValidationResults> resultList = new ArrayList<>();

        resultList.add(idv.validate(p.getId()));
        resultList.add(nnv.validate(p.getCategory()));
        resultList.add(validateItem(p));

        ValidationResults result = new ValidationResults();

        for(ValidationResults vr : resultList){
            if(!vr.isSuccess()){
                return vr;
            }
        }

        return result;
    }

    public static InventoryManager getStaticManager() {
        return staticManager;
    }

    public static InventoryManager getTestManager() {
        return testManager;
    }
}
