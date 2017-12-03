package inventory_app.data_mappers;

import java.sql.*;

public abstract class InventoryDataMapper {
    protected Connection connect;
    protected Statement statement;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;

    protected boolean connectToDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://vm343a.se.rit.edu:3333/inventorymanagement?user=GroupA&password=SWEN343");
        } catch (ClassNotFoundException e) {
            System.out.println("JConnector error...");
            return false;
        } catch (SQLException e) {
            System.out.println("Connection error...");
            return false;
        }

        return true;
    }

    protected void close() {
        try {
            if (resultSet != null) {
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

    protected Connection getConnection(){
        return connect;
    }
}
