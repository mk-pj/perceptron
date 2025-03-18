import java.util.List;
import java.util.Random;

public class Perceptron {

    private final double alpha;
    private final double[] weights;

    public Perceptron(int dim, double alpha, double theta) {
        this.alpha = alpha;
        this.weights = new double[dim + 1];
        Random rand = new Random();
        for(int i = 0; i < dim; ++i)
            weights[i] = rand.nextDouble() - 0.5;
        weights[dim] = theta;
    }

    private int compute(double[] input) {
        double sum = 0.0;
        if(input.length != this.weights.length)
            throw new IllegalArgumentException("Input array length does not match weights array length");
        if(input[input.length - 1] != -1)
            throw new IllegalArgumentException("Last input value must be -1");
        for(int i = 0; i < this.weights.length; ++i)
            sum += input[i] * this.weights[i];
        return sum >= 0 ? 1 : 0;
    }

    public void learn(List<DataPoint> input, List<Integer> d, int epochs) {
        for(int epoch = 0; epoch < epochs; ++epoch) {
            for(int i = 0; i < input.size(); ++i) {
                double[] x = input.get(i).x;
                int y = compute(x);
                int diff = d.get(i) - y;
                for(int j = 0; j < weights.length; ++j)
                    weights[j] += diff * this.alpha * x[j];
            }
        }
    }

    public double test(List<DataPoint> x, List<Integer> answer) {
        int correctAnswersCount = 0;
        for(int i = 0; i < x.size(); ++i) {
            DataPoint dp = x.get(i);
            int prediction = compute(dp.x);
            if(prediction == answer.get(i))
                correctAnswersCount++;
            System.out.println("prediction: " + prediction + " answer: " + answer.get(i));
        }
        return correctAnswersCount / (double) answer.size();
    }

    public double test(List<DataPoint> testingData, List<Integer> answer, Kl targetClass) {
        int correctAnswersCount = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < testingData.size(); ++i) {
            DataPoint dp = testingData.get(i);
            int prediction = compute(dp.x);
            if(prediction == answer.get(i))
                correctAnswersCount++;

            sb.setLength(0);
            sb.append("prediction = ")
              .append(prediction == 1 ? "[" + targetClass + "]" : Kl.getOthers(targetClass))
              .append(" reality = [").append(dp.dataClass).append("]");
            System.out.println(sb);
        }
        return correctAnswersCount / (double) answer.size();
    }

    public int classify(DataPoint point) {
        return compute(point.x);
    }

    public double[] getWeights() {
        return weights;
    }

}
