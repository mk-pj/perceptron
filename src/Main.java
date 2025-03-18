import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        try {
            int testingSetSize = 10;
            ReadData readData = new ReadData(new Scanner(new File("iris.txt")));
            Test test = new Test(4, 0.01, 1.2, testingSetSize, 80);

            test.oneAgainstTheRest(Kl.SETOSA, readData::readDataRandom);
            System.out.println("\n================================================\n");

            readData.setSc(new Scanner(new File("iris.txt")));
            test.oneAgainstTheRest(Kl.VERSICOLOR, readData::readDataRandom);
            System.out.println("\n================================================\n");

            readData.setSc(new Scanner(new File("iris.txt")));
            test.oneAgainstTheRest(Kl.VIRGINICA, readData::readDataRandom);

            readData.setSc(new Scanner(new File("iris.txt")));
            System.out.println("\n================================================");
            System.out.println("================================================");
            System.out.println("================================================\n");
            System.out.println("Separate all classes:");
            test.separateAll(readData::readDataRandom);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }
}
