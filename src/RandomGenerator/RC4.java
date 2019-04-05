package RandomGenerator;

import java.util.ArrayList;

import static com.sun.tools.javac.jvm.ByteCodes.swap;

public class RC4 extends Generator {

    ArrayList<Integer> S = new ArrayList();

    ArrayList<Integer> K = new ArrayList();


    @Override
    public String generate() {

        ArrayList<Integer> si = new ArrayList<>();

        for (int i = 0; i < 256; i++) {
            si.add(i);
        }

        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (int) (((long) (j + si.get(i)) + this.initVector.get(i)) % 256);
            int temp = si.get(i);
            si.set(i, si.get(j));
            si.set(j, temp);
        }

        int i = 0;
        j = 0;
        for (int l = 0; l < n; l++) {
            i = (i + 1) % 256;
            j = (j + si.get(i)) % 256;
            int temp = si.get(i);
            si.set(i, si.get(j));
            si.set(j, temp);
            long t = (si.get(i) + si.get(j)) % 256;
            long k = si.get(Math.toIntExact(t));
            out.append(k + " ");
        }
        return out.toString();
    }
}


