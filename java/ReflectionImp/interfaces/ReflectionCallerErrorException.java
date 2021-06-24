package ReflectionImp.interfaces;

public class ReflectionCallerErrorException extends ReflectiveOperationException{
    public ReflectionCallerErrorException(){
        super();
    }

    public ReflectionCallerErrorException(String s){
        super(s);
    }

}
