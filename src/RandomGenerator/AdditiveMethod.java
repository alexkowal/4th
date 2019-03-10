package RandomGenerator;

public class AdditiveMethod {

    Long x0;
    Long x1;
    Long mod;

    public AdditiveMethod(Long x0, Long x1, Long mod) {
        this.x0 = x0;
        this.x1 = x1;
        this.mod = mod;
    }

    Long generate() {
        Long result = (x0 + x1) % mod;
        x0 = x1;
        x1 = result;
        return result;
    }


    public static void main(String[] args) {
        AdditiveMethod am = new AdditiveMethod(1l, 5l, 31l);
        for (int i = 0; i < 100; i++)
            System.out.println(am.generate());
    }
}
