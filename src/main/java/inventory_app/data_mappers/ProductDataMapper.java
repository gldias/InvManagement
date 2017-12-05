package inventory_app.data_mappers;

import inventory_app.domain_layer.Product;
import inventory_app.domain_layer.ProductCategory;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The bridge between the product operations in the InventoryManager and the database
 */
public class ProductDataMapper extends InventoryDataMapper {

    /**
     * Inserts a product into the database products table
     * @param product The product to be inserted
     * @return True if operation was successful
     */
    public boolean insert(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "INSERT INTO products VALUES" +
                "('" + product.getSKU() + "'" +
                ", '" + product.getName() + "'" +
                ", " + product.getQuantity() +
                ", " + product.getWeight() + ")";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ProductDataMapper insertion error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Updates a product already in the database
     * @param product The new info for the product to be updated
     * @return True if operation is successful
     */
    public boolean update(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "UPDATE products SET" +
                " name = '" + product.getName() + "'" +
                ", quantity = " + product.getQuantity() +
                ", weight = " + product.getWeight() +
                " WHERE product_id = '" + product.getSKU() + "'";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ProductDataMapper update error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Deletes a product from the database
     * @param product The product to be deleted
     * @return True if operation was successful
     */
    public boolean delete(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "DELETE FROM products WHERE product_id = '" + product.getSKU() + "'";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ProductDataMapper deletion error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Finds the product info for the productSKU and returns the product
     * @param productSKU The SKU of the product to be returned
     * @return The product specified by the SKU
     */
    public Product findBySKU(String productSKU) {
        Product toReturn = null;

        if(!connectToDB()) {
            close();
            return toReturn;
        }

        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM products WHERE product_id = '" +
                    productSKU + "'");
            resultSet.next();
            String id = resultSet.getString("product_id");
            String name = resultSet.getString("name");
            int quantity = resultSet.getInt("quantity");
            double weight = resultSet.getDouble("weight");

            toReturn = new Product(name, ProductCategory.ACTIVE, id, weight, quantity);
        } catch(SQLException e) {
            System.out.println("ProductDataMapper findBySKU error...");
        } finally {
            close();
        }

        return toReturn;
    }

    /**
     * Returns the whole product table
     * @return The table of products.
     */
    public ArrayList<Product> getTable() {
        ArrayList<Product> toReturn = new ArrayList<>();

        if (!connectToDB()) {
            close();
            return toReturn;
        }

        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM products");
            while(resultSet.next()){
                String id = resultSet.getString("product_id");
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                double weight = resultSet.getDouble("weight");

                toReturn.add(new Product(name, ProductCategory.ACTIVE, id, weight, quantity));
            }
        } catch(SQLException e) {
            System.out.println("PartDataMapper getTable error...");
            toReturn = new ArrayList<>();
            toReturn.add(new Product("DNE", ProductCategory.FASHION, "F0000N", 0.0, 0));
            return toReturn;
        } finally {
            close();
        }

        return toReturn;
    }
}
