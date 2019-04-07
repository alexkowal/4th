package RandomGenerator;

import java.math.BigInteger;

public class BBS extends Generator {

    public BBS() {
    }
    long w;
    BigInteger p, q, x = BigInteger.ZERO;
    BigInteger e = BigInteger.valueOf(2);
    BigInteger N = BigInteger.ZERO;

    void init() {
        this.p = BigInteger.valueOf(parameters.get(0));
        this.q = BigInteger.valueOf(parameters.get(1));
        this.x = BigInteger.valueOf(parameters.get(2));
        this.w = (parameters.get(3));
        N = p.multiply(q);
    }

    @Override
    public String generate() {
        init();
        for (int i = 0; i < n; i++) {
            long r = 0;
            for (int j = 0; j < w; j++) {
                this.x = this.x.modPow(e, N);
                long lastBit = this.x.testBit(0) ? 1 : 0;
                r = r << 1 | lastBit;
            }
            out.append(r + " ");
        }
        return out.toString();
    }

    @Override
    public String help() {
        System.out.println("Пример ввода: /g:bbs /p: 20998787 20999999 12345 32 /n:10000 /f: random.txt");
        System.out.println("Введите параметры: p, q, x, w, где p, q - простые числа сравнимые с 3 по модулю 4, ");
        System.out.println("x - взаимнопростое с p * q, w - бит в выходном слове");
        return "";
    }
}