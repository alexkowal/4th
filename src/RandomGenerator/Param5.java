package RandomGenerator;

import java.util.ArrayList;

public class Param5 extends Generator {

    private Long p;
    private Long q1;
    private Long q2;
    private Long q3;
    private Long w;
    private Long a;
    private ArrayList<Long> res = new ArrayList<>();


    public Param5() {
    }

    public void init() {
        this.p = parameters.get(0);
        this.q1 = parameters.get(1);
        this.q2 = parameters.get(2);
        this.q3 = parameters.get(3);
        this.w = parameters.get(4);
        this.a = parameters.get(5);
    }

    private ArrayList<Long> toBin(long a, long p) {
        ArrayList<Long> b = new ArrayList<>();
        while (a != 0 && p > 0) {
            b.add(0, a % 2);
            a /= 2;
            p--;
        }
        while (p > 0) {
            b.add(0, 0L);
            p--;
        }
        return b;
    }

    public String generate() {
        init();
        if (q1 >= p || q1 == 0 || q2 == 0 || q3 == 0 || q2 >= p || q3 >= p || n == 0) {
            return "Error";
        }
        ArrayList<Long> b = toBin(a, p);
        for (int i = 0; i < n; i++) {
            ArrayList<Long> temp = new ArrayList<>();
            for (int j = 0; j < w; j++) {
                Long b_f = b.get(b.size() - 1);
                temp.add(0, b_f);

                Long b_l = (b.get(Math.toIntExact((q1))) ^ b.get(Math.toIntExact(q2)) ^ b.get(Math.toIntExact(q3)));

                for (int l = b.size() - 2; l >= 0; l--)
                    b.set(l + 1, b.get(l));
                b.add(0, b_l);
                b.remove(b.size() - 1);

            }
            String s = "";
            for (Long a : temp)
                s += a;
            Long t2 = Long.parseLong(s, 2);
            res.add(t2);
        }
        for (Long re : res) {
            out.append(re + " ");
        }
        return out.toString();
    }
    @Override
    public String help() {
        System.out.println("Пример ввода: /g:5p /p: 89 20 40 69 10 1712343232342 /n:10000 /f: random.txt");
        System.out.println("Введите параметры: p - бит во входном слове, {q1, q2, q3} - отводы, w - бит в полученном слове, a - входное слово");
        return "";
    }
}