package stubs;

import inventory_app.data_mappers.OrderDataMapper;
import inventory_app.domain_layer.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FakeOrderMapper extends OrderDataMapper {

    private Map<Integer,Order> fakeDb;

    public FakeOrderMapper(){
        fakeDb = new HashMap<>();
    }

    @Override
    public boolean insert(Order o) {
        if(fakeDb.containsKey(o.getId())){
            return false;
        }

        fakeDb.put(o.getId(),o);
        return true;
    }

    @Override
    public boolean update(Order o) {
        if(!fakeDb.containsKey(o.getId())){
            return false;
        }

        fakeDb.put(o.getId(),o);
        return true;
    }

    @Override
    public boolean delete(Order o) {
        if(!fakeDb.containsKey(o.getId())){
            return false;
        }

        fakeDb.remove(o.getId());
        return true;
    }


    @Override
    public Order findOrder(int orderId) {
        if(!fakeDb.containsKey(orderId)){
            return null;
        }

        return fakeDb.get(orderId);
    }

    @Override
    public ArrayList<Order> getTable(){
        ArrayList<Order> output = new ArrayList<>();

        for(Order o : fakeDb.values()){
            output.add(o);
        }

        return output;
    }
}
