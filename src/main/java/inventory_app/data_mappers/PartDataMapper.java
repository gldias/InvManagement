package inventory_app.data_mappers;
import inventory_app.domain_layer.Part;

import java.sql.*;

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

    //TODO figure out why quantity column doesn't allow numbers greater than 100
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
            System.out.println(preparedStatement.toString());
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

        String line = "DELETE FROM parts WHERE part_id = " + part.getId();

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
}
