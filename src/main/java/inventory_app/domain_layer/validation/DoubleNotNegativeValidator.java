package inventory_app.domain_layer.validation;

public class DoubleNotNegativeValidator extends DoubleValidator{
    @Override
    public ValidationResults validate(double input) {
        if(input >= 0)
            return new ValidationResults();
        else
            return new ValidationResults(String.format("%f is negative",input));
    }
}
