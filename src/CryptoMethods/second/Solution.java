package CryptoMethods.second;

import CryptoMethods.longAr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) {

        try  {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите 0 - сгенерировать ключи, 1 - зашифровать сообщение, 2 - расшифровать криптограмму:");
            int k = Integer.parseInt(reader.readLine());
            if (k != 1 && k != 2 && k != 0) throw new NumberFormatException();

            if (k == 0){
                System.out.println("Введите p, p > 1:");
                String p = reader.readLine();

                if (!longAr.isNumber(p) || new longAr(p).compareTo(new longAr("1")) != 1)
                {
                    throw new NumberFormatException();
                }

                if (!RSA.MillerRabinTest(new longAr(p), 130)) {
                    System.out.println("Ошибка! Вы ввели составное p!");
                    return;
                }

                System.out.println("Введите q, q > 2 (если p = 2, то введедите q > 3):");
                String q = reader.readLine();

                if (!longAr.isNumber(q) || new longAr(q).compareTo(new longAr("2")) != 1 || (p.equals("2") && new longAr(q).compareTo(new longAr("3")) != 1))
                {
                    throw new NumberFormatException();
                }

                if (!RSA.MillerRabinTest(new longAr(q), 130)) {
                    System.out.println("Ошибка! Вы ввели составное q!");
                    return;
                }

                RSA.generate(new longAr(p), new longAr(q));
            }
            else if (k == 1) {
                System.out.println("Введите e:");
                String e = reader.readLine();
                if (!longAr.isNumber(e)) {
                    throw new NumberFormatException();
                }
                System.out.println("Введите n:");
                String n = reader.readLine();
                if (!longAr.isNumber(n)) {
                    throw new NumberFormatException();
                }
                RSA.encryption(new longAr(e), new longAr(n));
            }
            else {
                System.out.println("Введите d:");
                String d = reader.readLine();
                if (!longAr.isNumber(d)) {
                    throw new NumberFormatException();
                }
                System.out.println("Введите n:");
                String n = reader.readLine();
                if (!longAr.isNumber(n)) {
                    throw new NumberFormatException();
                }
                RSA.decryption(new longAr(d), new longAr(n));
            }

        }
        catch (IOException | NumberFormatException e) {
            System.out.println("Ошибка! Некорректный ввод");
        }
    }
}