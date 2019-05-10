package CryptoMethods.second;

import CryptoMethods.longAr;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Starter {

    public static void main(String[] args) throws IOException {
        try (FileWriter fileWriter = new FileWriter("primes.txt")){

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите число бит в p:");
            int kp = Integer.parseInt(reader.readLine());
            longAr p = Generator.generate(kp);
            if (Generator.MillerRabinTest(p, kp))
                fileWriter.write("p: " + p.toString() );
        }
    }
}
