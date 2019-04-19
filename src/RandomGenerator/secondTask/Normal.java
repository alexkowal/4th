package RandomGenerator.secondTask;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Normal extends Distribution {
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
        for (int i = 0; i < array.size() - 1; i++) {
            fw.write(String.format(String.valueOf((p1 + p2 * Math.sqrt(-2 * Math.log(1 - array.get(i) / max)) * Math.cos(2 * Math.PI * array.get(i + 1) / max)))) + " ");
            fw.write(String.format(String.valueOf((p1 + p2 * Math.sqrt(-2 * Math.log(1 - array.get(i) / max)) * Math.sin(2 * Math.PI * array.get(i + 1) / max)))) + " ");
        }
        fw.close();
    }
}