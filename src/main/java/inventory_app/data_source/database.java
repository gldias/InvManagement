package inventory_app.data_source;

import com.sun.org.apache.xpath.internal.SourceTree;
import inventory_app.data_mappers.ItemDM;
import inventory_app.domain_layer.Part;
import inventory_app.domain_layer.PartCategory;

import java.sql.*;

public class database {

    private Connection connect;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public database() {
        connect = null;
        statement = null;
        preparedStatement = null;
        resultSet = null;
    }

    public static void main(String[] args) {
        Part p = new Part("1023", PartCategory.RAM, "Floppy", 45, 1.4);
        System.out.println(new ItemDM().insert(p));
        //(new database()).readDataBase();
    }

    public void readDataBase() /*throws Exception*/ {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Grabbed driver...");

            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://vm343a.se.rit.edu:3333/inventorymanagement?user=GroupA&password=SWEN343");
            System.out.println("Connected...");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();

            // Result set get the result of the SQL query
            resultSet = statement.executeQuery("SELECT * FROM parts");
            writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect.prepareStatement("INSERT INTO parts VALUES ('7341', 'The CPU', 225, 800, 2.3)");
/*
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
*/
            preparedStatement.executeUpdate();
            System.out.println("Insert PSU");
/*
            preparedStatement = connect.prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // Remove again the insert comment
            preparedStatement = connect.prepareStatement("delete from feedback.comments where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();
*/
            resultSet = statement.executeQuery("SELECT * FROM parts");
            writeMetaData(resultSet);
            System.out.println("Reprint...");
            writeResultSet(resultSet);

        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound error...");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("SQL error...");
            System.exit(2);
        } finally {
            close();
        }
    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        System.out.println("Write resultset...");
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String user = resultSet.getString("part_id");
            String website = resultSet.getString("name");
            int summary = resultSet.getInt("quantity");
            int date = resultSet.getInt("buy_price");
            int comment = resultSet.getInt("weight");
            System.out.print("ID: " + user);
            System.out.print(", Name: " + website);
            System.out.print(", Quantity: " + summary);
            System.out.print(", Price: " + date);
            System.out.println(", Weight: " + comment);
        }
    }

    private void close() {
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
}
