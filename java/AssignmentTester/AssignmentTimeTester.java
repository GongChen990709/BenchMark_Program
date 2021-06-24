package AssignmentTester;

import JSONConverter.JSONClassConverter;
import JSONConverter.utils.JsonFileReader;
import MethodTester.MethodTester;
import ReflectionImp.interfaces.ReflectionCallerErrorException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, batchSize = 1000)
@State(Scope.Thread)
public class AssignmentTimeTester {
    @Param({"default"})
    public String dataType;
    @Param({"default"})
    public String data;
    @Param({"default"})
    public String classPath;
    @Param({"default"})
    public String className;

    public JSONClassConverter testClass;
    public Map<String, List<List<Object>>> testData;
    public Map<String, List<List<Class<?>>>> testDataType;
    public String constructorName;
    public List<String> testMethodName;

    @Setup
    public void assignmentTester(){
        JSONObject dataTypeObject = JSON.parseObject(dataType);
        this.testClass = new JSONClassConverter(dataTypeObject);

        JSONObject dataObject = JSON.parseObject(data);
        this.testData = testClass.batchTestDataLoader(dataObject);
        this.testDataType = testClass.batchTestMethodParametersTypeLoader(dataObject);
        this.constructorName = testClass.getConstructorName(dataObject);
        this.testMethodName = testClass.batchTestMethodName(dataObject);
    }

    @Benchmark
    public void classTest() throws ReflectionCallerErrorException {
        MethodTester classTester = new MethodTester(classPath, className, constructorName, testData, testDataType);
        classTester.testMethod(this.testMethodName);
    }

    public static void main(String[] args) throws RunnerException, ReflectionCallerErrorException {
        String dataType = JsonFileReader.readJsonFile(args[0]);
        String data = JsonFileReader.readJsonFile(args[1]);

        AssignmentResultTester assignmentResultTester = new AssignmentResultTester(dataType);
        assignmentResultTester.dataUpdate(data);
        String classPath = args[2];
        String className2 = args[3];

        Map<String, List<Object>> teacherResult = assignmentResultTester.classTest(classPath, className2);
        Map<String,List<Object>> studentResult = assignmentResultTester.classTest(classPath, className2);
        Map<String,List<Boolean>> judge =  assignmentResultTester.resultCompare(studentResult, teacherResult);
        System.out.println(judge);


        Options opt = new OptionsBuilder()
                .include(AssignmentTimeTester.class.getSimpleName())
                .forks(1)
                .param("dataType",dataType)
                .param("data",data)
                .param("classPath",classPath)
                .param("className",className2)
                .verbosity(VerboseMode.SILENT)
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();

    }

}
