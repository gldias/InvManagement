package inventory_app.data_mappers;

import java.sql.SQLException;

public class DatabaseLocking extends InventoryDataMapper{

    /**
     * Add a lock to the database for a part.
     * @param id - a part id
     * @return - true if added properly
     */
    public synchronized boolean lockPart(String id){
        if(!connectToDB()){
            close();
            return false;
        }

        String line = "INSERT INTO id_locks VALUES" +
                "'" + id + "'";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Part locking error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Add a lock to the database for a product.
     * @param SKU - a product SKU
     * @return - true if added properly
     */
    public synchronized boolean lockProduct(String SKU){
        if(!connectToDB()){
            close();
            return false;
        }

        String line = "INSERT INTO id_locks VALUES" +
                "'" + SKU + "'";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Product locking error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Remove a lock from the database for a part.
     * @param id - a part id
     * @return - true if removed properly
     */
    public synchronized boolean unlockPart(String id){
        if(!connectToDB()){
            close();
            return false;
        }

        String line = "DELETE FROM id_locks WHERE" +
                "'" + id + "'";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Part unlocking error...");
            return false;
        } finally {
            close();
        }

        return true;
    }

    /**
     * Remove a lock from the database for a product.
     * @param SKU - a product SKU
     * @return - true if removed properly
     */
    public synchronized boolean unlockProduct(String SKU){
        if(!connectToDB()){
            close();
            return false;
        }

        String line = "DELETE FROM id_locks WHERE id_lock = " +
                "'" + SKU + "'";

        try {
            preparedStatement = connect.prepareStatement(line);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println("Product unlocking error...");
            return false;
        } finally {
            close();
        }

        return true;
    }
}
