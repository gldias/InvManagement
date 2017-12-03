package stubs;

import inventory_app.data_mappers.PartDataMapper;
import inventory_app.domain_layer.Part;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FakePartMapper extends PartDataMapper {

    private Map<String,Part> fakeDb;

    public FakePartMapper(){
        fakeDb = new HashMap<>();
    }

    @Override
    public boolean insert(Part part) {

        if(fakeDb.containsKey(part.getId())){
            return false;
        }

        fakeDb.put(part.getId(),part);
        return true;
    }

    @Override
    public boolean update(Part part) {

        if(!fakeDb.containsKey(part.getId())){
            return false;
        }

        fakeDb.put(part.getId(),part);
        return true;
    }

    @Override
    public boolean delete(Part part){

        if(!fakeDb.containsKey(part.getId())){
            return false;
        }

        fakeDb.remove(part.getId());
        return true;
    }

    @Override
    public Part findById(String partId){

        if(!fakeDb.containsKey(partId)){
            return null;
        }

        return fakeDb.get(partId);
    }

    @Override
    public ArrayList<Part> getTable() {

        ArrayList<Part> output = new ArrayList<>();

        for(Part p : fakeDb.values()){
            output.add(p);
        }

        return output;
    }

    public Connection getConnection(){
        return null;
    }
}
