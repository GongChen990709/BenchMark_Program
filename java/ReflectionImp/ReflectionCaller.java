package ReflectionImp;

import MethodTester.MethodCallerErrorException;
import ReflectionImp.interfaces.ReflectionCallerErrorException;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.LinkedList;
import java.util.List;
/**
 * ��һ�֣��ڹ��������Զ�����ʵ�巴���ࣨһ���޸���Ҫ���Եķ���������Ҫ�����µ�ʵ�巴����)
 * �ڶ��֣�����һ��С��������ʵ�巴����
 *
 * -----����ֱ���ø��ļ�ʵ�ֲ��ԣ����ֵ��õײ��е��鷳�������뵽�м��ٽ���һ�ι��ɣ�ʵ�ְ�װ
 *
 * ����ļ��Ȳ�����ֱ�Ӳ������뷽��������������װ�ײ�ķ��䣬��user-friendly
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
