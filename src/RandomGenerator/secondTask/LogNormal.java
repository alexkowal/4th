package RandomGenerator.secondTask;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LogNormal extends Distribution {
    double p1;
    double p2;
    ArrayList<Double> array = new ArrayList<>();


    void init() {
        p1 = parameters.get(0);
        p2 = parameters.get(1);
        array = inputArray;
    }

    void toDistribution() throws IOException {
        double max = -Integer.MIN_VALUE;
        for (Double aDouble : array) {
            if (aDouble > max)
                max = aDouble;
        }
        max++;

        FileWriter fw = new FileWriter("output.txt");
        for (Double i : array) {
            fw.write(String.format(String.valueOf((p1 + Math.exp(p2 - i / max)))) + " ");
        }
        fw.close();
    }
}
