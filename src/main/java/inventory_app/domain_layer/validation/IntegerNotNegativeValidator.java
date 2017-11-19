package inventory_app.domain_layer.validation;

public class IntegerNotNegativeValidator extends IntegerValidator{
    @Override
    public ValidationResults validate(int input) {
        if(input >= 0){
            return new ValidationResults();
        } else{
            return new ValidationResults(String.format("%d is negative",input));
        }
    }
}
