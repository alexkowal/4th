package CryptoMethods;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.lang.Integer.lowestOneBit;
import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class LongArithmetic {
    static final int b = 10;

    static String normalize(String u, int size) {
        if (u.length() < size) {
            StringBuilder temp = new StringBuilder();
            for (int i = 0; i < size - u.length(); i++)
                temp.append('0');
            temp.append(u);
            u = temp.toString();
        }
        return u.toString();
    }

    static String reverce(String u) {
        StringBuilder b = new StringBuilder(u);
        b = b.reverse();
        return b.toString();
    }

    static boolean validate(String s) {
        if (s.length() == 0)
            return false;
        String template = "[0-9]+";
        Pattern pattern = null;
        try {
            pattern = Pattern.compile(template);
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
        if (pattern == null) {
            return false;
        }
        final Matcher regexp = pattern.matcher(s);
        if (!regexp.matches())
            return false;

        if (s.length() > 1 && s.charAt(0) == '0')
            return false;

        return true;
    }

    static String add(String u, String v) {
        String result = "";
        if (u.length() > v.length()) {
            String temp = u;
            u = v;
            v = temp;
        }
        u = normalize(u, v.length());
        int n = max(u.length(), v.length());
        int k = 0;
        for (int j = n - 1; j >= 0; j--) {
            int w = (Integer.parseInt(String.valueOf(u.charAt(j))) + Integer.parseInt(String.valueOf(v.charAt(j))) + k) % b;
            k = (Integer.parseInt(String.valueOf(u.charAt(j))) + Integer.parseInt(String.valueOf(v.charAt(j))) + k) / b;
            result += w;
        }
        if (k != 0)
            result += k;
        StringBuilder b = new StringBuilder(result);
        b.reverse();
        return b.toString();
    }

    static String subtract(String u, String v) {
        String a = u;
        String result = "";

        boolean minus = false;

        if (u.length() == v.length())
            for (int i = 0; i < min(u.length(), v.length()); i++)
                if (Integer.parseInt(String.valueOf(u.charAt(i))) < Integer.parseInt(String.valueOf(v.charAt(i)))) {
                    minus = true;
                    break;
                }
        if (u.length() > v.length()) {
            minus = false;
            v = normalize(v, u.length());

        }
        if (u.length() < v.length()) {
            minus = true;
            u = normalize(u, v.length());
        }
        int n = max(u.length(), v.length());
        int k = 0;
        for (int j = n - 1; j >= 0; j--) {
            boolean flag = false;
            int w = (Integer.parseInt(String.valueOf(u.charAt(j))) - Integer.parseInt(String.valueOf(v.charAt(j))) + k) % b;
            if (w < 0) {

                if (j > 0) {
                    int i = 1;
                    while (i <= j) {
                        StringBuilder t = new StringBuilder();
                        t.append(u);
                        if (t.charAt(j - i) != '0') {
                            t.setCharAt(j - i, (char) (t.charAt(j - i) - 1));
                            u = t.toString();
                            flag = true;
                            break;
                        } else {
                            i++;
                        }
                        //u = t.toString();
                    }
                }
                w = (Integer.parseInt(String.valueOf(u.charAt(j))) + 10 - Integer.parseInt(String.valueOf(v.charAt(j))) + k) % b;
            } else flag = true;
            k = (Integer.parseInt(String.valueOf(u.charAt(j))) - Integer.parseInt(String.valueOf(v.charAt(j))) + k) / b;
            if (flag)
                result += w;
        }


        StringBuilder b = new StringBuilder(result);
        b.reverse();

        for (int i = 0; i < b.length(); i++) {
            if (b.charAt(i) == '0' && i != b.length() - 1) {
                b.deleteCharAt(i--);
            } else break;
        }
        result = b.toString();

        if (!a.equals(add(result, v)))
            return "WRONG INPUT: a < b";
        return result;
    }

    static String mul(String u, String v) {

        int count = 0;
        StringBuilder c = new StringBuilder();
        for (int i = 0; i < u.length() + v.length(); i++)
            c.append(0);

        StringBuilder t = new StringBuilder(u);
        t = t.reverse();
        u = t.toString();
        t = new StringBuilder(v);
        v = t.reverse().toString();

        int carry = 0;
        for (int i = 0; i < u.length(); i++)
            for (int j = 0; j < v.length(); j++) {

                int temp = Character.getNumericValue(c.charAt(i + j))
                        + Character.getNumericValue(u.charAt(i))
                        * ((j < v.length() ? Character.getNumericValue(v.charAt(j)) : 0)) + carry;
                if (j == v.length() - 1 && i + j != c.length()) {
                    c.setCharAt(i + j, Character.forDigit(temp % b, b));
                    c.setCharAt(i + j + 1, Character.forDigit(temp / b, b));
                    carry = 0;
                } else {
                    c.setCharAt(i + j, Character.forDigit(temp % b, b));
                    carry = (temp / b);
                }
                if (carry != 0)
                    count++;
            }
        if (carry != 0) {
            // count++;
            //c.setCharAt(c.length()-1,Character.forDigit(carry,b));
            c.setCharAt(c.length() - 1, Character.forDigit(carry, b));
        }
        while (c.length() > max(count, 1) && c.charAt(c.length() - 1) == '0')
            c.deleteCharAt(c.length() - 1);

        //System.out.println(c.toString());
        return c.reverse().toString();
    }

    static String divEz(String u, String v) {
        int num = u.length() - 1;
        int ost = 0;
        StringBuilder res = new StringBuilder("");
        for (int i = 0; i <= num; i++)
            res.append('0');
        while (num >= 0) {
            int cur = ost * b + Integer.valueOf(u.charAt(num));
            res.setCharAt(num, Character.forDigit(cur / Integer.valueOf(v), b));
            ost = cur % Integer.valueOf(v);
            num--;
        }
        return String.valueOf(res);
    }


    static String div(String u, String v) {


        String q = new String();

        if (v.length() == 1) {
            String k22 = String.valueOf(v.charAt(0));
            return divEz(u, k22);
        }

        int n = v.length();
        int m = u.length() - v.length();


        int[] tempArray = new int[m + 1];
        tempArray[m] = 1;
        for (int i = 0; i < tempArray.length; i++)
            q += tempArray[i];


        int d = (b / (Integer.parseInt(String.valueOf(v.charAt(n - 1))) + 1));

        u = mul(u, String.valueOf(d));
        v = mul(v, String.valueOf(d));



        if (d == 1 || u.length() <= m + n)
            u += "0";


       /* StringBuilder stringBuilder = new StringBuilder(u);
        u = stringBuilder.reverse().toString();

        stringBuilder = new StringBuilder(v);
        v = stringBuilder.reverse().toString();
*/

        for (int j = m; j >= 0; j--) {
            long t = Integer.parseInt(String.valueOf(u.charAt(j + n))) * (long) (b) + Integer.parseInt(String.valueOf(u.charAt(j + n - 1)));
            int qt = (int) (t / Integer.parseInt(String.valueOf(v.charAt((n - 1)))));
            int rt = (int) (t % Integer.parseInt(String.valueOf(v.charAt(n - 1))));
            while (rt < b && (qt == b || ((long) qt * (long) Integer.parseInt(String.valueOf(v.charAt(n - 2)))
                    > (long) b * (long) rt + Integer.parseInt(String.valueOf(u.charAt(j + n - 2)))))) {
                qt--;
                rt += Integer.parseInt(String.valueOf(v.charAt(n - 1)));
            }

            //longAr u2 = new longAr(new ArrayList<Integer>(u.longDigit.subList(j, j + n + 1)), true);
            String u2 = u.substring(j, j + n + 1);

            //longAr temp = v.mul(qt);
            String temp = mul(v, String.valueOf(qt));

            //longAr temp2 = new longAr("");
            StringBuilder temp2 = new StringBuilder();
            for (int i = 0; i < u.length(); i++)
                temp2.append("0");

            for (int i = 0; i < temp.length(); i++)
                temp2.setCharAt(i + j, temp.charAt(i));
            // temp2.longDigit.set(i + j, temp.longDigit.get(i));

            //u = u.sub(temp2);
            //u = subtract(u, temp2.toString()); //НЕ СРАБОТАЕТ !!!!!!!!!!!!!!!!!!

            //q.longDigit.set(j, qt);
            StringBuilder sb = new StringBuilder(q);
            sb.setCharAt(j, Character.forDigit(qt, b));
            q = sb.toString();

            if (subtract(u, temp2.toString()) == "WRONG INPUT: a < b") {

                //q.longDigit.set(j, qt - 1);
                sb = new StringBuilder(q);
                sb.setCharAt(j, Character.forDigit(qt - 1, b));
                q = sb.toString();


                //longAr bn = new longAr("");
                String bn = "";
                for (int i = 0; i < u.length(); i++)
                    bn += "0";

                for (int i = 0; i < v.length(); i++) {
                   // bn.longDigit.set(i + j, v.longDigit.get(i));
                    sb = new StringBuilder(bn);
                    sb.setCharAt(i + j,v.charAt(i));
                    q = sb.toString();
                }

                int count = u.length();
                u = add(u, String.valueOf(bn));
                if (count < u.length()) {
                    //    u.longDigit.remove(u.longDigit.size() - 1);
                    sb = new StringBuilder(u);
                    sb.deleteCharAt(u.length()-1);
                    u = sb.toString();

                }
            }

        }
        q = delNull(q);
        return q;

    }
    private static String  delNull(String a) {
        while (a.length() > 0 && a.charAt(a.length()- 1) =='0') {
         //   a.remove(a.size() - 1);
            StringBuilder sb = new StringBuilder(a);
            sb.deleteCharAt(a.length() - 1);
            a = sb.toString();
        }
            if (a.length() == 0)
                a+="0";
        return a;
    }



    public static void main(String[] args) throws IOException {
        String a, b;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            while (true) {
                System.out.println("Enter a");
                a = br.readLine();
                System.out.println("Enter b");
                b = br.readLine();
                if (!validate(a) || !validate(b)) {
                    System.out.println("WRONG INPUT.");
                    a = null;
                    b = null;
                } else if (subtract(a, b) == "") {
                    System.out.println("WRONG INPUT.");
                    a = null;
                    b = null;
                } else
                    break;
            }
            //System.out.println(a + " + " + b + " = " + add(a, b));
            //System.out.println(a + " - " + b + " = " + subtract(a, b));
            //System.out.println(a + " * " + b + " = " + mul(a, b));
            //System.out.println(div(a, b));

            System.out.println(div(a,b));
        }

    }
}
