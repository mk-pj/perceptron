import java.util.Arrays;

public class DataPoint {

    public double[] x;
    public Kl dataClass;

    public DataPoint(double[] x) {
        this.x = x;
    }

    public DataPoint(double[] x, Kl dataClass) {
        this.x = x;
        this.dataClass = dataClass;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "x=" + Arrays.toString(x) +
                ", dataClass=" + dataClass +
                '}';
    }
}
