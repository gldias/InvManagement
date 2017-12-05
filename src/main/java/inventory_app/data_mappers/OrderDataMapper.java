package inventory_app.data_mappers;

import inventory_app.domain_layer.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * OrderDataMapper is the bridge between storing orders in the domain and in the Database.
 */
public class OrderDataMapper extends InventoryDataMapper {

    /**
     * Inserts an order into either the product_orders table or part_orders table
     * @param o The order to be inserted
     * @return True if operation was successful
     */
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
                        "(" + o.getId() +
                        ", '" + currentProduct.getSKU() + "'" +
                        ", " + o.getItems().values().toArray()[i] + ")";
            }

            else if (o.getItems().keySet().toArray()[i] instanceof Part) {
                Part currentPart = (Part)o.getItems().keySet().toArray()[i];
                line = "INSERT INTO part_orders VALUES" +
                        "(" + o.getId() +
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

    /**
     * Updates an existing order in the database
     * @param o The order to be updated
     * @return True if operation was successful
     */
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
                        "product_id = '" + currentProduct.getSKU() + "'" +
                        ", quantity = " + o.getItems().values().toArray()[i] +
                        " WHERE order_id = " + o.getId();
            } else if (o.getItems().keySet().toArray()[i] instanceof Part) {
                Part currentPart = (Part)o.getItems().keySet().toArray()[i];
                line = "UPDATE part_orders SET " +
                        "part_id = '" + currentPart.getId() + "'" +
                        ", quantity = " + o.getItems().values().toArray()[i] +
                        " WHERE order_id = " + o.getId();
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

    /**
     * Removes an order from the database
     * @param o The order to remove from the database
     * @return True if the operation was successful
     */
    public boolean delete(Order o) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = null;

        for (int i = 0; i < o.getItems().size(); i++) {
            if (o.getItems().keySet().toArray()[i] instanceof Product) {
                line = "DELETE FROM product_orders WHERE order_id = " + o.getId();
            } else if (o.getItems().keySet().toArray()[i] instanceof Part) {
                line = "DELETE FROM part_orders WHERE order_id = " + o.getId();
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
     */
    public Order findOrder(int orderId) {
        if (!connectToDB()) {
            close();
            return null;
        }

        Order toReturn = new Order(new HashMap<>(), "ND");
        HashMap<Item, Integer> hashMapToReturn = new HashMap<>();
        int id = 0;

        try {
            statement = connect.createStatement();
            String product_line = "SELECT * FROM product_orders" +
                    " WHERE order_id = " + orderId;

            String part_line = "SELECT * FROM part_orders" +
                    " WHERE order_id = " + orderId;

            resultSet = statement.executeQuery(product_line);
            while (resultSet.next()) {
                id = resultSet.getInt("order_id");
                String productId = resultSet.getString("product_id");
                int quantity = resultSet.getInt("quantity");

                Item item = InventoryManager.getStaticManager().getProduct(productId);
                hashMapToReturn.put(item, quantity);
            }

            resultSet = statement.executeQuery(part_line);
            while (resultSet.next()) {
                id = resultSet.getInt("order_id");
                String partId = resultSet.getString("part_id");
                int quantity = resultSet.getInt("quantity");

                Item item = InventoryManager.getStaticManager().getPart(partId);
                hashMapToReturn.put(item, quantity);
            }
            toReturn.setId(id);
            toReturn.setItems(hashMapToReturn);
        } catch(SQLException e) {
            System.out.println("OrderDataMapper findById error...");
            return null;
        } finally {
            close();
        }



        return toReturn;
    }

    public ArrayList<Order> getTable(){
        if (!connectToDB()) {
            close();
            return null;
        }

        Map<Integer,Order> orderMap = new HashMap<>();
        HashMap<Item, Integer> hashMapToReturn = new HashMap<>();
        int id = 0;

        try {

            statement = connect.createStatement();
            String product_line = "SELECT * FROM product_orders;";

            String part_line = "SELECT * FROM part_orders;";

            resultSet = statement.executeQuery(product_line);
            while (resultSet.next()) {
                id = resultSet.getInt("order_id");
                if(!orderMap.containsKey(id)){
                    orderMap.put(id,new Order(id));
                }

                String productId = resultSet.getString("product_id");
                Product product = InventoryManager.getStaticManager().getProduct(productId);

                int quantity = resultSet.getInt("quantity");

                orderMap.get(id).addItems(product,quantity);
            }

            resultSet = statement.executeQuery(part_line);
            while (resultSet.next()) {
                id = resultSet.getInt("order_id");
                if(!orderMap.containsKey(id)){
                    orderMap.put(id,new Order(id));
                }

                String partId = resultSet.getString("part_id");
                Part part = InventoryManager.getStaticManager().getPart(partId);

                int quantity = resultSet.getInt("quantity");

                orderMap.get(id).addItems(part,quantity);
            }

            ArrayList<Order> toReturn = new ArrayList<>();
            for(Order o : orderMap.values()){
                toReturn.add(o);
            }

            return toReturn;

        } catch(SQLException e) {
            System.out.println("OrderDataMapper findById error...");
        } finally {
            close();
        }



        return null;

    }
}