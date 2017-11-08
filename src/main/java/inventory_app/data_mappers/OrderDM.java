package inventory_app.data_mappers;

import inventory_app.domain_layer.Order;
import inventory_app.domain_layer.Part;
import inventory_app.domain_layer.Product;

import java.sql.*;

/**
 * OrderDM is the bridge between storing orders in the domain and in the Database.
 */
public class OrderDM implements mapperInterface {
    private Connection connect;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    /**
     * Inserts an object into the Database
     * @param o Object to be inserted
     * @return True if operation is successful
     */
    @Override
    public boolean insert(Object o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;

        Order order = (Order) o;
        for (int i = 0; i < order.getItems().size(); i++) {
            if (order.getItems().keySet().toArray()[i] instanceof Product) {
                /*Print statements for debugging purposes
                System.out.println(order.getId());
                System.out.println(((Product)order.getItems().keySet().toArray()[0]).getSKU());
                System.out.println((int)order.getItems().values().toArray()[i]); */

                line = "INSERT INTO 'product_orders' ('order_id', 'product_id', 'quantity') VALUES(?, ?, ?)";
                try {
                    preparedStatement = connect.prepareStatement(line);
                    preparedStatement.setString(1, order.getId());
                    preparedStatement.setString(2, ((Product)order.getItems().keySet().toArray()[i]).getSKU());
                    preparedStatement.setInt(3, (int)order.getItems().values().toArray()[i]);
                    preparedStatement.executeUpdate(); //TODO: Find cause of SQL exception.  Probably due to test case product not corresponding to actual product in DB
                } catch (SQLException e) {
                    System.out.println("Insertion error...");
                    return false;
                }
            }

            else if (order.getItems().keySet().toArray()[i] instanceof Part) {
                /*Print statements for debugging purposes
                System.out.println(order.getId());
                System.out.println(((Part)order.getItems().keySet().toArray()[0]).getId());
                System.out.println((int)order.getItems().values().toArray()[i]); */

                line = "INSERT INTO 'part_orders' ('order_id', 'part_id', 'quantity') VALUES(?, ?, ?)";
                try {
                    preparedStatement = connect.prepareStatement(line);

                    preparedStatement.setString(1, order.getId());
                    preparedStatement.setString(2, ((Part)order.getItems().keySet().toArray()[i]).getId());
                    preparedStatement.setInt(3, (int)order.getItems().values().toArray()[i]);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Insertion error...");
                    return false;
                }
            }

        }
        close();

        return true;
    }


    /**
     * Updates an object already in the Database
     * @param o Object to be updated
     * @return True if operation is successful
     */
    @Override
    public boolean update(Object o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;

        Order order = (Order) o;

        for (int i = 0; i < order.getItems().size(); i++) {
            if (order.getItems().keySet().toArray()[i] instanceof Product) {
                line = "UPDATE product_orders SET" +
                        " quantity = " + order.getItems().values().toArray()[i] +
                        " WHERE order_id = " + order.getId() +
                        " AND product_id = " + ((Product) order.getItems().keySet().toArray()[i]).getSKU();
            }

            else if (order.getItems().keySet().toArray()[i] instanceof Part) {
                line = "UPDATE part_orders SET" +
                        " quantity = " + order.getItems().values().toArray()[i] +
                        " WHERE order_id = " + order.getId() +
                        " AND part_id = " + ((Part) order.getItems().keySet().toArray()[i]).getId();
            }


            try {
                preparedStatement = connect.prepareStatement(line);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Update error...");
                return false;
            }
        }
        close();

        return true;
    }

    /**
     * Removes an object from the Database
     * @param o Object to be removed
     * @return True if operation is successful
     */
    @Override
    public boolean delete(Object o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;
        Order order = (Order) o;
        if (order.getItems().keySet().toArray()[0] instanceof Product)
            line = "DELETE FROM product_orders WHERE order_id = " + order.getId();

        else if (order.getItems().keySet().toArray()[0] instanceof Part)
            line = "DELETE FROM part_orders WHERE order_id = " + order.getId();


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

    /**
     * Finds an object from the database
     * @param string The SKU or id of the product/part
     * @return True if operation is successful
     */
    public boolean find(String string) {
        return false;
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
