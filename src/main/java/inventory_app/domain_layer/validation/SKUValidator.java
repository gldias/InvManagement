package inventory_app.domain_layer.validation;

public class SKUValidator extends StringValidator {

    @Override
    public ValidationResults validate(String input) {

        if(input.length() != 6){
            return new ValidationResults("Incorrect SKU length [6]");
        }

        char letter = input.charAt(0);
        if(!(letter == 'F' || letter == 'C' || letter == 'H' || letter == 'A')){
            return new ValidationResults("Incorrect SKU category [F,C,H,A]");
        }

        letter = input.charAt(input.length()-1);
        if(!(letter == 'N' || letter == 'R')){
            return new ValidationResults("Incorrect SKU end letter [N,R]");
        }

        StringBuilder sb = new StringBuilder(input);
        sb.deleteCharAt(0);
        sb.deleteCharAt(4);

        return (new IDValidator()).validate(sb.toString());
    }
}
