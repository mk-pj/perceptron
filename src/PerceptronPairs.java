import java.util.*;

public class PerceptronPairs {

    private final Perceptron setosaVersicolor;
    private final Perceptron setosaVirginica;
    private final Perceptron virginicaVersicolor;

    public PerceptronPairs(int dim, double alpha, double theta) {
        this.setosaVersicolor = new Perceptron(dim, alpha, theta);
        this.setosaVirginica = new Perceptron(dim, alpha, theta);
        this.virginicaVersicolor = new Perceptron(dim, alpha, theta);
    }

    public void train(List<DataPoint> input, int epochs) {
        List<DataPoint> setosaVersicolorData = new ArrayList<>();
        List<Integer> setosaVersicolorAnswers = new ArrayList<>();

        List<DataPoint> setosaVirginicaData = new ArrayList<>();
        List<Integer> setosaVirginicaAnswers = new ArrayList<>();

        List<DataPoint> virginicaVersicolorData = new ArrayList<>();
        List<Integer> virginicaVersicolorAnswers = new ArrayList<>();

        for (DataPoint dp : input) {
            if (dp.dataClass != Kl.SETOSA) {
                virginicaVersicolorData.add(dp);
                virginicaVersicolorAnswers.add(dp.dataClass == Kl.VIRGINICA ? 1 : 0);
            }
            if (dp.dataClass != Kl.VIRGINICA) {
                setosaVersicolorData.add(dp);
                setosaVersicolorAnswers.add(dp.dataClass == Kl.SETOSA ? 1 : 0);
            }
            if (dp.dataClass != Kl.VERSICOLOR) {
                setosaVirginicaData.add(dp);
                setosaVirginicaAnswers.add(dp.dataClass == Kl.SETOSA ? 1 : 0);
            }
        }

        this.setosaVersicolor.learn(setosaVersicolorData, setosaVersicolorAnswers, epochs);
        this.setosaVirginica.learn(setosaVirginicaData, setosaVirginicaAnswers, epochs);
        this.virginicaVersicolor.learn(virginicaVersicolorData, virginicaVersicolorAnswers, epochs);
    }

    public Kl classify(DataPoint dp) {
        int setosaCount = 0;
        int versicolorCount = 0;
        int virginicaCount = 0;

        if(this.setosaVersicolor.classify(dp) == 1)
            setosaCount++;
        else
            versicolorCount++;
        if(this.setosaVirginica.classify(dp) == 1)
            setosaCount++;
        else
            virginicaCount++;
        if(this.virginicaVersicolor.classify(dp) == 1)
            virginicaCount++;
        else
            versicolorCount++;
        return Map.of(
                Kl.SETOSA, setosaCount,
                Kl.VERSICOLOR, versicolorCount,
                Kl.VIRGINICA, virginicaCount
        ).entrySet()
         .stream()
         .max(Map.Entry.comparingByValue())
         .orElseThrow()
         .getKey();
    }

    public double test(Map<Kl, List<DataPoint>> testingData) {
        int size = 0;
        int correctPredictions = 0;
        StringBuilder sb = new StringBuilder();
        for (Kl kl : new Kl[]{Kl.SETOSA, Kl.VERSICOLOR, Kl.VIRGINICA}) {
            List<DataPoint> dataPoints = testingData.get(kl);
            for (DataPoint dp : dataPoints) {
                Kl prediction = classify(dp);

                if (prediction == kl)
                    ++correctPredictions;
                ++size;

                sb.append("prediction = [").append(prediction)
                  .append("] reality = [").append(kl).append("]");
                System.out.println(sb);
                sb.setLength(0);
            }
        }
        return correctPredictions / (double) size;
    }

    public Perceptron getSetosaVersicolor() {
        return setosaVersicolor;
    }

    public Perceptron getVirginicaVersicolor() {
        return virginicaVersicolor;
    }

    public Perceptron getSetosaVirginica() {
        return setosaVirginica;
    }
}
