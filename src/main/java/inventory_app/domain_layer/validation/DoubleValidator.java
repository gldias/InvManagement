package inventory_app.domain_layer.validation;

public abstract class DoubleValidator implements Validator {

    public abstract ValidationResults validate(double input);

    @Override
    public ValidationResults validate(Object o) {
        return new ValidationResults(String.format("%s is not a Double",o.toString()));
    }
}
