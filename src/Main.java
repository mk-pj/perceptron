import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;


public class Main {

    private static Test test;
    private static ReadData readData;
    private static final int testSize = 10;

    public static void main(String[] args) {
        try {
            readData = new ReadData(new Scanner(new File("iris.txt")));
            test = new Test(4, 0.01, 1.2, testSize, 80);

            System.out.println("================== Last " + testSize + " test rows per class ====================\n");
            runTasks(readData::readData);

            System.out.println("\n================ Randomly picked " + testSize +  " test rows per class ====================");
            readData.setSc(new Scanner(new File("iris.txt")));
            runTasks(readData::readDataRandom);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    public static void runTasks(Function<Integer, Map<Kl, Data>> readingDataOperation) throws FileNotFoundException {
        test.oneAgainstTheRest(Kl.SETOSA, readingDataOperation);
        System.out.println("\n================================================\n");

        readData.setSc(new Scanner(new File("iris.txt")));
        test.oneAgainstTheRest(Kl.VERSICOLOR, readingDataOperation);
        System.out.println("\n================================================\n");

        readData.setSc(new Scanner(new File("iris.txt")));
        test.oneAgainstTheRest(Kl.VIRGINICA, readingDataOperation);

        readData.setSc(new Scanner(new File("iris.txt")));
        System.out.println("\n================================================");
        System.out.println("================================================");
        System.out.println("================================================\n");
        System.out.println("Separate all classes:");
        test.separateAll(readData::readDataRandom);
    }
}
