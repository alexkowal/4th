package RandomGenerator;

import java.util.ArrayList;

public class Param5 {

    Integer p;
    Integer q1;
    Integer q2;
    Integer q3;
    Integer w;
    ArrayList<Integer> x = new ArrayList<Integer>();
    Integer n;


    public Param5(Integer p, Integer q1, Integer q2, Integer q3, Integer w, ArrayList<Integer> x, Integer n) {
        this.p = p;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.w = w;
        this.x = x;
        this.n = n;
    }

    String toBin(Integer x) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < w; i++)
            result.append('0');
        for (int i = 0; i < w; i++) {
            if (x % 2 == 0)
                result.setCharAt(i, '1');
            x /= 2;
        }
        return String.valueOf(result);
    }

    String addBin(String x1, String x2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < w; i++)
            result.append('0');
        for (int i = 0; i < w; i++) {
            result.setCharAt(i, '1');
            if (x1.charAt(i) == '1' && x2.charAt(i) == '1')
                result.setCharAt(i, '0');
            if (x1.charAt(i) == '0' && x2.charAt(i) == '0')
                result.setCharAt(i, '0');
        }
        return result.toString();
    }

    void generate() {
        for (int i = 0; i < n; i++) {
            String temp = toBin(x.get(i + q1));
            temp = addBin(temp, toBin(x.get(i + q2)));
            temp = addBin(temp, toBin(x.get(i + q3)));
            temp = addBin(temp, toBin(x.get(i)));
            System.out.println(temp);
        }
    }

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++)
            list.add(i + 7);
        Param5 p5 = new Param5(89, 20, 40, 69, 7, list, 10);
        p5.generate();
    }
}
