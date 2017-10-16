package inventory_app.domain_layer;

/**
 * This abstract class represents an item, which can either be a product or part.
 */
public abstract class Item {

    private String name;
    private double weight;
    private int quantity;

    public Item(){
        weight = 0;
        name = "item";
        quantity = 0;
    }

    public Item(String _name, double _weight){
        name = _name;
        weight = _weight;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int addQuantity(int _quantity){
        return (quantity += _quantity);
    }

    public int subtractQuantity(int _quantity){
        return (quantity -= _quantity);
    }










}
