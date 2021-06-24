package AssignmentTester;

import JSONConverter.JSONClassConverter;
import MethodTester.MethodTester;
import ReflectionImp.interfaces.ReflectionCallerErrorException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class AssignmentResultTester {
    private JSONClassConverter testClass;

    private Map<String, List<List<Object>>> testData;
    private Map<String, List<List<Class<?>>>> testDataType;
    private String constructorName;
    private List<String> testMethodName;


    public AssignmentResultTester(String dataType){
        JSONObject dataTypeObject = JSON.parseObject(dataType);
        this.testClass = new JSONClassConverter(dataTypeObject);
    }


    public void dataUpdate(String data){
        JSONObject dataObject = JSON.parseObject(data);
        this.testData = testClass.batchTestDataLoader(dataObject);
        this.testDataType = testClass.batchTestMethodParametersTypeLoader(dataObject);
        this.constructorName = testClass.getConstructorName(dataObject);
        this.testMethodName = testClass.batchTestMethodName(dataObject);
    }


    public Map<String,List<Object>> classTest(String classPath, String className) throws ReflectionCallerErrorException {
        MethodTester classTester = new MethodTester(classPath, className, constructorName, testData, testDataType);
        return classTester.testMethod(this.testMethodName);
    }

    public Map<String,List<Boolean>> resultCompare(Map<String, List<Object>> studentResult, Map<String, List<Object>> teacherResult){
        Map<String,List<Boolean>> compareResult = new HashMap<>(this.testMethodName.size());
        String methodName;
        for (int i = 0; i < this.testMethodName.size(); i++) {
            methodName = this.testMethodName.get(i);
            compareResult.put(methodName, resultCompare(studentResult.get(methodName),teacherResult.get(methodName)));
        }
        return compareResult;
    }

    private List<Boolean> resultCompare(List<Object> studentFunctionResult, List<Object> teacherFunctionResult){
        List<Boolean> functionCompareResult = new LinkedList<>();
        for (int i = 0; i < teacherFunctionResult.size(); i++) {
            functionCompareResult.add(teacherFunctionResult.get(i).equals(studentFunctionResult.get(i)));
        }
        return functionCompareResult;
    }

    public Map<String, List<List<Object>>> getTestData(){
        return this.testData;
    }

    public Map<String, List<List<Class<?>>>> getTestDataType(){
        return this.testDataType;
    }

    public List<String> getTestMethodName(){
        return this.testMethodName;
    }








//    public void test(String data, List<String> studentClass) throws ReflectionCallerErrorException {
//        JSONObject dataObject = JSON.parseObject(data);
//
//        Map<String, List<List<Object>>> testData = testClass.batchTestDataLoader(dataObject);
//        Map<String, List<Class<?>>> testDataType = testClass.batchTestMethodParametersTypeLoader(dataObject);
//        String constructorName = testClass.getConstructorName(dataObject);
//        List<String> testMethodName = testClass.batchTestMethodName(dataObject);
//
//
//        Map<String,List<Object>> teacherAnswer = answerBuilder(testClass.getClassName(), constructorName, testData, testDataType, testMethodName);
//        Map<String,Map<String,List<Object>>> allStudentsAnswer = new HashMap<>(studentClass.size());
//        for (int i = 0; i < studentClass.size(); i++) {
//            Map<String,List<Object>> studentAnswer = answerBuilder(studentClass.get(i), constructorName, testData, testDataType, testMethodName);
//            allStudentsAnswer.put(studentClass.get(i), studentAnswer);
//        }
//
//
//
//
//    }









}
