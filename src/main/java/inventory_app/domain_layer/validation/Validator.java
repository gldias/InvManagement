package inventory_app.domain_layer.validation;

public interface Validator {

    /**
     * Determines whether a given Object is valid based on input criteria
     *
     * @return
     */
    public ValidationResults validate(Object o);
}
