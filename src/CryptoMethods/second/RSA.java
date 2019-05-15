package CryptoMethods.second;

import CryptoMethods.longArr;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RSA {
    private static Map<Character, Integer> alph = new TreeMap<>();

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

    public static longArr rnd(int k) {

        longArr resN = new longArr("0");
        ArrayList<Boolean> bits = new ArrayList<>();
        Random brand = new Random();
        for (int i = 0; i < k; i++) {
            bits.add(brand.nextBoolean());
        }

        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i)) {
                resN = resN.add(longArr.pow(new longArr("2"), i));
            }
        }
        return resN;
    }

    public static void generate(longArr p, longArr q) throws IOException {

        FileWriter fw = new FileWriter("openKey.txt");
        try (FileWriter writer = new FileWriter("keys.txt")) {
            longArr n = new longArr(p.mul(q).toStr());
            System.out.println("n = " + n.toStr());
            longArr fi;

            if(p.equals(q))
            {
                fi = p.mul(p);
                fi = fi.sub(p);
                System.out.println("fi = (p * p - p) =  " + fi.toStr());
            }
            else {
                fi = new longArr(p.sub(new longArr("1")).toStr());
                fi = new longArr(fi.mul(new longArr(q.sub(new longArr("1")).toStr())).toStr());
                System.out.println("fi = (p - 1) * (q - 1) =  " + fi.toStr());
            }

            writer.write("n: " + n.toStr() + "\n");
            fw.write("n: " + n.toStr() + "\n");

            longArr e;
            longArr d;
            int r;

            while (true) {
                Random random = new Random();
                r = 2 + random.nextInt(128);
                e = rnd(r);
                if (gcd(e, fi).toStr().equals("1") && e.compareTo(new longArr("1")) == 1 && e.compareTo(fi) == -1)
                    break;
            }
            //writer.write("e: " + e.toStr() + "\n");
            fw.write("e: " + e.toStr() + "\n");
            fw.close();
            System.out.println("e = " + e.toStr());
            d = RSA.modInverse(e, fi);

            System.out.println("e * d (mod fi) = " + longArr.modPow(new longArr(e.mul(d).toStr()), new longArr("1"), fi).toStr());

            writer.write("d: " + d.toStr() + "\n");

            System.out.println("d = " + d.toStr());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static longArr modInverse(longArr a, longArr m) {
        longArr m0 = m;
        longArr y = new longArr("0");
        longArr x = new longArr("1");

        if (m.toStr().equals("1"))
            return new longArr("0");

        boolean fy = true;
        boolean ft = true;
        boolean fx = true;

        while (a.compareTo(new longArr("1")) == 1) {
            longArr q = new longArr(longArr.div(a, m).toStr());

            longArr t = m;

            m = new longArr(longArr.mod(a, m).toStr());

            a = t;

            t = y;

            if (!fy)
                ft = false;
            else ft = true;

            if (x.compareTo(new longArr(q.mul(y).toStr())) == 1 && fy && fx) {
                y = new longArr(x.sub(new longArr(q.mul(y).toStr())).toStr());
                fy = true;
            } else if (x.toStr().equals(q.mul(y).toStr()) && fy && fx) {
                y = new longArr("0");
                fy = true;
            } else if (x.compareTo(new longArr(q.mul(y).toStr())) == -1 && fy && fx) {

                y = new longArr((new longArr(q.mul(y).toStr())).sub(x).toStr());
                fy = false;

            } else if (x.compareTo(new longArr(q.mul(y).toStr())) == 1 && !fy && !fx) {
                y = new longArr(x.sub(new longArr(q.mul(y).toStr())).toStr());
                fy = false;
            } else if (x.toStr().equals(q.mul(y).toStr()) && !fy && !fx) {
                y = new longArr("0");
                fy = true;
            } else if (x.compareTo(new longArr(q.mul(y).toStr())) == -1 && !fy && !fx) {
                y = new longArr((new longArr(q.mul(y).toStr())).sub(x).toStr());
                fy = true;
            } else if (!fx && fy) {
                y = new longArr(x.add(new longArr(q.mul(y).toStr())).toStr());
                fy = false;
            } else if (fx && !fy) {
                y = new longArr(x.add(new longArr(q.mul(y).toStr())).toStr());
                fy = true;
            }

            x = t;
            if (!ft) {
                fx = false;
            } else fx = true;
        }

        if (!fx)
            x = new longArr(m0.sub(x).toStr());

        return x;
    }

    public static ArrayList<longArr> convert(longArr n) {
        String s = "";
        String mess = "";
        try (FileReader reader = new FileReader("message.txt");
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()) {
                char[] ch = scanner.nextLine().toCharArray();
                for (Character character : ch)
                    for (Map.Entry<Character, Integer> pair : alph.entrySet()) {
                        if (pair.getKey().equals(character)) {
                            System.out.println("Коды: " + pair.getKey() + " " + pair.getValue());
                            mess += pair.getKey();
                            s += String.valueOf(pair.getValue());
                            break;
                        }
                    }
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<longArr> list = new ArrayList<>();
        System.out.println("Сообщение: " + mess);
        System.out.println("Результирующая строка: " + s);
        longArr number = new longArr(s);
        if (number.compareTo(n) != -1) {
            while (!s.equals("")) {
                longArr maxx = new longArr(s.substring(0, 1));
                for (int i = 1; i < s.length() + 1; i++) {
                    if (new longArr(s.substring(0, i)).compareTo(n) != -1) {
                        s = s.substring(i - 1);
                        break;
                    }
                    if (maxx.compareTo(new longArr(s.substring(0, i))) == -1 && new longArr(s.substring(0, i)).compareTo(n) == -1)
                        maxx = new longArr(s.substring(0, i));
                    if (i == s.length()) {
                        s = "";
                    }
                }
                System.out.println(maxx.toStr());
                list.add(maxx);
            }
        } else {
            list.add(number);
        }
        return list;
    }

    public static longArr gcd(longArr a, longArr b) {

        if (a.toStr().equals("0")) return b;
        return gcd(new longArr(longArr.mod(b, a).toStr()), a);
    }

    public static void encryption(longArr e, longArr n) {
        try (FileWriter writer = new FileWriter("encrypt.txt")) {

            ArrayList<longArr> mess = RSA.convert(n);

            for (longArr numbers : mess) {
                longArr temp = new longArr(longArr.modPow(numbers, e, n).toStr());
                writer.write(temp.toStr() + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void decryption(longArr d, longArr n) {
        String s = "";
        System.out.println();
        try (FileReader fileReader = new FileReader("encrypt.txt");
             Scanner scanner = new Scanner(fileReader);
             FileWriter fileWriter = new FileWriter("decrypt.txt");
             FileWriter fileWriter2 = new FileWriter("decryptBlocks.txt")) {
            while (scanner.hasNextLine()) {
                longArr a = new longArr(scanner.nextLine());
                longArr temp = new longArr(longArr.modPow(a, d, n).toStr());
                fileWriter2.write(temp.toStr() + "\n");
                s += temp.toStr();
            }

            System.out.println("Результирующая строка: " + s);

            String[] strings = s.split("8");
            for (int i = 0; i < strings.length; i++) {
                strings[i] = strings[i] + "8";
            }
            for (String str : strings)
                for (Map.Entry<Character, Integer> pair : alph.entrySet()) {
                    if (pair.getValue() == Integer.parseInt(str)) {
                        System.out.println("Коды: " + pair.getKey() + " " + pair.getValue());
                        fileWriter.write(pair.getKey());
                        break;
                    }
                }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static int bits(longArr n) {
        int k = 0;
        while (!n.toStr().equals("0")) {
            n = longArr.div(n, new longArr("2"));
            k++;
        }
        return k;
    }

    static boolean MillerRabinTest(longArr n, int k) {

        if (n.toStr().equals("2") || n.toStr().equals("3"))
            return true;

        if (n.compareTo(new longArr("2")) == -1 || longArr.mod(n, new longArr("2")).toStr().equals("0"))
            return false;

        longArr t = new longArr(n.sub(new longArr("1")).toStr());

        long s = 0;

        while (longArr.mod(t, new longArr("2")).toStr().equals("0")) {
            t = new longArr(longArr.div(t, new longArr("2")).toStr());
            s++;
        }

        for (int i = 0; i < k; i++) {
            byte[] _a = new byte[RSA.bits(n)];

            longArr a;

            do {
                Random rng = new Random();
                for (int j = 0; j < _a.length; j++)
                    _a[j] = rng.nextBoolean() ? (byte) 1 : (byte) 0;

                a = new longArr("0");

                for (int j = 0; j < _a.length; j++) {
                    if (_a[j] == 1) {
                        a = a.add(longArr.pow(new longArr("2"), j));
                    }
                }
            }

            while (a.compareTo(new longArr("2")) == -1 || (a.compareTo(new longArr(n.sub(new longArr("2")).toStr())) == 1 || a.toStr().equals(n.sub(new longArr("2")).toStr())));

            longArr x = new longArr(longArr.modPow(a, t, n).toStr());

            if (x.toStr().equals("1") || x.toStr().equals(n.sub(new longArr("1")).toStr()))
                continue;

            for (int r = 1; r < s; r++) {
                x = new longArr(longArr.modPow(x, new longArr("2"), n).toStr());

                if (x.toStr().equals("1"))
                    return false;

                if (x.toStr().equals(n.sub(new longArr("1")).toStr()))
                    break;
            }

            if (!x.toStr().equals(n.sub(new longArr("1")).toStr()))
                return false;
        }

        return true;
    }
}
