package inventory_app.data_mappers;

import inventory_app.domain_layer.Part;
import inventory_app.domain_layer.Product;

import java.sql.SQLException;

public class ProductDataMapper extends InventoryDataMapper {
    //TODO test
    public boolean insert(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        //TODO fix to match table
        String line = "INSERT INTO products VALUES(" + product.getSKU() +
                ", " + product.getName() +
                ", " + product.getQuantity() +
                ", " + product.getCategory() +
                ", " + product.getRefurbished() +
                ", " + product.getWeight() + ")";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ProductDataMapper insertion error...");
            close();
            return false;
        } finally {
            close();
        }

        close();
        return true;
    }

    //TODO test
    public boolean update(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        //TODO fix to match table
        String line = "UPDATE products SET" +
                " name = " + product.getName() +
                ", quantity = " + product.getQuantity() +
                ", class = " + product.getCategory() +
                ", " + /*product.getRefurbished()*/0 +
                ", weight = " + product.getWeight() +
                " WHERE part_id = " + product.getSKU();

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

    //TODO test
    public boolean delete(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "DELETE FROM products WHERE product_id = " + product.getSKU();

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
}
