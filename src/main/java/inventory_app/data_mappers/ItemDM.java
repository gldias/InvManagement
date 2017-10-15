package inventory_app.data_mappers;

/**
 * ItemDM is the bridge between storing items in the domain and in the database.
 */
public class ItemDM implements mapperInterface {
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
