package inventory_app.data_mappers;

import inventory_app.domain_layer.Product;
import inventory_app.domain_layer.ProductCategory;

import java.sql.SQLException;
import java.util.ArrayList;

//TODO ProductCategory necessary??
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

    public boolean update(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "UPDATE products SET" +
                " name = '" + product.getName() + "'";

        if(product.getSKU().charAt(5) == 'N') {
            line += ", new_quantity = " + product.getQuantity();
        }
        else if(product.getSKU().charAt(5) == 'R') {
            line += ", refurb_quantity = " + product.getQuantity();
        }

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

    public boolean delete(Product product) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "DELETE FROM products WHERE product_id = " + product.getSKU().substring(1, 5);

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

    public Product findBySKU(String productSKU) {
        Product toReturn = new Product("DNE", ProductCategory.FASHION, "F0000N", 0.0, 0);

        if(!connectToDB()) {
            close();
            return toReturn;
        }

        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM products WHERE product_id = '" +
                    productSKU.substring(1, 5) + "'");
            resultSet.next();
            String id = resultSet.getString("product_id");
            String name = resultSet.getString("name");
            int quantity = 0;

            if(productSKU.charAt(5) == 'N'){
                quantity = resultSet.getInt("new_quantity");
            }
            else if(productSKU.charAt(5) == 'R'){
                quantity = resultSet.getInt("refurb_quantity");
            }

            String category = resultSet.getString("category");
            double weight = resultSet.getDouble("weight");

            toReturn = new Product(name, ProductCategory.ACTIVE, category+ id +productSKU.charAt(5), weight, quantity);
        } catch(SQLException e) {
            System.out.println("ProductDataMapper findBySKU error...");
        } finally {
            close();
        }

        return toReturn;
    }

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
                int newQuantity = resultSet.getInt("new_quantity");
                int refurbQuantity = resultSet.getInt("refurb_quantity");
                String category = resultSet.getString("category");
                double weight = resultSet.getDouble("weight");

                toReturn.add(new Product(name, ProductCategory.ACTIVE, category + id + "N", weight, newQuantity));
                toReturn.add(new Product(name, ProductCategory.ACTIVE, category + id + "R", weight, refurbQuantity));
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
