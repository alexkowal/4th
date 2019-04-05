package RandomGenerator;

public class LinearSequence extends Generator {

    Long mod;
    Long factor;
    Long increment;
    Long x;

    public LinearSequence() {

    }


    public LinearSequence(Long mod, Long factor, Long increment, Long x0) {
        this.mod = mod;
        this.factor = factor;
        this.increment = increment;
        this.x = x0;
    }

    /**
     * @return RandomDigit(Long)
     * @ mod - модуль
     * //* param factor - множитель для x
     * //* param increment - приращение
     * //* param x - начальное значение
     */

    void initGenerator() {
        this.x = parameters.get(0);
        this.factor = parameters.get(1);
        this.increment = parameters.get(2);
        this.mod = parameters.get(3);
    }


    public String generate() {
        initGenerator();
        for (int i = 0; i < n; i++) {
            this.x = (factor * x + increment) % mod;
            out.append(this.x + " ");
        }
        return out.toString();
    }
}
