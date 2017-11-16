package inventory_app.domain_layer;

/**
 * This abstract class represents an item, which can either be a product or part.
 */
public abstract class Item {

    private String name;
    private double weight;
    private int quantity;

    public Item() {
        weight = 0;
        name = "item";
        quantity = 0;
    }

    public Item(String _name, double _weight) {
        name = _name;
        weight = _weight;
        quantity = 0;
    }

    public Item(String _name, double _weight, int _quantity) {
        name = _name;
        weight = _weight;
        quantity = _quantity;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getQuantity() {
        return quantity;
    }

    //TODO delete after fixing classes that use this setter
    public void setName(String _name){
        name = _name;
    }

    //TODO delete after fixing classes that use this setter
    public void setWeight(double _weight){
        weight = _weight;
    }

    //TODO delete after fixing classes that use this setter
    public void setQuantity(int _quantity){
        quantity = _quantity;
    }

    public int addQuantity(int _quantity) {
        return (quantity += _quantity);
    }

    public int subtractQuantity(int _quantity) {
        return (quantity -= _quantity);
    }
}
