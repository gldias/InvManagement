package inventory_app.domain_layer.validation;

public abstract class IntegerValidator implements Validator{
    public abstract ValidationResults validate(int input);

    @Override
    public ValidationResults validate(Object o) {
        return new ValidationResults(String.format("%s is not an Integer",o.toString()));
    }
}
