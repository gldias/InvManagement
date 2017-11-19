package inventory_app.domain_layer.validation;

public class ValidationResults {

    private final boolean success;
    private final String errorMessage;

    public ValidationResults(){
        success = true;
        errorMessage = "";
    }

    public ValidationResults(String _errorMessage){
        success = false;
        errorMessage = _errorMessage;

    }

    public boolean isSuccess(){
        return success;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public String identifier(){
        return "";
    }



}
