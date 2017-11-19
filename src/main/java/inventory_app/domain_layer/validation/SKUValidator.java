package inventory_app.domain_layer.validation;

public class SKUValidator extends StringValidator {

    @Override
    public ValidationResults validate(String input) {

        if(input.length() != 6){
            return new ValidationResults("SKU is not 6 characters long");
        }

        StringBuilder sb = new StringBuilder(input);
        sb.deleteCharAt(0);
        sb.deleteCharAt(4);

        IDValidator idv = new IDValidator();

        return idv.validate(sb.toString());
    }
}
