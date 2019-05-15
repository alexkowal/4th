package ProgrammDefender.third;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Генерация ключей - (1)  " + "Шифрование - (2)  " + "Дешифрование - (3)");
        Integer var;
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
        var = scanner.nextInt();
        switch (var) {
            case (1): {
                System.out.println("Введите колличество ");
                Integer count = scanner.nextInt();
                Integer length = 21;
                Random random = new Random();
                KeyGenerator keyGenerator = new KeyGenerator(length / 3, count);
                keyGenerator.PublicKey();
                break;
            }
            case (2): {
                System.out.println("Введите ключ");
                String message = "";
                BufferedReader br = new BufferedReader(new FileReader("message.txt"));
                FileWriter fw = new FileWriter("encrypt.txt");
                String tmp = "";
                while ((tmp = br.readLine()) != null)
                    message += tmp;

                String key = scanner.next();
                String privateKey = " ";
                for (int i = 0; i < key.length(); i += 3) {
                    String num = "";
                    num += key.charAt(i);
                    num += key.charAt(i + 1);
                    num += key.charAt(i + 2);
                    privateKey += String.valueOf(Integer.valueOf(num) % 26);
                }
                Cipher cipher = new Cipher(privateKey);
                fw.write(cipher.encrypt(message));
                System.out.println(cipher.encrypt(message));
                fw.close();
                break;
            }
            case (3): {
                System.out.println("Введите ключ");
                BufferedReader br = new BufferedReader(new FileReader("encrypt.txt"));
                FileWriter fw = new FileWriter("decrypt.txt");
                String tmp = "";
                String message = "";
                while ((tmp = br.readLine()) != null)
                    message += tmp;

                String key = scanner.next();
                String privateKey = " ";
                for (int i = 0; i < key.length(); i += 3) {
                    String num = "";
                    num += key.charAt(i);
                    num += key.charAt(i + 1);
                    num += key.charAt(i + 2);
                    privateKey += String.valueOf(Integer.valueOf(num) % 26);
                }
                Cipher cipher = new Cipher(privateKey);
                fw.write(cipher.decrypt(message));
                fw.close();
                break;
            }
            default:
                break;
        }
    }
}
