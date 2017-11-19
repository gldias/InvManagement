package inventory_app.domain_layer.validation;

public class ValidationResults {

    private final boolean valid;
    private final String errorMessage;

    public ValidationResults(){
        valid = true;
        errorMessage = "";
    }

    public ValidationResults(String _errorMessage){
        valid = false;
        errorMessage = _errorMessage;

    }

    public boolean isValid(){
        return valid;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public String identifier(){
        return "";
    }



}
