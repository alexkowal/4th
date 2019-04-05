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
}