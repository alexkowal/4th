package CryptoMethods.second;

import CryptoMethods.longAr;
import RandomGenerator.RSA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Generator {

    private static final longAr two = new longAr("2");
    private static final longAr one = new longAr("1");
    private static final longAr zero = new longAr("0");


    static int bits(longAr n) {
        int k = 0;
        while (!n.toString().equals("0")) {
            n = longAr.div(n, new longAr("2"));
            k++;
        }
        return k;
    }

    static longAr generateQ(int k) {

        longAr resN = zero;
        ArrayList<Boolean> bits = new ArrayList<>();
        bits.add(true);
        Random brand = new Random();
        for (int i = 0; i < k - 2; i++) {
            bits.add(brand.nextBoolean());
        }
        bits.add(true);

        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i)) {
                resN = resN.add(longAr.pow(two, i));
            }
        }
        System.out.println("temp q start value = " + resN);

        while (true) {
            if (!longAr.mod(resN, two).toString().equals("0")) {
                if (MillerRabinTest(resN, 5)) {
                    return new longAr(resN.toString());

                } else resN = resN.add(one);
            } else
                resN = resN.add(one);
            System.out.println("tmp q = " + resN);
        }
    }

    static longAr generate(int k) {

        while (true) {

            longAr q = generateQ(k / 2);
            int sz = k - k / 2;

            int counter = 0;
            for (longAr s = new longAr(longAr.pow(two, sz).toString());
                 s.compareTo(new longAr(longAr.pow(two, sz + 1).toString())) == -1
                         || s.toString().equals(longAr.pow(two, sz + 1).toString());
                 s = new longAr(s.add(two).toString())) {

                counter++;
                System.out.println(counter);
                if (counter >= 100) {
                    System.out.println("Inpossible to find S parameter. New Q value will be generated");
                    break;
                }

                longAr p = new longAr(new longAr(q.mul(s).toString()).add(one).toString());

                //(2q+1)^2
                longAr temp = new longAr(longAr.pow(new longAr(new longAr(two.mul(q).toString()).add(one).toString()), 2).toString());
                //2^s mod p
                longAr t = longAr.modPow(two, s, p);
                int c = bits(p);
                if (c > k) break;
                if (c == k && p.compareTo(temp) == -1 &&
                        longAr.modPow(two, new longAr(q.mul(s).toString()), p).toString().equals("1") &&
                        !t.toString().equals("1")) {

                    System.out.println("q: " + q.toString());
                    System.out.println("s: " + s.toString());
                    System.out.println("q * s = " + s.mul(q).toString());

                    System.out.println("p: " + p);

                    return p;
                }
            }
        }
    }

    static boolean MillerRabinTest(longAr n, int k) {

        if (n.toString().equals("2") || n.toString().equals("3"))
            return true;

        if (n.compareTo(new longAr("2")) == -1 || longAr.mod(n, new longAr("2")).toString().equals("0"))
            return false;

        longAr t = new longAr(n.sub(new longAr("1")).toString());

        long s = 0;

        while (longAr.mod(t, new longAr("2")).toString().equals("0")) {
            t = new longAr(longAr.div(t, new longAr("2")).toString());
            s++;
        }

        for (int i = 0; i < k; i++) {
            byte[] _a = new byte[bits(n)];

            longAr a;

            do {
                Random rng = new Random();
                for (int j = 0; j < _a.length; j++)
                    _a[j] = rng.nextBoolean() ? (byte) 1 : (byte) 0;

                a = new longAr("0");

                for (int j = 0; j < _a.length; j++) {
                    if (_a[j] == 1) {
                        a = a.add(longAr.pow(new longAr("2"), j));
                    }
                }
            }

            while (a.compareTo(new longAr("2")) == -1 || (a.compareTo(new longAr(n.sub(new longAr("2")).toString())) == 1 || a.toString().equals(n.sub(new longAr("2")).toString())));

            longAr x = new longAr(longAr.modPow(a, t, n).toString());

            if (x.toString().equals("1") || x.toString().equals(n.sub(new longAr("1")).toString()))
                continue;

            for (int r = 1; r < s; r++) {
                x = new longAr(longAr.modPow(x, new longAr("2"), n).toString());

                if (x.toString().equals("1"))
                    return false;

                if (x.toString().equals(n.sub(new longAr("1")).toString()))
                    break;
            }

            if (!x.toString().equals(n.sub(new longAr("1")).toString()))
                return false;
        }

        return true;
    }
}