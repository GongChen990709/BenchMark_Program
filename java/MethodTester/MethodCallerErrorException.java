package MethodTester;



public class MethodCallerErrorException extends ReflectiveOperationException {
    public MethodCallerErrorException() {
        super();
    }

    public MethodCallerErrorException(String s) {
        super(s);
    }
}
