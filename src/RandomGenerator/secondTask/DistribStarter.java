package RandomGenerator.secondTask;

import RandomGenerator.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistribStarter {
    static Distribution setType(List<String> params) {
        Distribution distribution = null;
        String type = "";
        for (String param : params) {
            if (param.contains("g:"))
                type = param.substring(param.indexOf(':') + 1);
        }

        switch (type) {
            case "":
                distribution = new Binomial();
                break;
            case "add":
                distribution = new Exponential();
                break;
            case "5p":
                distribution = new Gamma();
                break;
            case "lfsr":
                distribution = new Logistic();
                break;
            case "nfsr":
                distribution = new LogNormal();
                break;
            case "mt":
                distribution = new Normal();
                break;
            case "rc4":
                distribution = new Standart();
                break;
            case "rsa":
                distribution = new Triangle();
                break;
        }
        return distribution;
    }

    List<Double> getParams(List<String> params) {
        List<String> res = new ArrayList<>();
        String temp = "";

        for (String param : params) {
            if (param.contains("p:")) {
                temp = param.substring(param.indexOf(':') + 1, param.length());
            }
        }
        if (temp != "") {
            res = Arrays.asList(temp.split(" "));
            List<Double> ans = new ArrayList<>();
            for (String re : res) {
                ans.add(Double.parseDouble(re));
            }

            return ans;
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        DistribStarter s = new DistribStarter();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            List<String> params = Arrays.asList(br.readLine().split("/"));

            for (int i = 0; i < params.size(); i++) {
                if (params.get(i).equals(""))
                    params.set(i, "/z: ");
                if (params.get(i).charAt(params.get(i).length() - 1) == ' ') {
                    params.set(i, params.get(i).substring(0, params.get(i).length() - 1));
                }
            }
            Distribution distribution = setType(params);
            distribution.filename = "rnd.txt";
            distribution.parameters = (ArrayList<Double>) s.getParams(params);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
