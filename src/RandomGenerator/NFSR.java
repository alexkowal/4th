package RandomGenerator;

import java.util.ArrayList;

public class NFSR extends Generator {
    private long p1;
    private long l1;
    private ArrayList<Long> initVector1;
    private ArrayList<Long> res1 = new ArrayList<>();
    private long p2;
    private long l2;
    private ArrayList<Long> initVector2;
    private ArrayList<Long> res2 = new ArrayList<>();
    private long p3;
    private long l3;
    private ArrayList<Long> initVector3;
    private ArrayList<Long> res3 = new ArrayList<>();
    ArrayList<Integer> res = new ArrayList<>();


    void init() {
        p1 = parametersNFSR.get(0).get(0);
        l1 = parametersNFSR.get(0).get(1);

        p2 = parametersNFSR.get(1).get(0);
        l2 = parametersNFSR.get(1).get(1);

        p3 = parametersNFSR.get(2).get(0);
        l3 = parametersNFSR.get(2).get(1);

        initVector1 = initVectorNFSR.get(0);
        initVector2 = initVectorNFSR.get(1);
        initVector3 = initVectorNFSR.get(2);
    }

    public ArrayList<Long> toBin(long a, long p) {
        ArrayList<Long> binarDig = new ArrayList<>();
        while (a != 0 && p > 0) {
            binarDig.add(a % 2);
            a /= 2;
            p--;
        }
        while (p > 0) {
            binarDig.add(0L);
            p--;
        }
        return binarDig;
    }

    @Override
    public String generate() {


        return out.toString();
    }
}
