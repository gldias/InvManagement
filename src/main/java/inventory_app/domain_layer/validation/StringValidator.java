package inventory_app.domain_layer.validation;

public abstract class StringValidator implements Validator{

    public abstract ValidationResults validate(String input);

    @Override
    public ValidationResults validate(Object o) {
        return new ValidationResults(String.format("%s is not a String", o.toString()));
    }
}
