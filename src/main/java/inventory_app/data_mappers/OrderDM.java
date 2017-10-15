package inventory_app.data_mappers;

/**
 * OrderDM is the bridge between storing orders in the domain and in the database.
 */
public class OrderDM implements mapperInterface {
    @Override
    public boolean insert(Object o) {
        return false;
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }
}
