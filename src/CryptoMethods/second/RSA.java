package CryptoMethods.second;

import CryptoMethods.longAr;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RSA {
    private  static Map<Character, Integer> alph = new TreeMap<>();

    static {
        alph.put('0', 18);
        alph.put('1', 28);
        alph.put('2', 38);
        alph.put('3', 48);
        alph.put('4', 58);
        alph.put('5', 68);
        alph.put('6', 78);
        alph.put('7', 118);
        alph.put('8', 128);
        alph.put('9', 138);
        alph.put('а', 148);
        alph.put('б', 158);
        alph.put('в', 168);
        alph.put('г', 178);
        alph.put('д', 218);
        alph.put('е', 228);
        alph.put('ё', 238);
        alph.put('ж', 248);
        alph.put('з', 258);
        alph.put('и', 268);
        alph.put('й', 278);
        alph.put('к', 318);
        alph.put('л', 328);
        alph.put('м', 338);
        alph.put('н', 348);
        alph.put('о', 358);
        alph.put('п', 368);
        alph.put('р', 378);
        alph.put('с', 418);
        alph.put('т', 428);
        alph.put('у', 438);
        alph.put('ф', 448);
        alph.put('х', 458);
        alph.put('ц', 468);
        alph.put('ч', 478);
        alph.put('ш', 518);
        alph.put('щ', 528);
        alph.put('ь', 538);
        alph.put('ы', 548);
        alph.put('ъ', 558);
        alph.put('э', 568);
        alph.put('ю', 578);
        alph.put('я', 618);
        alph.put(' ', 628);
        alph.put(',', 638);
        alph.put('.', 648);
        alph.put('-', 658);
        alph.put('?', 668);
        alph.put('!', 678);
        alph.put('А', 718);
        alph.put('Б', 728);
        alph.put('В', 738);
        alph.put('Г', 748);
        alph.put('Д', 758);
        alph.put('Е', 768);
        alph.put('Ё', 778);
        alph.put('Ж', 1118);
        alph.put('З', 1128);
        alph.put('И', 1138);
        alph.put('Й', 1148);
        alph.put('К', 1158);
        alph.put('Л', 1168);
        alph.put('М', 1178);
        alph.put('Н', 1218);
        alph.put('О', 1228);
        alph.put('П', 1238);
        alph.put('Р', 1248);
        alph.put('С', 1258);
        alph.put('Т', 1268);
        alph.put('У', 1278);
        alph.put('Ф', 1318);
        alph.put('Х', 1328);
        alph.put('Ц', 1338);
        alph.put('Ч', 1348);
        alph.put('Ш', 1358);
        alph.put('Щ', 1368);
        alph.put('Ь', 1378);
        alph.put('Ы', 1418);
        alph.put('Ъ', 1428);
        alph.put('Э', 1438);
        alph.put('Ю', 1448);
        alph.put('Я', 1458);
    }

    public static longAr rnd(int k) {

        longAr resN = new longAr("0");
        ArrayList<Boolean> bits = new ArrayList<>();
        Random brand = new Random();
        for (int i = 0; i < k; i++) {
            bits.add(brand.nextBoolean());
        }

        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i)) {
                resN = resN.add(longAr.pow(new longAr("2"), i));
            }
        }
        return resN;
    }

    public static void generate(longAr p, longAr q) {

        try (FileWriter writer = new FileWriter("keys.txt")) {
            longAr n = new longAr(p.mul(q).toString());
            System.out.println("n = " + n.toString());
            longAr fi;
//            if (p.toString().equals(q.toString())) {
//                fi = new longAr(new longAr(longAr.pow(p, 2).toString()).sub(p).toString());
//            }
//            else {
            fi = new longAr(p.sub(new longAr("1")).toString());
            fi = new longAr(fi.mul(new longAr(q.sub(new longAr("1")).toString())).toString());
            // }
            System.out.println("fi = (q - 1) * (p - 1) =  " + fi.toString());

            writer.write("n: " + n.toString() + "\n");

            longAr e;
            longAr d;
            int r;

            while (true) {
                Random random = new Random();
                r = 2 + random.nextInt(128);
                e = rnd(r);
                if (gcd(e, fi).toString().equals("1") && e.compareTo(new longAr("1")) == 1 && e.compareTo(fi) == -1)
                    break;
            }

            writer.write("e: " + e.toString() + "\n");

            System.out.println("e = " + e.toString());
            d = RSA.modInverse(e, fi);

            System.out.println("e * d (mod fi) = " + longAr.modPow(new longAr(e.mul(d).toString()), new longAr("1"), fi).toString());

            writer.write("d: " + d.toString() + "\n");

            System.out.println("d = " + d.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static longAr modInverse(longAr a, longAr m)
    {
        longAr m0 = m;
        longAr y = new longAr("0");
        longAr x = new longAr("1");

        if (m.toString().equals("1"))
            return new longAr("0");

        boolean fy = true;
        boolean ft = true;
        boolean fx = true;

        while (a.compareTo(new longAr("1")) == 1)
        {
            longAr q = new longAr(longAr.div(a, m).toString());

            longAr t = m;

            m = new longAr(longAr.mod(a, m).toString());

            a = t;

            t = y;

            if (!fy)
                ft = false;
            else ft = true;

            if (x.compareTo(new longAr(q.mul(y).toString())) == 1 && fy && fx) {
                y = new longAr(x.sub(new longAr(q.mul(y).toString())).toString());
                fy = true;
            }

            else if (x.toString().equals(q.mul(y).toString()) && fy && fx) {
                y = new longAr("0");
                fy = true;
            }

            else if (x.compareTo(new longAr(q.mul(y).toString())) == -1 && fy && fx) {

                y = new longAr((new longAr(q.mul(y).toString())).sub(x).toString());
                fy = false;

            }
            else if (x.compareTo(new longAr(q.mul(y).toString())) == 1 && !fy && !fx) {
                y = new longAr(x.sub(new longAr(q.mul(y).toString())).toString());
                fy = false;
            }

            else if (x.toString().equals(q.mul(y).toString()) && !fy && !fx) {
                y = new longAr("0");
                fy = true;
            }

            else if (x.compareTo(new longAr(q.mul(y).toString())) == -1 && !fy && !fx) {
                y = new longAr((new longAr(q.mul(y).toString())).sub(x).toString());
                fy = true;
            }
            else if (!fx && fy) {
                y = new longAr(x.add(new longAr(q.mul(y).toString())).toString());
                fy = false;
            }
            else if (fx && !fy) {
                y = new longAr(x.add(new longAr(q.mul(y).toString())).toString());
                fy = true;
            }

            x = t;
            if (!ft) {
                fx = false;
            }
            else fx = true;
        }

        if (!fx)
            x = new longAr(m0.sub(x).toString());

        return x;
    }

    public static ArrayList<longAr> convert(longAr n) {
        String s = "";
        String mess = "";
        try (FileReader reader = new FileReader("message.txt");
             Scanner scanner = new Scanner(reader)){
            while (scanner.hasNext()) {
                char[] ch = scanner.nextLine().toCharArray();
                for (Character character: ch)
                    for (Map.Entry<Character, Integer> pair: alph.entrySet()) {
                        if (pair.getKey().equals(character)) {
                            System.out.println("Коды: " + pair.getKey() + " " + pair.getValue());
                            mess += pair.getKey();
                            s += String.valueOf(pair.getValue());
                            break;
                        }
                    }
            }
            System.out.println();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<longAr> list = new ArrayList<>();
        System.out.println("Сообщение: " + mess);
        System.out.println("Результирующее число: " + s);
        longAr number = new longAr(s);
        if (number.compareTo(n) != -1) {
            while (!s.equals("")) {
                longAr maxx = new longAr(s.substring(0, 1));
                for (int i = 1; i < s.length() + 1; i++) {
                    if (new longAr(s.substring(0, i)).compareTo(n) != -1) {
                        s = s.substring(i - 1);
                        break;
                    }
                    if (maxx.compareTo(new longAr(s.substring(0, i))) == -1 && new longAr(s.substring(0, i)).compareTo(n) == -1)
                        maxx = new longAr(s.substring(0, i));
                    if (i == s.length()) {
                        s = "";
                    }
                }
                System.out.println(maxx.toString());
                list.add(maxx);
            }
        }
        else {
            list.add(number);
        }
        return list;
    }

    public static longAr gcd(longAr a, longAr b) {

        if (a.toString().equals("0")) return b;
        return gcd(new longAr(longAr.mod(b, a).toString()), a);
    }

    public static void encryption(longAr e, longAr n) {
        try (FileWriter writer = new FileWriter("encrypt.txt")) {

            ArrayList<longAr> mess = RSA.convert(n);

            for (longAr longAr : mess) {
                longAr temp = new longAr(longAr.modPow(longAr, e, n).toString());
                writer.write(temp.toString() + "\n");
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void decryption(longAr d, longAr n) {
        String s = "";
        System.out.println();
        try (FileReader fileReader = new FileReader("encrypt.txt");
             Scanner scanner = new Scanner(fileReader);
             FileWriter fileWriter = new FileWriter("decrypt.txt");
             FileWriter fileWriter2 = new FileWriter("decryptBlocks.txt")){
            while (scanner.hasNextLine()) {
                longAr a = new longAr(scanner.nextLine());
                longAr temp = new longAr(longAr.modPow(a, d, n).toString());
                fileWriter2.write(temp.toString() + "\n");
                s += temp.toString();
            }

            System.out.println("Результирующее число: " + s);

            String[] strings = s.split("8");
            for (int i = 0; i < strings.length; i++) {
                strings[i] = strings[i] + "8";
            }
            for (String str: strings)
                for (Map.Entry<Character, Integer> pair: alph.entrySet()) {
                    if (pair.getValue() == Integer.parseInt(str)) {
                        System.out.println("Коды: " + pair.getKey() + " " + pair.getValue());
                        fileWriter.write(pair.getKey());
                        break;
                    }
                }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static int bits(longAr n) {
        int k = 0;
        while (!n.toString().equals("0")) {
            n = longAr.div(n, new longAr("2"));
            k++;
        }
        return k;
    }

    static boolean MillerRabinTest(longAr n, int k) {

        if (n.toString().equals("2") || n.toString().equals("3"))
            return true;

        if (n.compareTo(new longAr("2")) == - 1 || longAr.mod(n, new longAr("2")).toString().equals("0"))
            return false;

        longAr t = new longAr(n.sub(new longAr("1")).toString());

        long s = 0;

        while (longAr.mod(t, new longAr("2")).toString().equals("0"))
        {
            t = new longAr(longAr.div(t, new longAr("2")).toString());
            s++;
        }

        for (int i = 0; i < k; i++)
        {
            byte[] _a = new byte[RSA.bits(n)];

            longAr a;

            do
            {
                Random rng = new Random();
                for (int j = 0; j < _a.length; j++)
                    _a[j] = rng.nextBoolean() ? (byte)1: (byte)0;

                a = new longAr("0");

                for (int j = 0; j < _a.length; j++) {
                    if (_a[j] == 1) {
                        a = a.add(longAr.pow(new longAr("2"), j));
                    }
                }
            }

            while (a.compareTo(new longAr("2")) == -1 || (a.compareTo(new longAr(n.sub(new longAr("2")).toString())) == 1 || a.toString().equals(n.sub(new longAr("2")).toString())));

            longAr x = new longAr(longAr.modPow(a, t, n).toString());

            if (x.toString().equals("1") || x.toString().equals(n.sub(new longAr("1")).toString()))
                continue;

            for (int r = 1; r < s; r++)
            {
                x = new longAr(longAr.modPow(x, new longAr("2"), n).toString());

                if (x.toString().equals("1"))
                    return false;

                if (x.toString().equals(n.sub(new longAr("1")).toString()))
                    break;
            }

            if (!x.toString().equals(n.sub(new longAr("1")).toString()))
                return false;
        }

        return true;
    }
}