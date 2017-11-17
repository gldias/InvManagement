package inventory_app.data_mappers;

import inventory_app.domain_layer.Part;
import inventory_app.domain_layer.Product;

import java.sql.*;

/**
 * ItemDataMapper is the bridge between storing items in the domain and in the database.
 */
public class ItemDataMapper implements mapperInterface {
    private Connection connect;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    /**
     * Takes an object, determines if it is a product or a part, and adds it to the appropriate table.
     * @param o Product or part
     * @return True if successful
     */
    @Override
    public boolean insert(Object o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;

        if (o instanceof Part) {
            Part part = (Part) o;
            line = "INSERT INTO parts VALUES('" + part.getId() +
                    "', '" + part.getName() +
                    "', " + part.getQuantity() +
                    ", " + part.getPrice() +
                    ", " + part.getWeight() + ")";
        } else if (o instanceof Product) {
            Product product = (Product) o;
            //todo fix product table
            line = "INSERT INTO products VALUES(" + product.getSKU() +
                    ", " + product.getName() +
                    ", " + product.getQuantity() +
                    ", " + product.getCategory() +
                    ", " + product.getRefurbished() +
                    ", " + product.getWeight() + ")";
        }

        try {
            preparedStatement = connect.prepareStatement(line);
        } catch (SQLException e) {
            System.out.println("ItemDM insertion error...prepareStatement");
            close();
            return false;
        }

        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ItemDM insertion error...executeUpdate");
            close();
            return false;
        }

        close();
        return true;
    }


    /**
     * Updates a row in the database
     * @param o The new version of the object being updated
     * @return True if successful
     */
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
                    ", " + /*product.getRefurbished()*/0 +
                    ", weight = " + product.getWeight() +
                    " WHERE part_id = " + product.getSKU();
        }

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ItemDM update error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Removes a row from the database
     * @param o
     * @return True if successful
     */
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
            System.out.println("ItemDM deletion error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Makes a connection with the database.  Has to be called before each operation
     * @return True if successful
     */
    private boolean connectToDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://vm343a.se.rit.edu:3333/inventorymanagement?user=GroupA&password=SWEN343");
        } catch (ClassNotFoundException e){
            System.out.println("ItemDM JConnector error...");
            return false;
        } catch (SQLException e){
            System.out.println("ItemDM connection error...");
            return false;
        }

        return true;
    }

    /**
     * Closes the connection with the database
     */
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
