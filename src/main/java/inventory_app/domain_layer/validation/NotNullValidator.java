package inventory_app.domain_layer.validation;

public class NotNullValidator implements Validator{

    @Override
    public ValidationResults validate(Object o) {
        if(o == null){
            return new ValidationResults("input is null");
        } else{
            return new ValidationResults();
        }
    }
}
