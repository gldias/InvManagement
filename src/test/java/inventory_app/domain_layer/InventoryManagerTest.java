package inventory_app.domain_layer;

import inventory_app.domain_layer.validation.ValidationResults;
import org.junit.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class InventoryManagerTest extends ManagerTest{

    @Test
    public void testGetProductSuccess() throws Exception {
        Product product = manager.getProduct("B0001N");
        assertEquals("Necklace Thing", product.getName());
    }

    @Test
    public void testGetProductFailure() throws Exception {
        Product product = manager.getProduct("B0012N");
        assertEquals(null, product);
    }

    @Test
    public void testGetProducts() throws Exception {
        Set<String> receivedProductNames = new HashSet<>();
        for(Product p :manager.getProducts()){
            receivedProductNames.add(p.getName());
        }

        Set<String> expectedProductNames = new HashSet<>();

        expectedProductNames.add("Light Up Bracelet");
        expectedProductNames.add("Reversible Jacket");
        expectedProductNames.add("Fitness Bracelet");
        expectedProductNames.add("Loogle Glass");
        expectedProductNames.add("Necklace Thing");

        assertEquals(expectedProductNames,receivedProductNames);
    }

    @Test
    public void testUpdateProductSuccess() throws Exception {
        manager.updateProduct("Reversible Jacket",
                ProductCategory.COMFORT, "C0001N",10.0);
        Product product = manager.getProduct("C0001N");
        assertEquals("Reversible Jacket", product.getName());
        assertEquals(10.0,product.getWeight(),.1);
    }

    @Test
    public void testUpdateProductFailure() throws Exception {
        //Uses an unused SKU in update
        manager.updateProduct("Reversible Jacket",
                ProductCategory.COMFORT, "C0001R",10.0);
        Product product = manager.getProduct("C0001R");
        assertEquals(null, product);
    }

    @Test
    public void testDeleteProductSuccess() throws Exception {
        manager.deleteProduct("H0001N");
        Product product = manager.getProduct("H0001N");

        assertEquals(null, product);
    }

    @Test
    public void testDeleteProductFailure() throws Exception {
        //SKU does not exist in system
        manager.deleteProduct("H0123N");
        //ensures nothing got deleted
        int numberOfProducts = manager.getProducts().size();

        assertEquals(5,numberOfProducts);
    }

    @Test
    public void testGetPartSuccess() throws Exception {
        Part part = manager.getPart("0001");
        assertEquals("LCD Screen", part.getName());
    }

    @Test
    public void testGetPartFailure() throws Exception {
        Part part = manager.getPart("0012");
        assertEquals(null, part);
    }

    @Test
    public void testGetParts() throws Exception {
        Set<String> receivedPartNames = new HashSet<>();
        for(Part p :manager.getParts()){
            receivedPartNames.add(p.getName());
        }

        Set<String> expectedPartNames = new HashSet<>();

        expectedPartNames.add("LCD Screen");
        expectedPartNames.add("Type A Battery");
        expectedPartNames.add("Type B Battery");

        assertEquals(expectedPartNames, receivedPartNames);
    }

    @Test
    public void testUpdatePartSuccess() throws Exception {
        manager.updatePart("Type A Battery",
                PartCategory.BATTERY, "0002", 0.3);
        Part part = manager.getPart("0002");
        assertEquals("Type A Battery", part.getName());
        assertEquals(.3, part.getWeight(),.01);
    }

    @Test
    public void testUpdatePartFailure() throws Exception {
        manager.updatePart("Type A Battery",
                PartCategory.BATTERY, "0012", 0.3);
        Part part = manager.getPart("0012");
        assertEquals(null, part);
    }

    @Test
    public void testDeletePartSuccess() throws Exception {
        manager.deletePart("0003");
        Part part = manager.getPart("0003");

        assertEquals(null, part);
    }

    @Test
    public void testDeletePartFailure() throws Exception {
        manager.deletePart("0004");

        int numberOfParts = manager.getParts().size();

        assertEquals(3, numberOfParts);
    }

    @Test
    public void testAddProducts() throws Exception {
        manager.addProducts("F0001N",20);
        Product product = manager.getProduct("F0001N");

        assertEquals(20,product.getQuantity());
    }

    @Test
    public void testRemoveProducts() throws Exception {
        manager.addProducts("F0001N",20);
        manager.removeProducts("F0001N",5);

        Product product = manager.getProduct("F0001N");

        assertEquals(15,product.getQuantity());
    }

    @Test
    public void testAddParts() throws Exception {
        manager.addParts("0001",20);
        Part part = manager.getPart("0001");

        assertEquals(20,part.getQuantity());
    }

    @Test
    public void testRemoveParts() throws Exception {
        manager.addParts("0001",20);
        manager.removeProducts("0001",5);

        Part part = manager.getPart("0001");

        assertEquals(20,part.getQuantity());
    }

    @Test
    public void testCreateValidProduct(){
        ValidationResults vr = manager.createProduct("newProduct", ProductCategory.COMFORT, "C1234N", 1.0);
        assertTrue(vr.isSuccess());
        manager.deleteProduct("C1234N");
    }

    @Test
    public void testCreateNamelessProductInvalid(){
        ValidationResults vr = manager.createProduct("", ProductCategory.COMFORT, "C1234N", 1.0);
        assertFalse(vr.isSuccess());
        manager.deleteProduct("C1234N");
    }

    @Test
    public void testCreateProductLongIdInvalid(){
        ValidationResults vr = manager.createProduct("newProduct", ProductCategory.COMFORT, "C12345N", 1.0);
        assertFalse(vr.isSuccess());
        manager.deleteProduct("C12345N");
    }

    @Test
    public void testCreateProductShortIdInvalid(){
        ValidationResults vr = manager.createProduct("newProduct", ProductCategory.COMFORT, "C123N", 1.0);
        assertFalse(vr.isSuccess());
        manager.deleteProduct("C123N");
    }

    @Test
    public void testCreateProductNonNumericIdInvalid(){
        ValidationResults vr = manager.createProduct("newProduct", ProductCategory.COMFORT, "C12t4N", 1.0);
        assertFalse(vr.isSuccess());
        manager.deleteProduct("C12t4N");
    }

    @Test
    public void testCreateProductNullCategoryInvalid(){
        ValidationResults vr = manager.createProduct("newProduct", null, "C1234N", 1.0);
        assertFalse(vr.isSuccess());
        manager.deleteProduct("C1234N");
    }

    @Test
    public void testCreateProductNegativeWeightInvalid(){
        ValidationResults vr = manager.createProduct("newProduct", null, "C1234N", -.1);
        assertFalse(vr.isSuccess());
        manager.deleteProduct("C1234N");
    }

    @Test
    public void testUpdateProductValid(){
        ValidationResults vr = manager.updateProduct("newProduct", ProductCategory.COMFORT, "F0001N", 1.0);
        assertTrue(vr.isSuccess());
    }

    @Test
    public void testUpdateProductInvalid(){
        ValidationResults vr = manager.updateProduct("newProduct", ProductCategory.COMFORT, "C1234N", 1.0);
        assertFalse(vr.isSuccess());
    }

    @Test
    public void testDeleteProductValid(){
        ValidationResults vr = manager.deleteProduct("F0001N");
        assertTrue(vr.isSuccess());
    }

    @Test
    public void testDeleteProductInvalid(){
        ValidationResults vr = manager.deleteProduct("C1234N");
        assertFalse(vr.isSuccess());
    }

    @Test
    public void testAddProductsValid(){
        ValidationResults vr = manager.addProducts("F0001N",10);
        assertTrue(vr.isSuccess());
    }

    @Test
    public void testAddProductsInvalid(){
        ValidationResults vr = manager.addProducts("C1234N",10);
        assertFalse(vr.isSuccess());
    }

    @Test
    public void testRemoveProductsValid(){
        ValidationResults vr = manager.removeProducts("F0001N",1);
        assertTrue(vr.isSuccess());
    }

    @Test
    public void testRemoveProductsInvalid(){
        ValidationResults vr = manager.removeProducts("C1234N",1);
        assertFalse(vr.isSuccess());
    }

    @Test
    public void testCreateValidPart(){
        ValidationResults vr = manager.createPart("newPart", PartCategory.SCREEN, "1234", 1.0);
        assertTrue(vr.isSuccess());
        manager.deletePart("1234");
    }

    @Test
    public void testCreateNamelessPartInvalid(){
        ValidationResults vr = manager.createPart("", PartCategory.SCREEN, "1234", 1.0);
        assertFalse(vr.isSuccess());
        manager.deletePart("1234");
    }

    @Test
    public void testCreatePartLongIdInvalid(){
        ValidationResults vr = manager.createPart("newPart", PartCategory.SCREEN, "12345", 1.0);
        assertFalse(vr.isSuccess());
        manager.deletePart("12345");
    }

    @Test
    public void testCreatePartShortIdInvalid(){
        ValidationResults vr = manager.createPart("newPart", PartCategory.SCREEN, "123", 1.0);
        assertFalse(vr.isSuccess());
        manager.deletePart("123");
    }

    @Test
    public void testCreatePartNonNumericIdInvalid(){
        ValidationResults vr = manager.createPart("newPart", PartCategory.SCREEN, "12t4", 1.0);
        assertFalse(vr.isSuccess());
        manager.deletePart("12t4");
    }

    @Test
    public void testCreatePartNullCategoryInvalid(){
        ValidationResults vr = manager.createPart("newPart", null, "1234", 1.0);
        assertFalse(vr.isSuccess());
        manager.deletePart("1234");
    }

    @Test
    public void testCreatePartNegativeWeightInvalid(){
        ValidationResults vr = manager.createProduct("newPart", null, "1234", -.1);
        assertFalse(vr.isSuccess());
        manager.deletePart("1234");
    }

    @Test
    public void testUpdatePartValid(){
        ValidationResults vr = manager.updatePart("newPart", PartCategory.SCREEN, "0001", 1.0);
        assertTrue(vr.isSuccess());
    }

    @Test
    public void testUpdatePartInvalid(){
        ValidationResults vr = manager.updatePart("newPart", PartCategory.SCREEN, "1234", 1.0);
        assertFalse(vr.isSuccess());
    }

    @Test
    public void testDeletePartValid(){
        ValidationResults vr = manager.deletePart("0001");
        assertTrue(vr.isSuccess());
    }

    @Test
    public void testDeletePartInvalid(){
        ValidationResults vr = manager.deletePart("1234");
        assertFalse(vr.isSuccess());
    }

    @Test
    public void testAddPartsValid(){
        ValidationResults vr = manager.addParts("0001",10);
        assertTrue(vr.isSuccess());
    }

    @Test
    public void testAddPartsInvalid(){
        ValidationResults vr = manager.addParts("1234",10);
        assertFalse(vr.isSuccess());
    }

    @Test
    public void testRemovePartsValid(){
        ValidationResults vr = manager.removeParts("0001",1);
        assertTrue(vr.isSuccess());
    }

    @Test
    public void testRemovePartsInvalid(){
        ValidationResults vr = manager.removeParts("1234",1);
        assertFalse(vr.isSuccess());
    }

    @After
    public void tearDown(){
        manager.deleteProduct("F0001N");
        manager.deleteProduct("C0001N");
        manager.deleteProduct("H0001N");
        manager.deleteProduct("A0002N");
        manager.deleteProduct("B0001");

        manager.deletePart("0001");
        manager.deletePart("0002");
        manager.deletePart("0003");
    }

}