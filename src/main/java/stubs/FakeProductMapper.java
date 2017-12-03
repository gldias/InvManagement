package stubs;

import inventory_app.data_mappers.ProductDataMapper;
import inventory_app.domain_layer.Product;
import inventory_app.domain_layer.ProductCategory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FakeProductMapper extends ProductDataMapper {

    private Map<String,Product> fakeDb;

    public FakeProductMapper(){
        fakeDb = new HashMap<>();
    }

    @Override
    public boolean insert(Product product) {

        if(fakeDb.containsKey(product.getSKU())){
            return false;
        }

        fakeDb.put(product.getSKU(),product);
        return true;
    }

    @Override
    public boolean update(Product product) {

        if(!fakeDb.containsKey(product.getSKU())){
            return false;
        }

        fakeDb.put(product.getSKU(),product);
        return true;
    }

    @Override
    public boolean delete(Product product){

        if(!fakeDb.containsKey(product.getSKU())){
            return false;
        }

        fakeDb.remove(product.getSKU());
        return true;
    }

    @Override
    public Product findBySKU(String partId){

        if(!fakeDb.containsKey(partId)){
            return null;
        }

        return fakeDb.get(partId);
    }

    @Override
    public ArrayList<Product> getTable() {

        ArrayList<Product> output = new ArrayList<>();

        for(Product p : fakeDb.values()){
            output.add(p);
        }

        return output;
    }

    public Connection getConnection(){
        return null;
    }
}
