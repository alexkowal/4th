package RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public abstract class Generator {

    List<Long> parameters = new ArrayList<>();
    String fileName = "rnd.txt";
    Long n;
    StringBuilder out = new StringBuilder();
    List<Long> initVector = new ArrayList<>();
    ArrayList<ArrayList<Long>> parametersNFSR = new ArrayList<ArrayList<Long>>();
    ArrayList<ArrayList<Long>> initVectorNFSR = new ArrayList<ArrayList<Long>>();

    public abstract String generate();

    public abstract String help();
}
