import java.util.*;
import java.util.function.Function;

public class Test {

    private final int dimension;
    private final double alpha;
    private final double theta;
    private final int testingSize;
    private  final int epochs;

    public Test(int dimension, double alpha, double theta, int testingSize, int epochs) {
        this.dimension = dimension;
        this.alpha = alpha;
        this.theta = theta;
        this.testingSize = testingSize;
        this.epochs = epochs;
    }

    public void oneAgainstTheRest(
        Kl chosenClass,
        Function<Integer, Map<Kl, Data>> readingFunction
    ) {
        Map<Kl, Data> inputData = readingFunction.apply(this.testingSize);
        List<DataPoint> trainingData = new ArrayList<>();
        List<DataPoint> testingData = new ArrayList<>();
        List<Integer> trainingAnswers = new ArrayList<>();
        List<Integer> testingAnswers = new ArrayList<>();

        for(Kl kl : Kl.irisTypes) {
            trainingData.addAll(inputData.get(kl).getTraining());
            testingData.addAll(inputData.get(kl).getTesting());
        }

        trainingData.forEach(point -> trainingAnswers.add(point.dataClass == chosenClass ? 1 : 0));
        testingData.forEach(point -> testingAnswers.add(point.dataClass == chosenClass ? 1 : 0));

        Perceptron perceptron = new Perceptron(this.dimension, this.alpha, this.theta);

        System.out.println("Chosen Class: " + chosenClass);
        System.out.println("Weight vector before training:");
        System.out.println(Arrays.toString(perceptron.getWeights()));

        perceptron.learn(trainingData, trainingAnswers, this.epochs);

        System.out.println("Weight vector after training:");
        System.out.println(Arrays.toString(perceptron.getWeights()) + '\n');
        double result = perceptron.test(testingData, testingAnswers, chosenClass);

        System.out.printf("Accuracy: %.2f%%\n", 100 * result);
    }

    public void separateAll(Function<Integer, Map<Kl, Data>> readingFunction) {
        Map<Kl, Data> inputData = readingFunction.apply(this.testingSize);
        List<DataPoint> allTrainingData = new ArrayList<>();
        Map<Kl, List<DataPoint>> allTestingData = new HashMap<>();

        for(Kl kl : Kl.irisTypes) {
            allTrainingData.addAll(inputData.get(kl).getTraining());
            allTestingData.put(kl, inputData.get(kl).getTesting());
        }

        PerceptronPairs perceptronPairs = new PerceptronPairs(this.dimension, this.alpha, this.theta);

        System.out.print("Weight vector before training: [SETOSA/VERSICOLOR]: ");
        System.out.println(Arrays.toString(perceptronPairs.getSetosaVersicolor().getWeights()));

        System.out.print("Weight vector before training: [SETOSA/VIRGINICA]: ");
        System.out.println(Arrays.toString(perceptronPairs.getSetosaVirginica().getWeights()));

        System.out.print("Weight vector before training: [VIRGINICA/VERSICOLOR]: ");
        System.out.println(Arrays.toString(perceptronPairs.getVirginicaVersicolor().getWeights()));

        perceptronPairs.train(allTrainingData, this.epochs);

        System.out.println("==================================================");

        System.out.print("Weight vector after training: [SETOSA/VERSICOLOR]: ");
        System.out.println(Arrays.toString(perceptronPairs.getSetosaVersicolor().getWeights()));

        System.out.print("Weight vector after training: [SETOSA/VIRGINICA]: ");
        System.out.println(Arrays.toString(perceptronPairs.getSetosaVirginica().getWeights()));

        System.out.print("Weight vector after training: [VIRGINICA/VERSICOLOR]: ");
        System.out.println(Arrays.toString(perceptronPairs.getVirginicaVersicolor().getWeights()));

        System.out.println();
        double result = perceptronPairs.test(allTestingData);
        System.out.printf("Accuracy: %.2f%%\n", 100 * result);
    }

}
