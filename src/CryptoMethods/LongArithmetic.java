package CryptoMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

    static String substract(String u, String v) {
        String result = "";
        /*if (u.length() < v.length()) {
            String temp = u;
            u = v;
            v = temp;
        }
        */

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


        // for (int j = n - 1; j >= 0; j--) {
        for (int j = n - 1; j >= 0; j--) {
            int w = (Integer.parseInt(String.valueOf(u.charAt(j))) - Integer.parseInt(String.valueOf(v.charAt(j))) + k) % b;
            if (w < 0) {
                if (j > 0) {
                    StringBuilder t = new StringBuilder();
                    t.append(u);
                    t.setCharAt(j - 1, (char) (t.charAt(j - 1) - 1));
                    if (t.charAt(j - 1) == '/')
                        t.setCharAt(j - 1, '9');
                    u = t.toString();

                }
                if (j == 0) {
                    StringBuilder t = new StringBuilder();
                    t.append(u);
                    t.setCharAt(j, (char) (t.charAt(j) - 1));
                    if (t.charAt(j) == '/')
                        t.setCharAt(j - 1, '9');
                    u = t.toString();
                }
                w = (Integer.parseInt(String.valueOf(u.charAt(j))) + 10 - Integer.parseInt(String.valueOf(v.charAt(j))) + k) % b;

            }

            k = (Integer.parseInt(String.valueOf(u.charAt(j))) - Integer.parseInt(String.valueOf(v.charAt(j))) + k) / b;
            result += w;
        }
        if (minus)
            result += '-';
        StringBuilder b = new StringBuilder(result);
        b.reverse();

        for (int i = 0; i < b.length(); i++) {
            if (b.charAt(i) == '0' && i != b.length() - 1) {
                b.deleteCharAt(i--);
            } else break;
        }

        return b.toString();
    }


    public static void main(String[] args) throws IOException {

        String a, b;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Enter a");
            a = br.readLine();
            System.out.println("Enter b");
            b = br.readLine();
            if (!validate(a) || !validate(b)) {
                System.out.println("WRONG INPUT.");
                a = null;
                b = null;
            } else
                break;
        }
        System.out.println(a + " + " + b + " = " + add(a, b));
        System.out.println(substract(a, b));

    }
}
