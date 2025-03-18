import java.util.*;
import java.util.function.Function;

public class Test {

    private final int dimension;
    private final double alpha;
    private final double theta;
    private final int testingSize;
    private  final int epochs;
    private final Kl[] classes;

    public Test(int dimension, double alpha, double theta, int testingSize, int epochs) {
        this.dimension = dimension;
        this.alpha = alpha;
        this.theta = theta;
        this.testingSize = testingSize;
        this.epochs = epochs;
        this.classes = new Kl[] { Kl.SETOSA, Kl.VERSICOLOR, Kl.VIRGINICA };
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

        for(Kl kl : this.classes) {
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

        System.out.printf("Accuracy: %.2f%%\n", result);
    }

    public void separateAll(Function<Integer, Map<Kl, Data>> readingFunction) {
        Map<Kl, Data> inputData = readingFunction.apply(this.testingSize);
        List<DataPoint> allTrainingData = new ArrayList<>();
        Map<Kl, List<DataPoint>> allTestingData = new HashMap<>();

        for(Kl kl : this.classes) {
            allTrainingData.addAll(inputData.get(kl).getTraining());
            allTestingData.put(kl, inputData.get(kl).getTesting());
        }

        PerceptronPairs perceptronPairs = new PerceptronPairs(this.dimension, this.alpha, this.theta);
        perceptronPairs.train(allTrainingData, this.epochs);

        double result = perceptronPairs.test(allTestingData);
        System.out.printf("Accuracy: %.2f%%\n", 100 * result);
    }


}
