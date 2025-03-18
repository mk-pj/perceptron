import java.util.ArrayList;
import java.util.List;

public class Data {

    private final List<DataPoint> training = new ArrayList<>();
    private final List<DataPoint> testing = new ArrayList<>();

    public List<DataPoint> getTraining() {
        return training;
    }

    public List<DataPoint> getTesting() {
        return testing;
    }

    public void addTraining(DataPoint dp) {
        training.add(dp);
    }

    public void addTesting(DataPoint dp) {
        testing.add(dp);
    }

    @Override
    public String toString() {
        return "\ntraining=" + training +
                ", \ntesting=" + testing +
                '}';
    }
}
