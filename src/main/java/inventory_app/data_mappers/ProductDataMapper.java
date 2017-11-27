package inventory_app.data_mappers;

import inventory_app.domain_layer.Product;

import java.sql.SQLException;

public class ProductDataMapper extends InventoryDataMapper {
    public boolean insert(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "INSERT INTO products VALUES" +
                "('" + product.getSKU().substring(1, 5) + "'" +
                ", '" + product.getName() + "'";

        if(product.getSKU().charAt(5) == 'N') {
            line += ", " + product.getQuantity() +
                    ", " + 0;
        }
        else if(product.getSKU().charAt(5) == 'R') {
            line += ", " + 0 +
                    ", " + product.getQuantity();
        }

        line += ", '" + product.getSKU().charAt(0) + "'" +
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

    //TODO test
    public boolean update(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "UPDATE products SET" +
                " name = '" + product.getName() + "'";

        //if(product.getSKU().charAt(5) == 'N') {
            line += ", new_quantity = " + product.getQuantity();
        //}
        //else if(product.getSKU().charAt(5) == 'R') {
            line += ", refurb_quantity = " + product.getQuantity();
        //}

        line += ", category = '" + product.getSKU().charAt(0) + "'" +
                ", weight = " + product.getWeight() +
                " WHERE product_id = '" + product.getSKU().substring(1, 5) + "'";
        System.out.println(line);

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
