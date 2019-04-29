package CryptoMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class longAr {

    ArrayList<Integer> longDigit = new ArrayList<>();
    private static int base = 10;
    private static int lenghtOfBase = 1;
    private boolean sign = true;
    static int k = 0;
    boolean flag = false;
    boolean showOst = false;

    longAr(String str) {
        for (int i = (int) str.length(); i > 0; i -= lenghtOfBase) {
            if (i < lenghtOfBase)
                longDigit.add(Integer.parseInt(str.substring(0, i)));
            else
                longDigit.add(Integer.parseInt(str.substring(i - lenghtOfBase, i)));
        }
    }

    @Override
    public String toString() {
        String res = "";
        ArrayList<Integer> tmp = removeZeros(this.longDigit);
        for (int i = tmp.size() - 1; i >= 0; i--) {
            res += tmp.get(i);
        }
        return res;
    }

    private longAr(ArrayList<Integer> a, boolean sign) {
        this.longDigit = a;
        this.sign = sign;
    }

    private longAr(int[] a, boolean sign) {
        for (int i : a) {
            this.longDigit.add(i);
        }
        this.sign = sign;
    }

    private longAr(ArrayList<Integer> arr) {
        this.longDigit = arr;
    }

    private void changeSign(boolean flag) {
        sign = flag;
    }

    private int compareTo(longAr a) {
        int isBigger = 0;
        if (this.longDigit.size() == a.longDigit.size()) {
            for (int i = 0; i < this.longDigit.size(); i++) {
                if (this.longDigit.get(i) > a.longDigit.get(i))
                    isBigger = 1;
                if (this.longDigit.get(i) < a.longDigit.get(i))
                    isBigger = -1;
            }
        } else {
            if (this.longDigit.size() > a.longDigit.size())
                isBigger = 1;
            if (this.longDigit.size() < a.longDigit.size())
                isBigger = -1;
        }
        return isBigger;
    }

    private static ArrayList<Integer> removeZeros(ArrayList<Integer> a) {
        while (a.size() > 0 && a.get(a.size() - 1) == 0)
            a.remove(a.size() - 1);
        if (a.size() == 0)
            a.add(0);
        return a;
    }

    longAr add(longAr a) {
        int k = 0;
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < Math.max(a.longDigit.size(), this.longDigit.size()); i++) {
            res.add((a.longDigit.size() > i ? a.longDigit.get(i) : 0) + (this.longDigit.size() > i ? this.longDigit.get(i) : 0) + k);
            if (res.get(i) >= base) {
                res.set(i, res.get(i) - base);
                k = 1;
            } else
                k = 0;
        }
        if (k == 1)
            res.add(k);
        return new longAr(res);
    }

    longAr sub(longAr a) {
        k = 0;
        int nMax = Math.max(a.longDigit.size(), this.longDigit.size());
        int nMin = Math.min(a.longDigit.size(), this.longDigit.size());
        longAr arg3 = new longAr("");
        for (int i = 0; i < nMax; i++)
            arg3.longDigit.add(0);

        for (int i = 0; i < nMin; i++) {
            arg3.longDigit.set(i, (this.longDigit.get(i) - a.longDigit.get(i) + k));
            if (arg3.longDigit.get(i) < 0) {
                arg3.longDigit.set(i, arg3.longDigit.get(i) + base);
                k = -1;
            } else
                k = 0;
        }

        if (this.longDigit.size() > a.longDigit.size()) {
            for (int i = nMin; i < nMax; i++) {
                arg3.longDigit.set(i, (this.longDigit.get(i) + k));
                if (arg3.longDigit.get(i) < 0) {
                    arg3.longDigit.set(i, arg3.longDigit.get(i) + base);
                    k = -1;
                } else
                    k = 0;
            }
        }

       /* if (k == -1)
            return a.subWithMinus(this, -1);
*/
        return arg3;
    }

    longAr subWithMinus(longAr a, int sign) {

        k = 0;
        int nMax = Math.max(a.longDigit.size(), this.longDigit.size());
        int nMin = Math.min(a.longDigit.size(), this.longDigit.size());
        longAr arg3 = new longAr("");
        for (int i = 0; i < nMax; i++)
            arg3.longDigit.add(0);

        for (int i = 0; i < nMin; i++) {
            arg3.longDigit.set(i, (this.longDigit.get(i) - a.longDigit.get(i) + k));
            if (arg3.longDigit.get(i) < 0) {
                arg3.longDigit.set(i, arg3.longDigit.get(i) + base);
                k = -1;
            } else
                k = 0;
        }

        if (this.longDigit.size() > a.longDigit.size()) {
            for (int i = nMin; i < nMax; i++) {
                arg3.longDigit.set(i, (this.longDigit.get(i) + k));
                if (arg3.longDigit.get(i) < 0) {
                    arg3.longDigit.set(i, arg3.longDigit.get(i) + base);
                    k = -1;
                } else
                    k = 0;
            }
        }
        arg3.k = -1;
        return arg3;
    }

    longAr mul(int a) {
        int k = 0;
        ArrayList<Integer> ans = new ArrayList<>();
        for (Integer aDigit : this.longDigit) {
            long temp = (long) aDigit * (long) a + k;
            ans.add((int) (temp % base));
            k = (int) (temp / base);
        }
        ans.add(k);
        return new longAr(removeZeros(ans));
    }

    longAr mul(longAr a) {
        longAr ans = new longAr("0");
        for (int i = 0; i < this.longDigit.size(); i++) {
            longAr temp = a.mul(this.longDigit.get(i));
            for (int j = 0; j < i; j++)
                temp.longDigit.add(0, 0);
            ans = ans.add(temp);
        }
        return ans;
    }

    longAr div(int v) {

        int num = this.longDigit.size() - 1;
        int ost = 0;
        longAr res = new longAr("");
        for (int i = 0; i <= num; i++)
            res.longDigit.add(0);
        while (num >= 0) {
            int cur = ost * base + this.longDigit.get(num);
            res.longDigit.set(num, cur / v);
            ost = cur % v;
            num--;
        }
        if (this.showOst) {
            if (this.flag) {
                longAr temp = (res);
                ArrayList<Integer> result = this.sub(temp.mul(v)).longDigit;
                removeZeros(result);

                System.out.print("Остаток = ");
                for (int i = result.size() - 1; i >= 0; i--) {
                    System.out.print(result.get(i));
                }
            }
            System.out.println();
        }

        return res;


    }

    static longAr getOst(longAr u, int d) {
        return u.div(d);
    }

    public static longAr div(longAr u, longAr v) {
        longAr qj = u;
        longAr tempv = v;
        longAr tempu = u;

        if (u.compareTo(v) == -1)
            return new longAr("0");

        longAr q;

        if (v.longDigit.size() == 1) {
            int k22 = v.longDigit.get(0);

            u.flag = true;
            return u.div(k22);
        }

        int n = v.longDigit.size();
        int m = u.longDigit.size() - v.longDigit.size();

        int[] tempArray = new int[m + 1];
        tempArray[m] = 1;
        q = new longAr(tempArray, true);

        int d = (base / (v.longDigit.get(n - 1) + 1));

        /*
        * ПУНКТ 1 АЛГОРИТМА ДЕЛЕНИЯ НАЧАЛО
        * */

        boolean show = u.showOst;
        u = u.mul(d);
        v = v.mul(d);

        if (d == 1 || u.longDigit.size() <= m + n)
            u.longDigit.add(0);

        /*
        * ПУНКТ 1 АЛГОРИТМА ДЕЛЕНИЯ КОНЕЦ
        * */

        /*
        * ПУНКТ 2 АЛГОРИТМА ДЕЛЕНИЯ НАЧАЛО
        * */


        /*
        * ПУНКТ 3 АЛГОРИТМА ДЕЛЕНИЯ НАЧАЛО
        * */


        for (int j = m; j >= 0; j--) {
            long t = (long) (u.longDigit.get(j + n)) * (long) (base) + u.longDigit.get(j + n - 1);
            int qt = (int) (t / v.longDigit.get(n - 1));
            int rt = (int) (t % v.longDigit.get(n - 1));
            while (rt < base && (qt == base || ((long) qt * (long) v.longDigit.get(n - 2) > (long) base * (long) rt + u.longDigit.get(j + n - 2)))) {
                qt--;
                rt += v.longDigit.get(n - 1);
            }
        /*
        * ПУНКТ 3 АЛГОРИТМА ДЕЛЕНИЯ КОНЕЦ
        * */

         /*
        * ПУНКТ 4 АЛГОРИТМА ДЕЛЕНИЯ НАЧАЛО
        * */

            longAr u2 = new longAr(new ArrayList<Integer>(u.longDigit.subList(j, j + n + 1)), true);

            longAr temp = v.mul(qt);

            longAr temp2 = new longAr("");
            for (int i = 0; i < u.longDigit.size(); i++)
                temp2.longDigit.add(0);


            for (int i = 0; i < temp.longDigit.size(); i++)
                temp2.longDigit.set(i + j, temp.longDigit.get(i));



            u = u.sub(temp2);


            /*ШАГ 5*/
            q.longDigit.set(j, qt);

            if (u.k == -1) {

                longAr b = new longAr("10");
                for (int i = 0; i <= n; i++)
                    b = b.mul(base);

                //u = b.add(u);

                k = 0;
                int k = 0;


                u2.changeSign(true);

                qj = qj.sub(new longAr("1"));

                q.longDigit.set(j, qt - 1);

                longAr bn = new longAr("");
                for (int i = 0; i < u.longDigit.size(); i++)
                    bn.longDigit.add(0);


                /*формирование 6 шага*/
                for (int i = 0; i < v.longDigit.size(); i++)
                    bn.longDigit.set(i + j, v.longDigit.get(i));


                /*6 шаг*/
                int count = u.longDigit.size();
                u = u.add(bn);
                if (count < u.longDigit.size())
                    u.longDigit.remove(u.longDigit.size() - 1);
            }

        }
        q.longDigit = q.removeZeros(q.longDigit);

        u.showOst = show;
        if (u.showOst)
            System.out.println("Остаток = " + u.div(d));


        return q;
    }

    longAr getByMod(longAr a, longAr mod) {
        longAr temp = div(a, mod);
        temp = temp.mul(mod);
        temp = a.sub(temp);
        temp.longDigit = removeZeros(temp.longDigit);
        return temp;
        //return a.sub(div(a,mod).mul(mod));
    }


    private static boolean isEven(longAr a) {
        if (a.longDigit.get(0) % 2 == 0)
            return true;
        return false;
    }

    longAr modPow(longAr a, longAr mod) {
        longAr N = a;
        longAr Y = new longAr("1");
        longAr Z = this;
        boolean oneMoreTime = false;
        if (a.longDigit.get(0) % 2 != 0)
            oneMoreTime = true;

        while (true) {
            boolean isEvenN = isEven(N);
            N = div(N, new longAr("2"));
            N.longDigit = removeZeros(N.longDigit);
            if (!isEvenN) {
                Y = Z.mul(Y);
                Y = Y.getByMod(Y, mod);

                if (N.longDigit.size() == 1 && N.longDigit.get(0) == 0)
                    if (oneMoreTime)
                        return Y.mul(Z).getByMod(Y.mul(Z), mod);
                    else return Y;
            } else {
                Z = Z.mul(Z);
                Z = Z.getByMod(Z, mod);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println();
            System.out.println("Enter u:");
            String u = br.readLine();
            System.out.println("Enter v:");
            String v = br.readLine();


            /*System.out.println("Enter mod:");
            String mod = br.readLine();
*/
            if (v.equals("0")) {
                System.out.println("Нельзя делить на ноль");
                return;
            }
            longAr a = new longAr(u);
            longAr b = new longAr(v);
            a.showOst = true;
            b.showOst = true;


            System.out.println(a.sub(b));


            ArrayList<Integer> result = div(a, b).longDigit;
            String q = "";
            removeZeros(result);

            System.out.print("Частное = ");
            for (int i = result.size() - 1; i >= 0; i--) {
                System.out.print(result.get(i));
                q += result.get(i);
            }
            if (result.size() == 1 && result.get(0) == 0)
                System.out.println("\nостаток = " + u);

            /*a.showOst = false;
            b.showOst=false;
            System.out.println("\nВозведение в степень по модулю: ");
                if (mod.equals("1"))
                System.out.println("0");
            else
                System.out.println(a.modPow(b, new longAr(mod)).toString());*/
        }
    }
}
