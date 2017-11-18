package inventory_app.data_mappers;
import inventory_app.domain_layer.Part;

import java.sql.*;
import java.util.ArrayList;

public class PartDataMapper extends InventoryDataMapper {
    public boolean insert(Part part) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "INSERT INTO parts VALUES('" + part.getId() +
                "', '" + part.getName() +
                "', " + part.getQuantity() +
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

    //TODO change DB trigger
    public boolean update(Part part) {
        if (!connectToDB()) {
            close();
            return false;
        }

        String line = "UPDATE parts SET" +
                " name = '" + part.getName() +
                "', quantity = " + part.getQuantity() +
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
            close();
        }

        return toReturn;
    }
}
