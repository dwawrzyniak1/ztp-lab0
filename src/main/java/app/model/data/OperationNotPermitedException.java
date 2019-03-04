package app.model.data;

public class OperationNotPermitedException extends Exception {

    public OperationNotPermitedException(String message){
        super(message);
    }
}
