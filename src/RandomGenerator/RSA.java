package RandomGenerator;

import java.math.BigInteger;

public class RSA extends Generator {

    BigInteger p, q, x;
    Long w;
    BigInteger N, e;

    Long result = 0L;

    void init() {
        this.p = BigInteger.valueOf(parameters.get(0));
        this.q = BigInteger.valueOf(parameters.get(1));
        this.e = BigInteger.valueOf(parameters.get(2));
        this.x = BigInteger.valueOf(parameters.get(3));
        this.w = parameters.get(4);
    }

    @Override
    public String generate() {
        init();
        N = p.multiply(q);
        BigInteger f = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < w; i++) {
                x = x.modPow(e, N);
                byte b = (x.testBit(0) ? (byte) 1 : (byte) 0);
                result = result << 1L | b;
            }
            out.append(result + " ");
        }
        return out.toString();
    }

    @Override
    public String help() {
        System.out.println("Пример ввода: /g:rsa /p: 2000003 2000029 31 12345 32 /n:10000 /f: random.txt");
        System.out.println("Введите параметры: p, q, e, x, w, где p и q - простые числа, w - бит в выходном слове" +
                "e такое, что НОД(е,(p-1) * (q-1)) = 1, ");
        System.out.println("x - начальное целое число в интервале [1,(p * q) - 1]");
        return "";
    }
}
