package inventory_app.domain_layer;

public class Double<X,Y> {

    private final X x;

    private final Y y;

    public Double(X _x, Y _y){
        x = _x;
        y = _y;
    }

    public X getX(){
        return x;
    }

    public Y getY(){
        return y;
    }


}
