package inventory_app.data_mappers;

/**
 * Interface for mappers. Includes insert update and delete.
 * Returns a boolean based on whether operation was successful
 */
public interface mapperInterface {
    public boolean insert(Object o);
    public boolean update(Object o);
    public boolean delete(Object o);
}
