package ReflectionImp.interfaces;

public class InvokeErrorException extends ReflectiveOperationException {
    public InvokeErrorException(){
        super();
    }

    public InvokeErrorException(String s){
        super(s);
    }
}
