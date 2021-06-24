package ReflectionImp.interfaces;

public class InstantiationErrorException extends ReflectiveOperationException {
    public InstantiationErrorException() {
        super();
    }
    public InstantiationErrorException(String s) {
        super(s);
    }
}
