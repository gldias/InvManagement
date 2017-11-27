package inventory_app.domain_layer.validation;

import inventory_app.domain_layer.Item;

public class ValidationResults {

    private final boolean success;
    private final String errorMessage;
    private Object validatedObject;

    public ValidationResults(){
        success = true;
        errorMessage = "";
        validatedObject = null;
    }

    public ValidationResults(String _errorMessage){
        success = false;
        errorMessage = _errorMessage;
        validatedObject = null;

    }

    public boolean isSuccess(){
        return success;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public Object getValidatedObject(){return validatedObject;}

    public void setValidatedObject(Object _validatedObject){
        validatedObject = _validatedObject;
    }

    public String identifier(){
        return "";
    }



}
