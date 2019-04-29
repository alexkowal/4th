package RandomGenerator.secondTask;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Distribution {

    ArrayList<Double> parameters = new ArrayList();
    ArrayList<Double> inputArray = new ArrayList();
    String filename = "";
    String inputFile = "";

    abstract void toDistribution() throws IOException;
}
