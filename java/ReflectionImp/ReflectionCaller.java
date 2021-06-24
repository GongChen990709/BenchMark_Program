package ReflectionImp;

import MethodTester.MethodCallerErrorException;
import ReflectionImp.interfaces.ReflectionCallerErrorException;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.LinkedList;
import java.util.List;
/**
 * 第一种：在构造器中自动创建实体反射类（一旦修改需要测试的方法集，需要创建新的实体反射类)
 * 第二种：单独一个小方法创建实体反射类
 *
 * -----本想直接用该文件实现测试，发现调用底层有点麻烦，于是想到中间再进行一次过渡，实现包装
 *
 * 这个文件先不用来直接测试类与方法，而是用来包装底层的反射，更user-friendly
 */

@State(Scope.Thread)
public class ReflectionCaller {
    private ReflectionLoader loader;
    private Object reflectObj;

//    public ReflectionCaller(String className) throws ReflectionCallerErrorException {
//        this(className, new LinkedList<>());
//    }

    public ReflectionCaller(String className, List<Class<?>> parametersType, Object... parameters ) throws ReflectionCallerErrorException {
        try{
            this.loader = new ReflectionLoader(className);
            this.reflectObj = loader.instantiation(parametersType, parameters);
        } catch (ReflectiveOperationException e){
            throw new ReflectionCallerErrorException(e.getMessage());
        }
    }

    public ReflectionCaller(String classPath, String className, List<Class<?>> parametersType, Object... parameters ) throws ReflectionCallerErrorException {
        try{
            this.loader = new ReflectionLoader(classPath, className);
            this.reflectObj = loader.instantiation(parametersType, parameters);
        } catch (ReflectiveOperationException e){
            throw new ReflectionCallerErrorException(e.getMessage());
        }
    }

    public Object methodCaller(String singleMethod, List<Class<?>> parametersType, Object... parameters) throws MethodCallerErrorException {
        try {
            return loader.invoke(reflectObj,singleMethod, parametersType, parameters);
        }catch (ReflectiveOperationException e){
            throw new MethodCallerErrorException(e.getMessage());
        }
    }

}
