package RandomGenerator;

import java.util.ArrayList;

public class NFSR extends Generator {
    private long p1;
    private long l1;
    private ArrayList<Long> initVector1;
    private ArrayList<Long> res1 = new ArrayList<>();
    private long p2;
    private long l2;
    private ArrayList<Long> initVector2;
    private ArrayList<Long> res2 = new ArrayList<>();
    private long p3;
    private long l3;
    private ArrayList<Long> initVector3;
    private ArrayList<Long> res3 = new ArrayList<>();
    ArrayList<Integer> res = new ArrayList<>();


    void init() {
        p1 = parametersNFSR.get(0).get(0);
        l1 = parametersNFSR.get(0).get(1);

        p2 = parametersNFSR.get(1).get(0);
        l2 = parametersNFSR.get(1).get(1);

        p3 = parametersNFSR.get(2).get(0);
        l3 = parametersNFSR.get(2).get(1);

        initVector1 = initVectorNFSR.get(0);
        initVector2 = initVectorNFSR.get(1);
        initVector3 = initVectorNFSR.get(2);
    }

    public ArrayList<Long> toBin(long a, long p) {
        ArrayList<Long> binarDig = new ArrayList<>();
        while (a != 0 && p > 0) {
            binarDig.add(a % 2);
            a /= 2;
            p--;
        }
        while (p > 0) {
            binarDig.add(0L);
            p--;
        }
        return binarDig;
    }

    @Override
    public String generate() {
        init();
        ArrayList<Long> b1 = toBin(l1, p1);
        ArrayList<Long> b2 = toBin(l2, p2);
        ArrayList<Long> b3 = toBin(l3, p3);

        for (int i = 0; i < n; i++) {
            ArrayList<Long> temp1 = new ArrayList<>();
            ArrayList<Long> temp2 = new ArrayList<>();
            ArrayList<Long> temp3 = new ArrayList<>();

            for (int j = 0; j < 32; j++) {
                Long b_f1 = (b1.get(0));
                Long b_f2 = (b2.get(0));
                Long b_f3 = (b3.get(0));
                temp1.add(b_f1);
                temp2.add(b_f2);
                temp3.add(b_f3);

                Long b_l1 = b1.get(Math.toIntExact(initVector1.get(0)));
                Long b_l2 = b2.get(Math.toIntExact(initVector2.get(0)));
                Long b_l3 = b3.get(Math.toIntExact(initVector3.get(0)));


                if (initVector1.size() > 1)
                    for (int l = 1; l < initVector1.size(); l++) {
                        b_l1 = b_l1 ^ (b1.get(Math.toIntExact(initVector1.get(l))));
                    }

                if (initVector2.size() > 1)
                    for (int l = 1; l < initVector2.size(); l++) {
                        b_l2 = b_l2 ^ (b2.get(Math.toIntExact(initVector2.get(l))));
                    }

                if (initVector3.size() > 1)
                    for (int l = 1; l < initVector3.size(); l++) {
                        b_l3 = b_l3 ^ (b3.get(Math.toIntExact(initVector3.get(l))));
                    }

                for (int l = 0; l < b1.size() - 1; l++)
                    b1.set(l, b1.get(l + 1));
                b1.set(b1.size() - 1, b_l1);

                for (int l = 0; l < b2.size() - 1; l++)
                    b2.set(l, b2.get(l + 1));
                b2.set(b2.size() - 1, b_l2);

                for (int l = 0; l < b3.size() - 1; l++)
                    b3.set(l, b3.get(l + 1));
                b3.set(b3.size() - 1, b_l3);
            }
            String s1 = "";
            for (Long a : temp1)
                s1 += a;

            String s2 = "";
            for (Long a : temp2)
                s2 += a;

            String s3 = "";
            for (Long a : temp3)
                s3 += a;

            Long t1 = Long.parseLong(s1, 2);
            res1.add(t1);

            Long t2 = Long.parseLong(s2, 2);
            res2.add(t2);

            Long t3 = Long.parseLong(s3, 2);
            res3.add(t3);
        }

        for (int i = 0; i < n; i++) {
            Long r1 = res1.get(i);
            Long r2 = res2.get(i);
            Long r3 = res3.get(i);
            out.append((((r1 & r2) ^ (r2 & r3) ^ r3)) + " ");
        }

        return out.toString();
    }
    @Override
    public String help() {
        System.out.println("Пример ввода: /g:nfsr /p: 32 12312213 | 8 32324 | 24 4332355 /i: 31 15 11 7 4 | 7 6 2 1 | 12 8 5 1 /n:10000 /f: random.txt");
        System.out.println("Введите параметры (для каждого из РСЛОС): p - бит во входном слове, l - входное слово");
        System.out.println("Введите инициализационный вектор (для каждого из РСЛОС): вектор степеней полинома");
        return "";
    }
}
