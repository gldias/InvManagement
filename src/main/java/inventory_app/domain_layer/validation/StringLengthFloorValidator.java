package inventory_app.domain_layer.validation;

/**
 * ensures a given string is no smaller than a given length
 */
public class StringLengthFloorValidator extends StringValidator {

    private int floor;

    public StringLengthFloorValidator(){
        floor = 0;
    }

    public StringLengthFloorValidator(int _floor){
        floor = _floor;
    }

    @Override
    public ValidationResults validate(String s) {

        if(s.length() >= floor)
            return new ValidationResults();
        else
            return new ValidationResults(String.format("%s is below length %d",s,floor));
    }
}
