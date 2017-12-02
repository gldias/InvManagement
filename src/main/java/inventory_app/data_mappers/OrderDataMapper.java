package inventory_app.data_mappers;

import inventory_app.domain_layer.*;

import java.sql.*;
import java.util.HashMap;

/**
 * OrderDataMapper is the bridge between storing orders in the domain and in the Database.
 */
public class OrderDataMapper extends InventoryDataMapper {

    public boolean insert(Order o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;

        //Determine what kind of order we are working with by viewing its items
        for (int i = 0; i < o.getItems().size(); i++) {
            if (o.getItems().keySet().toArray()[i] instanceof Product) {
                Product currentProduct = (Product)o.getItems().keySet().toArray()[i];
                line = "INSERT INTO product_orders VALUES" +
                        "('" + o.getId() + "'" +
                        ", '" + currentProduct.getSKU().substring(1,5) + "'" +
                        ", " + o.getItems().values().toArray()[i] + ")";
            }

            else if (o.getItems().keySet().toArray()[i] instanceof Part) {
                Part currentPart = (Part)o.getItems().keySet().toArray()[i];
                line = "INSERT INTO part_orders VALUES" +
                        "('" + o.getId() + "'" +
                        ", '" + currentPart.getId() + "'" +
                        ", " + o.getItems().values().toArray()[i] + ")";
            }

            try {
                System.out.println(line);
                preparedStatement = connect.prepareStatement(line);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("OrderMapper insertion error...");
                return false;
            }
        }


        close();
        return true;
    }

    public boolean update(Order o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;
        
        for (int i = 0; i < o.getItems().size(); i++) {
            if (o.getItems().keySet().toArray()[i] instanceof Product) {
                Product currentProduct = (Product)o.getItems().keySet().toArray()[i];
                line = "UPDATE product_orders SET " +
                        "product_id = '" + currentProduct.getSKU().substring(1,5) + "'" +
                        ", quantity = " + o.getItems().values().toArray()[i] +
                        " WHERE order_id = '" + o.getId() + "'";
            } else if (o.getItems().keySet().toArray()[i] instanceof Part) {
                Part currentPart = (Part)o.getItems().keySet().toArray()[i];
                line = "UPDATE part_orders SET " +
                        "part_id = '" + currentPart.getId() + "'" +
                        ", quantity = " + o.getItems().values().toArray()[i] +
                        " WHERE order_id = '" + o.getId() + "'";
            }
            try {
                preparedStatement = connect.prepareStatement(line);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("OrderMapper update error...");
                return false;
            }
        }

        close();
        return true;
    }

    public boolean delete(Order o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;

        for (int i = 0; i < o.getItems().size(); i++) {
            if (o.getItems().keySet().toArray()[i] instanceof Product) {
                line = "DELETE FROM product_orders WHERE order_id = '" + o.getId() + "'";
            } else if (o.getItems().keySet().toArray()[i] instanceof Part) {
                line = "DELETE FROM part_orders WHERE order_id = '" + o.getId() + "'";
            }
            try {
                preparedStatement = connect.prepareStatement(line);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("OrderMapper delete error...");
                return false;
            }
        }

        close();
        return true;
    }


    /**
     * Returns an order object read from the database using the given order Id
     * @param orderId The ID of the order to be read from the database
     * @return The order object read from the database
     * FIXME: (12/1/2017) Order object needs a hashmap, which needs items as its keys,
     * FIXME: which means parts and products will need to be pulled from the InventoryManager.
     * FIXME: Petition to make the hashmap use SKUs as its keys.
     */
    public Order findOrder(String orderId) {
        if (!connectToDB()) {
            close();
            return null;
        }

        Order toReturn = new Order(new HashMap<>(), "ND");
        HashMap<Item, Integer> hashMapToReturn = new HashMap<>();
        String id = "";

        try {
            statement = connect.createStatement();
            String product_line = "SELECT * FROM product_orders" +
                    " WHERE order_id = '" + orderId + "'";

            String part_line = "SELECT * FROM part_orders" +
                    " WHERE order_id = '" + orderId + "'";

            resultSet = statement.executeQuery(product_line);
            while (resultSet.next()) {
                id = resultSet.getString("order_id");
                String productId = resultSet.getString("product_id");
                int quantity = resultSet.getInt("quantity");

                Item item = InventoryManager.getStaticManager().getProduct(productId);
                hashMapToReturn.put(item, quantity);
            }

            resultSet = statement.executeQuery(part_line);
            while (resultSet.next()) {
                id = resultSet.getString("order_id");
                String partId = resultSet.getString("part_id");
                int quantity = resultSet.getInt("quantity");

                Item item = InventoryManager.getStaticManager().getPart(partId);
                hashMapToReturn.put(item, quantity);
            }
            toReturn.setId(id);
            toReturn.setItems(hashMapToReturn);
        } catch(SQLException e) {
            System.out.println("OrderDataMapper findById error...");
        } finally {
            close();
        }

        return null;
    }
}






















    /*
    private Connection connect;

    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    /**
     * Inserts an object into the Database
     * @param o Object to be inserted
     * @return True if operation is successful

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
                /* Print statements for debugging purposes
                System.out.println(order.getId());
                System.out.println(((Product)order.getItems().keySet().toArray()[0]).getSKU());
                System.out.println((int)order.getItems().values().toArray()[i]);

                line = "INSERT INTO product_orders ('order_id', 'product_id', 'quantity') VALUES(?, ?, ?)";
                try {
                    preparedStatement = connect.prepareStatement(line);
                    preparedStatement.setString(1, order.getId());
                    preparedStatement.setString(2, ((Product)order.getItems().keySet().toArray()[i]).getSKU());
                    preparedStatement.setInt(3, (int)order.getItems().values().toArray()[i]);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Insertion error...");
                    return false;
                }
            }

            else if (order.getItems().keySet().toArray()[i] instanceof Part) {
                /*Print statements for debugging purposes
                System.out.println(order.getId());
                System.out.println(((Part)order.getItems().keySet().toArray()[0]).getId());
                System.out.println((int)order.getItems().values().toArray()[i]);

                line = "INSERT INTO part_orders ('order_id', 'part_id', 'quantity') VALUES(?, ?, ?)";
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
     * Updates an object already in the database
     * @param o Object to be updated
     * @return True if operation is successful

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

    public boolean find(String string) {
        return false;
    }


    /**
     * Makes a connection with the database.  Has to be called before each operation
     * @return True if successful

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


    /**
     * Closes the connection with the database

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
**/