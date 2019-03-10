package RandomGenerator;

public class LinearSequence {

    Long mod;
    Long factor;
    Long increment;
    Long x;


    public LinearSequence(Long mod, Long factor, Long increment, Long x0) {
        this.mod = mod;
        this.factor = factor;
        this.increment = increment;
        this.x = x0;
    }

    /**
     *
     * @ mod - модуль
     //* param factor - множитель для x
     //* param increment - приращение
     //* param x - начальное значение
     * @return RandomDigit(Long)
     */

    Long generate() {
        this.x = (factor * x + increment) % mod;
        return this.x;
    }


    public static void main(String[] args) {
        Long mod = 31L;
        Long factor = 7L;
        Long increment = 3L;
        Long x = 1L;
        LinearSequence ls = new LinearSequence(mod,factor,increment,x);
        for(int i = 0 ;i  < 1000; i++) {
        ls.generate();
            System.out.println(ls.x);
        }
    }
}
