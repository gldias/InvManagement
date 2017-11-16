package inventory_app.domain_layer.validation;

public class ValidationResults {

    private final boolean valid;
    private final String errorMessage;

    public ValidationResults(boolean _valid, String _errorMessage){
        valid = _valid;
        errorMessage = _errorMessage;

    }

    public boolean isValid(){
        return valid;
    }

    public String getErrorMessage(){
        return errorMessage;
    }



}
