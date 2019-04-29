package RandomGenerator.secondTask;

import RandomGenerator.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DistribStarter {
    static Distribution setType(List<String> params) {
        Distribution distribution = null;
        String type = "";
        for (String param : params) {
            if (param.contains("d:"))
                type = param.substring(param.indexOf(':') + 1);
        }

        switch (type) {
            case "bi":
                distribution = new Binomial();
                break;
            case "ex":
                distribution = new Exponential();
                break;
            case "gm":
                distribution = new Gamma();
                break;
            case "ls":
                distribution = new Logistic();
                break;
            case "ln":
                distribution = new LogNormal();
                break;
            case "nr":
                distribution = new Normal();
                break;
            case "st":
                distribution = new Standart();
                break;
            case "tr":
                distribution = new Triangle();
                break;
        }
        return distribution;
    }

    String getFileName(List<String> params) throws FileNotFoundException {
        String fileName = "";
        for (String param : params) {
            if (param.contains("f:")) {
                fileName = param.substring(param.indexOf(':') + 1);
            }
        }

        return fileName.equals("") ? "rnd.txt" : fileName;
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

        System.out.println("Examples:");
        System.out.println("Стандартное:/d:st /p:3 15 /f:rnd.txt ");
        System.out.println("Треугольное:/d:tr /p:3 15 /f:rnd.txt ");
        System.out.println("Экспоненциальное:/d:ex /p:3 15 /f:rnd.txt ");
        System.out.println("Нормальное:/d:nr /p:3 15 /f:rnd.txt ");
        System.out.println("Гамма:/d:gm /p:3 15 /f:rnd.txt ");
        System.out.println("Логнормальное:/d:ln /p:3 15 /f:rnd.txt ");
        System.out.println("Логистическое:/d:ls /p:3 15 /f:rnd.txt ");
        System.out.println("Биномиальное:/d:bi /p:0.3 100 /f:rnd.txt ");


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
            Distribution distribution;
            distribution = setType(params);
            String fn = s.getFileName(params);
            distribution.inputFile = fn;
            BufferedReader reader = new BufferedReader(new FileReader(distribution.inputFile));
            ArrayList<Double> input = new ArrayList<>();

            String temp ="";
            String t = "";

            while((t = reader.readLine())!=null)
            {
                temp+=t;
            }

            String[] split = temp.split(" ");
            for (String s1 : split) {
                input.add(Double.parseDouble(s1));
            }

            distribution.parameters = (ArrayList<Double>) s.getParams(params);
            distribution.inputArray = input;
            distribution.toDistribution();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
