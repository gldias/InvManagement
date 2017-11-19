package inventory_app.domain_layer.validation;

public class IDValidator extends StringValidator {

    @Override
    public ValidationResults validate(String input) {
        if (input.length() != 4) {
            return new ValidationResults(String.format("%s is not 4 characters long",input));
        } else if(!input.matches("\\d+")){
            return new ValidationResults(String.format("%s is not numeric",input));
        } else {
            return new ValidationResults();
        }
    }
}
