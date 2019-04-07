package RandomGenerator;

import java.util.ArrayList;

public class LFSR extends Generator {

    private Long p;
    private Long l;
    private ArrayList<Long> res = new ArrayList<>();


    public LFSR() {
    }

    public void init() {
        this.p = parameters.get(0);
        this.l = parameters.get(1);

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
        for (int i = 0; i < initVector.size() - 1; i++) {
            if (initVector.get(i) > p || initVector.get(i) == initVector.get(i + 1)) {
                return "Error";
            }
        }
        if (n == 0 || initVector.size() - 1 > p) {
            return "Error";
        }
        ArrayList<Long> b = toBin(l, p);
        for (int i = 0; i < n; i++) {
            ArrayList<Long> temp = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Long b_f = b.get(b.size() - 1);
                temp.add(0, b_f);
                Long b_l = b.get(Math.toIntExact((initVector.get(0))));
                if (initVector.size() > 1)
                    for (int l = 1; l < initVector.size(); l++) {
                        b_l = b_l ^ (b.get(Math.toIntExact(initVector.get(l))));
                    }
                for (int l = b.size() - 2; l >= 0; l--)
                    b.set(l + 1, b.get(l));
                b.add(0, b_l);
                b.remove(b.size() - 1);
            }
            String s = "";
            for (Long a : temp)
                s += a;
            Long t2 = Long.parseLong(s, 2) % 1000;
            out.append(t2 + " ");
        }
        return out.toString();
    }

    @Override
    public String help() {
        System.out.println("Пример ввода: /g:lfsr /p:32 12321321312 /i:20 14 18 /n:10000 /f: random.txt");
        System.out.println("Введите параметры: p - бит во входном слове, l - p-битное входное слово. ");
        System.out.println("Введите инициализационный вектор: вектор степеней полинома");
        return "";
    }
}