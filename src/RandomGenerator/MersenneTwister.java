package RandomGenerator;

import RandomGenerator.Generator;

import java.io.FileWriter;
import java.util.ArrayList;

public class MersenneTwister extends Generator {

    private long r;
    private long a;
    private long u;
    private long s;
    private long t;
    private long l;
    private long b;
    private long c;
    private long w;

    private long q;
    private long cnt;
    private ArrayList<Long> I;
    private ArrayList<Long> res = new ArrayList<>();


    public void init() {
        w = parameters.get(0);
        r = parameters.get(1);
        q = parameters.get(2);
        a = parameters.get(3);
        u = parameters.get(4);
        s = parameters.get(5);
        t = parameters.get(6);
        l = parameters.get(7);
        b = parameters.get(8);
        c = parameters.get(9);
        I = (ArrayList<Long>) initVector;
        cnt = I.size();

    }

    public String generate() {
        init();
        long a = (long) Math.pow(2, r);
        long b = a - 1;
        ArrayList<Long> tmp = new ArrayList<>();

        for (int i = 0; i < I.size(); i++)
            tmp.add(I.get(i));
        for (int i = 0; i < n; i++) {
            long y = tmp.get(i) & a | (tmp.get(i + 1)) & b;
            long temp = 0;
            if (y % 2 == 0)
                temp = tmp.get(Math.toIntExact((i + q) % cnt)) ^ (y >> 1) ^ 0;
            else
                temp = tmp.get(Math.toIntExact((i + q) % cnt)) ^ (y >> 1) ^ a;
            y = temp;
            y = y ^ (y >> u);
            y = y ^ ((y << s) & b);
            y = y ^ ((y << t) & c);
            long z = y ^ (y >> l);
            tmp.add(z);
            out.append(z + " ");
        }
        return out.toString() + " ";
    }


    @Override
    public String help() {
        System.out.println("/g:mt /p:32 29 5 35471736145 13 8 9 17 26433923645 5122733259 /i:104 45 6 8 9 17 /n:10000 /f: random.txt\n ");
        System.out.println("Порядок ввода параметров w, r, q, a, u, s, t, l, b, c, q - число, 1 <= q <= p, где p - степень рекуррентности,\nw - число бит, r - число, 1 <= r <= w, a - w-битное число. Далее записывается инициализирующий вектор.\n ");
        return null;
    }
}