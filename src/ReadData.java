import java.util.Map;
import java.util.Scanner;

public class ReadData {

    private Scanner sc;

    public ReadData(Scanner sc) {
        this.sc = sc;
    }

    private DataPoint processLine(String line) {
        String[] col = line.split(",");
        double[] values = new double[col.length];
        for(int i = 0; i < values.length - 1; i++)
            values[i] = Double.parseDouble(col[i]);
        values[col.length - 1] = -1;
        return new DataPoint(values);
    }

    public Map<Kl, Data> readData(int numberOfTesting) {
        int rowCounter = 0;
        Data setosa = new Data();
        Data versicolot = new Data();
        Data virginica = new Data();
        int setosaCutOff = 50 - numberOfTesting;
        int versicolotCutOff = 100 - numberOfTesting;
        int virginicaCutOff = 150 - numberOfTesting;
        sc.nextLine();
        while(this.sc.hasNext()) {
            DataPoint point = processLine(sc.nextLine());
            if(rowCounter < 50) {
                point.dataClass = Kl.SETOSA;
                if(rowCounter < setosaCutOff) {
                    setosa.addTraining(point);
                } else {
                    setosa.addTesting(point);
                }
            } else if(rowCounter < 100) {
                point.dataClass = Kl.VERSICOLOR;
                if(rowCounter < versicolotCutOff) {
                    versicolot.addTraining(point);
                } else {
                    versicolot.addTesting(point);
                }
            } else {
                point.dataClass = Kl.VIRGINICA;
                if(rowCounter < virginicaCutOff) {
                    virginica.addTraining(point);
                } else {
                    virginica.addTesting(point);
                }
            }
            rowCounter++;
        }
        return Map.of(
                Kl.SETOSA, setosa,
                Kl.VERSICOLOR, versicolot,
                Kl.VIRGINICA, virginica
        );
    }

    private static int getRandomIndex(Kl kl) {
        return switch (kl) {
            case SETOSA -> (int)(Math.random() * 50);
            case VERSICOLOR -> (int)(50 * Math.random() + 50);
            case VIRGINICA -> (int)(50 * Math.random() + 100);
            case UNDETERMINED -> throw new IllegalArgumentException("Undetermined argument");
        };
    }

    public Map<Kl, Data> readDataRandom(int numberOfTesting) {
        Data setosa = new Data();
        Data versicolot = new Data();
        Data virginica = new Data();
        int[] dataIndices = new int[150];

        for(int i = 0; i < numberOfTesting; ++i) {
            int setosaTestIndex = getRandomIndex(Kl.SETOSA);
            int versicolotTestIndex = getRandomIndex(Kl.VERSICOLOR);
            int virginicaTestIndex = getRandomIndex(Kl.VIRGINICA);

            while (dataIndices[setosaTestIndex] == 1)
                setosaTestIndex = getRandomIndex(Kl.SETOSA);
            dataIndices[setosaTestIndex] = 1;

            while (dataIndices[versicolotTestIndex] == 1)
                versicolotTestIndex = getRandomIndex(Kl.VERSICOLOR);
            dataIndices[versicolotTestIndex] = 1;

            while (dataIndices[virginicaTestIndex] == 1)
                virginicaTestIndex = getRandomIndex(Kl.VIRGINICA);
            dataIndices[virginicaTestIndex] = 1;
        }
        sc.nextLine();
        int rowCounter = 0;

        while(this.sc.hasNext()) {
            DataPoint point = processLine(sc.nextLine());
            if(rowCounter < 50) {
                point.dataClass = Kl.SETOSA;
                if(dataIndices[rowCounter] == 0) {
                    setosa.addTraining(point);
                } else {
                    setosa.addTesting(point);
                }
            } else if(rowCounter < 100) {
                point.dataClass = Kl.VERSICOLOR;
                if(dataIndices[rowCounter] == 0) {
                    versicolot.addTraining(point);
                } else {
                    versicolot.addTesting(point);
                }
            } else {
                point.dataClass = Kl.VIRGINICA;
                if(dataIndices[rowCounter] == 0) {
                    virginica.addTraining(point);
                } else {
                    virginica.addTesting(point);
                }
            }
            rowCounter++;
        }
        return Map.of(
                Kl.SETOSA, setosa,
                Kl.VERSICOLOR, versicolot,
                Kl.VIRGINICA, virginica
        );
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

}
