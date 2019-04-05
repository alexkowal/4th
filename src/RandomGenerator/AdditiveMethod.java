package RandomGenerator;

public class AdditiveMethod extends Generator {

    Long x0;
    Long x1;
    Long mod;

    void initGenerator() {
        this.x0 = parameters.get(0);
        this.x1 = parameters.get(1);
        this.mod = parameters.get(2);
    }

    public String generate() {
        this.initGenerator();
        for (int i = 0; i < n; i++) {
            Long result = (x0 + x1) % mod;
            x0 = x1;
            x1 = result;
            out.append(result + " ") ;
        }
        return out.toString();
    }

    public AdditiveMethod() {


    }
}
