package ProgrammDefender.third;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KeyGenerator {

    private List privateKey = new ArrayList();
    private Integer count;
    private Integer length;
    private String alphabet;

    {
        for (Character c = 'A'; c <= 'Z'; c++) {
            alphabet += c.toString();
        }
        for (Character c = 'a'; c <= 'z'; c++) {
            alphabet += c.toString();
        }
        for (Character c = '0'; c <= '9'; c++) {
            alphabet += c.toString();
        }
    }

    public KeyGenerator(Integer length, Integer count) {
        Random random = new Random();
        this.count = count;
        this.length = length;
        for (int i = 0; i < length; i++) {
            Integer a = 104 + Math.abs(random.nextInt()) % alphabet.length();
            privateKey.add(a);
        }
    }

    public void PublicKey() throws IOException {
        Random random = new Random();
        FileWriter fw = new FileWriter("keys.txt");
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < length; j++) {
                Integer k = j + 78 * (3 + Math.abs(random.nextInt()) % 4);
                fw.write(k.toString());
            }
            fw.write("\n");
        }
        fw.close();
    }
}
