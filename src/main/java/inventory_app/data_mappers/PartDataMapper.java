package inventory_app.data_mappers;
import inventory_app.domain_layer.Part;

import java.sql.*;
import java.util.ArrayList;

//TODO PartCategory necessary??

/**
 * Bridge between the part operations in the InventoryManager and the database
 */
public class PartDataMapper extends InventoryDataMapper {

    /**
     * Inserts a part into the database parts table
     * @param part The part to be inserted
     * @return True if operation was successful
     */
    public boolean insert(Part part) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "INSERT INTO parts VALUES('" + part.getId() + "'" +
                ", '" + part.getName() + "'" +
                ", " + part.getQuantity() +
                ", " + part.getPrice() +
                ", " + part.getWeight() + ")";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("PartDataMapper insertion error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * The part in the database to be updated
     * @param part The part being updated with new info
     * @return True if operation was successful
     */
    public boolean update(Part part) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "UPDATE parts SET" +
                " name = '" + part.getName() + "'" +
                ", quantity = " + part.getQuantity() +
                ", buy_price = " + part.getPrice() +
                ", weight = " + part.getWeight() +
                " WHERE part_id = '" + part.getId() + "'";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("PartDataMapper update error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Removes a part from the database
     * @param part The part to be removed
     * @return True if the operation was successful
     */
    public boolean delete(Part part) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "DELETE FROM parts WHERE part_id = '" + part.getId() +"'";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("PartDataMapper deletion error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Finds a part in the database by the given ID
     * @param partId The ID of the part to be read
     * @return The part object read by the database
     */
    public Part findById(String partId){
        Part toReturn = new Part("0000", "DNE", 0, 0, 0.0);

        if (!connectToDB()) {
            close();
            return toReturn;
        }

        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM parts WHERE part_id = '" + partId + "'");
            resultSet.next();
            String id = resultSet.getString("part_id");
            String name = resultSet.getString("name");
            int quantity = resultSet.getInt("quantity");
            int price = resultSet.getInt("buy_price");
            double weight = resultSet.getDouble("weight");

            toReturn = new Part(id, name, quantity, price, weight);
        } catch(SQLException e) {
            System.out.println("PartDataMapper findById error...");
        } finally {
            close();
        }

        return toReturn;
    }

    public ArrayList<Part> getTable() {
        ArrayList<Part> toReturn = new ArrayList<>();

        if (!connectToDB()) {
            close();
            return toReturn;
        }

        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM parts");
            while(resultSet.next()){
                String id = resultSet.getString("part_id");
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                int price = resultSet.getInt("buy_price");
                double weight = resultSet.getDouble("weight");

                toReturn.add(new Part(id, name, quantity, price, weight));
            }
        } catch(SQLException e) {
            System.out.println("PartDataMapper getTable error...");
            toReturn = new ArrayList<>();
            toReturn.add(new Part("0000", "DNE", 0, 0, 0.0));
            return toReturn;
        } finally {
            close();
        }

        return toReturn;
    }
}
