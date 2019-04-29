package RandomGenerator.secondTask;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Binomial extends Distribution {
    private ArrayList<Double> array;
    private ArrayList<Integer> res = new ArrayList<>();
    private double p1;
    private double p2;
    private int n;

    public void init() {
        p1 = parameters.get(0);
        p2 = parameters.get(1);
        array = inputArray;
        n = array.size();
    }

    private BigInteger fact(int n) {
        init();
        if (n == 0 || n == 1)
            return BigInteger.ONE;
        BigInteger N = BigInteger.ONE;

        for (long i = 2; i <= n; i++) {
            N = N.multiply(new BigInteger(String.valueOf(i)));
        }
        return N;
    }

    private double f(int j) {
        double p = p1;
        double q = 1.0 - p;
        double ans = 0;
        BigInteger N = fact((int) p2);

        for (int i = 0; i < j + 1; i++)
            ans += Double.parseDouble(String.valueOf(N.divide(fact((int) p2 - i).multiply(fact(i))))) * Math.pow(p, i) * Math.pow(q, p2 - i);
        return ans;
    }


    public void toDistribution() throws IOException {
        init();
        FileWriter fw = new FileWriter("output.txt");
        double max = Integer.MIN_VALUE;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) > max)
                max = array.get(i);
        }
        max++;

        for (int i = 0; i < array.size(); i++) {
            int j = 0;
            for (; j < p2; j++) {
                if (array.get(i) / max <= f(j)) {
                    break;
                }
            }
            res.add(j);
        }
        for (Integer i : res) {
            fw.write(String.valueOf(i) + " ");
        }
        fw.close();
    }
}
