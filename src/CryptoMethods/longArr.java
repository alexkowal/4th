package CryptoMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class longArr {
    ArrayList<Integer> longDigit = new ArrayList<>();
    private static int base = 10;
    private static int lenghtOfBase = 1;
    private boolean sign = true;
    static int k = 0;
    private String str = "";


    longArr(String str) {
        this.str = str;
        for (int i = str.length(); i > 0; i -= lenghtOfBase) {
            if (i < lenghtOfBase)
                longDigit.add(Integer.parseInt(str.substring(0, i)));
            else
                longDigit.add(Integer.parseInt(str.substring(i - lenghtOfBase, i)));
        }
    }

    public String toStr() {
        String res = "";
        int k = this.longDigit.size();
        if (k > 1) {
            while (this.longDigit.size() > 0 && this.longDigit.get(this.longDigit.size() - 1) == 0) {
                this.longDigit.remove(this.longDigit.size() - 1);
            }
        }

        if (this.longDigit.size() == 0)
            this.longDigit.add(0);

        if (this.str.equals(""))
            for (int i = this.longDigit.size() - 1; i >= 0; i--)
                res += this.longDigit.get(i);
        else res += this.str;
        return res;
    }

    private longArr(ArrayList<Integer> a, boolean sign) {
        this.longDigit = a;
        this.sign = sign;
    }

    private longArr(int[] a, boolean sign) {
        for (int i : a) {
            this.longDigit.add(i);
        }
        this.sign = sign;
    }

    private longArr(ArrayList<Integer> arr) {
        this.longDigit = arr;
    }

    private void changeSign(boolean flag) {
        sign = flag;
    }

    public int compareTo(longArr a) {
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

    private static ArrayList<Integer> delNull(ArrayList<Integer> a) {
        while (a.size() > 0 && a.get(a.size() - 1) == 0)
            a.remove(a.size() - 1);
        if (a.size() == 0)
            a.add(0);
        return a;
    }

    longArr add(longArr a) {
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
        return new longArr(res);
    }

    longArr sub(longArr a) {
        k = 0;

        int nMax = Math.max(a.longDigit.size(), this.longDigit.size());
        int nMin = Math.min(a.longDigit.size(), this.longDigit.size());
        longArr arg3 = new longArr("");
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
        } else {
            for (int i = nMin; i < nMax; i++) {
                arg3.longDigit.set(i, (a.longDigit.get(i) + k));
                if (arg3.longDigit.get(i) < 0) {
                    arg3.longDigit.set(i, arg3.longDigit.get(i) + 10);
                    k = -1;
                } else
                    k = 0;
            }
        }
        return arg3;
    }

    longArr mul(int a) {
        int k = 0;
        ArrayList<Integer> ans = new ArrayList<>();
        for (Integer aDigit : this.longDigit) {
            long temp = (long) aDigit * (long) a + k;
            ans.add((int) (temp % base));
            k = (int) (temp / base);
        }
        ans.add(k);
        return new longArr(delNull(ans));
    }

    longArr mul(longArr a) {
        longArr ans = new longArr("0");
        for (int i = 0; i < this.longDigit.size(); i++) {
            longArr temp = a.mul(this.longDigit.get(i));
            for (int j = 0; j < i; j++)
                temp.longDigit.add(0, 0);
            ans = ans.add(temp);
        }
        return ans;
    }

    longArr div(int v) {
        int num = this.longDigit.size() - 1;
        int ost = 0;
        longArr res = new longArr("");
        for (int i = 0; i <= num; i++)
            res.longDigit.add(0);
        while (num >= 0) {
            int cur = ost * base + this.longDigit.get(num);
            res.longDigit.set(num, cur / v);
            ost = cur % v;
            num--;
        }
        return res;
    }

    public static longArr div(longArr u, longArr v) {
        if (u.compareTo(v) == -1)
            return new longArr("0");

        longArr q;

        if (v.longDigit.size() == 1) {
            int k22 = v.longDigit.get(0);
            return u.div(k22);
        }

        int n = v.longDigit.size();
        int m = u.longDigit.size() - v.longDigit.size();

        int[] tempArray = new int[m + 1];
        tempArray[m] = 1;
        q = new longArr(tempArray, true);

        int d = (base / (v.longDigit.get(n - 1) + 1));

        u = u.mul(d);
        v = v.mul(d);

        if (d == 1 || u.longDigit.size() <= m + n)
            u.longDigit.add(0);

        for (int j = m; j >= 0; j--) {
            long t = (long) (u.longDigit.get(j + n)) * (long) (base) + u.longDigit.get(j + n - 1);
            int qt = (int) (t / v.longDigit.get(n - 1));
            int rt = (int) (t % v.longDigit.get(n - 1));
            while (rt < base && (qt == base || ((long) qt * (long) v.longDigit.get(n - 2) > (long) base * (long) rt + u.longDigit.get(j + n - 2)))) {
                qt--;
                rt += v.longDigit.get(n - 1);
            }

            longArr u2 = new longArr(new ArrayList<Integer>(u.longDigit.subList(j, j + n + 1)), true);

            longArr temp = v.mul(qt);

            longArr temp2 = new longArr("");
            for (int i = 0; i < u.longDigit.size(); i++)
                temp2.longDigit.add(0);

            for (int i = 0; i < temp.longDigit.size(); i++)
                temp2.longDigit.set(i + j, temp.longDigit.get(i));

            u = u.sub(temp2);

            q.longDigit.set(j, qt);

            if (u.k == -1) {
                k = 0;
                u2.changeSign(true);
                q.longDigit.set(j, qt - 1);

                longArr bn = new longArr("");
                for (int i = 0; i < u.longDigit.size(); i++)
                    bn.longDigit.add(0);

                for (int i = 0; i < v.longDigit.size(); i++)
                    bn.longDigit.set(i + j, v.longDigit.get(i));

                int count = u.longDigit.size();
                u = u.add(bn);
                if (count < u.longDigit.size())
                    u.longDigit.remove(u.longDigit.size() - 1);
            }

        }
        q.longDigit = q.delNull(q.longDigit);
        return q;
    }

    longArr mod(int v) {
        int num = this.longDigit.size() - 1;
        int ost = 0;
        longArr res = new longArr("");
        for (int i = 0; i <= num; i++)
            res.longDigit.add(0);
        while (num >= 0) {
            int cur = ost * base + this.longDigit.get(num);
            res.longDigit.set(num, cur / v);
            ost = cur % v;
            num--;
        }
        return new longArr(Integer.toString(ost));
    }

    public static longArr mod(longArr u, longArr v) {
        if (u.compareTo(v) == -1)
            return u;
        longArr q, r;

        if (v.longDigit.size() == 1) {
            int k22 = v.longDigit.get(0);
            return u.mod(k22);
        }

        int n = v.longDigit.size();
        int m = u.longDigit.size() - v.longDigit.size();
        if (m < 0)
            return u;

        int[] tempArray = new int[m + 1];
        tempArray[m] = 1;
        q = new longArr(tempArray, true);

        int d = (base / (v.longDigit.get(n - 1) + 1));

        u = u.mul(d);
        v = v.mul(d);

        if (d == 1 || u.longDigit.size() <= m + n)
            u.longDigit.add(0);

        if (v.longDigit.size() == 1) {
            int k22 = v.longDigit.get(0);
            return u.mod(k22);
        }

        for (int j = m; j >= 0; j--) {
            long t = (long) (u.longDigit.get(j + n)) * (long) (base) + u.longDigit.get(j + n - 1);
            int qt = (int) (t / v.longDigit.get(n - 1));
            int rt = (int) (t % v.longDigit.get(n - 1));
            while (rt < base && (qt == base || ((long) qt * (long) v.longDigit.get(n - 2) > (long) base * (long) rt + u.longDigit.get(j + n - 2)))) {
                qt--;
                rt += v.longDigit.get(n - 1);
            }

            longArr u2 = new longArr(new ArrayList<Integer>(u.longDigit.subList(j, j + n + 1)), true);

            longArr temp = v.mul(qt);

            longArr temp2 = new longArr("");
            for (int i = 0; i < u.longDigit.size(); i++)
                temp2.longDigit.add(0);

            for (int i = 0; i < temp.longDigit.size(); i++)
                temp2.longDigit.set(i + j, temp.longDigit.get(i));

            u = u.sub(temp2);

            q.longDigit.set(j, qt);

            if (u.k == -1) {
                k = 0;
                u2.changeSign(true);
                q.longDigit.set(j, qt - 1);

                longArr bn = new longArr("");
                for (int i = 0; i < u.longDigit.size(); i++)
                    bn.longDigit.add(0);

                for (int i = 0; i < v.longDigit.size(); i++)
                    bn.longDigit.set(i + j, v.longDigit.get(i));

                int count = u.longDigit.size();
                u = u.add(bn);
                if (count < u.longDigit.size())
                    u.longDigit.remove(u.longDigit.size() - 1);
            }
        }
        r = new longArr(new ArrayList<Integer>(u.longDigit.subList(0, n)), true).div(d);
        r.longDigit = r.delNull(r.longDigit);
        return r;
    }

    public static longArr modPow(longArr x, longArr n, longArr m) {

        // Z - x, N - n, Y - 1.

        longArr Y = new longArr("1");

        if (n.str.equals("0"))
            return new longArr("1");

        while (true) {
            if (n.str.equals("0"))
                break;

            longArr a = new longArr(n.mod(2).toStr());

            if (a.str.equals("0")) {
                n = new longArr(n.div(2).toStr());
                x = new longArr(x.mul(x).toStr());
                x = new longArr(mod(x, m).toStr());
            } else {
                n = new longArr(n.sub(new longArr("1")).toStr());
                Y = new longArr(Y.mul(x).toStr());
                Y = new longArr(mod(Y, m).toStr());
            }
        }
        return mod(Y, m);

    }

    public static longArr pow(longArr x, int n) {

        longArr cnt = new longArr("1");

        if (n == 0)
            return new longArr("1");

        while (true) {
            if (n == 0)
                break;


            if (n % 2 == 0) {
                n /= 2;
                x = new longArr(x.mul(x).toStr());
            } else {
                n--;
                cnt = new longArr(cnt.mul(x).toStr());
            }
        }
        return cnt;
    }

    static boolean isNumber(String s) {
        char[] a = s.toCharArray();

        if (a[0] == '0' && a.length > 1)
            return false;

        for (int i = 0; i < a.length; i++) {

            if (a[i] < '0' || a[i] > '9')
                return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println();
            System.out.println("Enter u:");
            String u = br.readLine();
            System.out.println("Enter v:");
            String v = br.readLine();

            System.out.println("Enter mod:");
            String mod = br.readLine();


            longArr a = new longArr(u);
            longArr b = new longArr(v);
            System.out.println("\nВозведение в степень по модулю: ");
            if (mod.equals("0")) {
                System.out.println("Mod is null");
                return;
            }
            if (mod.equals("1")) {
                System.out.println("0");

                System.out.println();
            } else if (b.longDigit.size() == 1 && b.longDigit.get(0) == 0) {
                System.out.println("1");
            } else
                System.out.println(modPow(a,b, new longArr(mod)).toStr());
        }
    }
}
