package inventory_app.data_mappers;

import inventory_app.domain_layer.Part;
import inventory_app.domain_layer.Product;

import java.sql.*;

/**
 * ItemDM is the bridge between storing items in the domain and in the database.
 */
public class ItemDM implements mapperInterface {
    private Connection connect;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @Override
    public boolean insert(Object o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;

        if (o instanceof Part) {
            Part part = (Part) o;
            //todo fix parts table
            line = "INSERT INTO parts VALUES(" + part.getId() +
                    ", " + part.getName() +
                    ", " + part.getQuantity() +
                    ", " + /*part.getPrice()*/100 +
                    ", " + part.getWeight() + ")";
        } else if (o instanceof Product) {
            Product product = (Product) o;
            //todo fix product table
            line = "INSERT INTO products VALUES(" + product.getSKU() +
                    ", " + product.getName() +
                    ", " + product.getQuantity() +
                    ", " + product.getCategory() +
                    ", " + product.getWeight() + ")";
        }

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insertion error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    @Override
    public boolean update(Object o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;

        if (o instanceof Part) {
            Part part = (Part) o;
            //todo fix parts table
            line = "UPDATE parts SET" +
                    " name = " + part.getName() +
                    ", quantity = " + part.getQuantity() +
                    ", buy_price = " + /*part.getPrice()*/100 +
                    ", weight = " + part.getWeight() +
                    " WHERE part_id = " + part.getId();
        } else if (o instanceof Product) {
            Product product = (Product) o;
            //todo fix product table
            line = "UPDATE products SET" +
                    " name = " + product.getName() +
                    ", quantity = " + product.getQuantity() +
                    ", class = " + product.getCategory() +
                    ", weight = " + product.getWeight() +
                    " WHERE part_id = " + product.getSKU();
        }

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    @Override
    public boolean delete(Object o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;

        if (o instanceof Part) {
            Part part = (Part) o;

            line = "DELETE FROM parts WHERE part_id = " + part.getId();
        } else if (o instanceof Product) {
            Product product = (Product) o;

            line = "DELETE FROM products WHERE product_id = " + product.getSKU();
        }

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Deletion error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    private boolean connectToDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://vm343a.se.rit.edu:3333/inventorymanagement?user=GroupA&password=SWEN343");
        } catch (ClassNotFoundException e){
            System.out.println("JConnector error...");
            return false;
        } catch (SQLException e){
            System.out.println("Connection error...");
            return false;
        }

        return true;
    }

    private void close() {
        try {
            if(resultSet != null){
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            System.exit(3);
        }

        System.out.println("Closed...");
    }
}
