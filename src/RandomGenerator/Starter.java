package RandomGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Starter {

    FileWriter fs;

    static Generator setType(List<String> params) {
        Generator g = null;
        String type = "";
        for (String param : params) {
            if (param.contains("g:"))
                type = param.substring(param.indexOf(':') + 1);
        }

        switch (type) {
            case "lc":
                g = new LinearSequence();
                break;
            case "add":
                g = new AdditiveMethod();
                break;
            case "5p":
                g = new Param5();
                break;
            case "lfsr":
                g = new LFSR();
                break;
            case "nfsr":
                g = new NFSR();
                break;
            case "mt":
                g = new AdditiveMethod();
                break;
            case "rc4":
                g = new RC4();
                break;
            case "rsa":
                g = new RSA();
                break;
            case "bbs":
                g = new BBS();
                break;
            default:
                g = new AdditiveMethod();
        }
        return g;
    }

    Long getCount(List<String> params) {
        Long n = 0l;
        for (String param : params) {
            if (param.contains("n:")) {
                String count = param.substring(param.indexOf(':') + 1);
                n = Long.parseLong(count);
            }
        }
        return n == 0l ? 10000 : n;
    }

    String getFileName(List<String> params) {
        String fileName = "";
        for (String param : params) {
            if (param.contains("f:")) {
                fileName = param.substring(param.indexOf(':') + 1);
            }
        }
        return fileName.equals("") ? "rnd.txt" : fileName;
    }

    List<Long> getParams(List<String> params) {
        List<String> res = new ArrayList<>();
        String temp = "";

        for (String param : params) {
            if (param.contains("p:")) {
                temp = param.substring(param.indexOf(':') + 1, param.length());
            }
        }
        if (temp != "") {
            res = Arrays.asList(temp.split(" "));
            List<Long> ans = new ArrayList<>();
            for (String re : res) {
                ans.add(Long.parseLong(re));
            }

            return ans;
        }
        return new ArrayList<>();
    }

    List<Long> getInitVector(List<String> params) {
        List<String> res = new ArrayList<>();
        String temp = "";
        boolean flag = false;
        for (String param : params) {
            if (param.contains("i:")) {
                temp = param.substring(param.indexOf(':') + 1, param.length());
                flag = true;
            }

        }
        if (flag) {
            res = Arrays.asList(temp.split(" "));
            List<Long> ans = new ArrayList<>();
            for (String re : res) {
                ans.add(Long.parseLong(re));
            }

            return ans;
        } else
            return null;
    }


    public static void main(String[] args) throws IOException {


        Starter s = new Starter();
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

            Generator g = setType(params);
            g.fileName = "rnd.txt";
            g.n = s.getCount(params) != 0 ? s.getCount(params) : 10000;
            s.fs = new FileWriter(s.getFileName(params));
            if (g.getClass() == NFSR.class) {
                g.parametersNFSR = s.getParamsNFSR(params);
                g.initVectorNFSR = s.getInitVectorNFSR(params);
            }

            g.parameters = s.getParams(params);
            if (s.getInitVector(params) != null)
                g.initVector = s.getInitVector(params);
            s.fs.write(g.generate());
        } finally {
            s.fs.close();
        }
    }

    private ArrayList<ArrayList<Long>> getParamsNFSR(List<String> params) {
        ArrayList<ArrayList<Long>> parameters = new ArrayList<>();
        parameters.add(new ArrayList<>());
        parameters.add(new ArrayList<>());
        parameters.add(new ArrayList<>());

        for (String param : params) {
            if (param.contains("p:")) {
                String[] dig = param.split(" ");
                int position = 0;
                for (int i = 0; i < dig.length; i++) {
                    if (dig[i].equals("|"))
                        position++;
                    if (dig[i].contains(":"))
                        parameters.get(position).add(Long.parseLong(dig[i].substring(dig[i].indexOf(':') + 1, dig[i].length())));
                    try {
                        parameters.get(position).add(Long.parseLong(dig[i]));
                    } catch (Exception e) {

                    }
                }
            }
        }
        return parameters;
    }

    private ArrayList<ArrayList<Long>> getInitVectorNFSR(List<String> initVec) {
        ArrayList<ArrayList<Long>> vector = new ArrayList<>();
        vector.add(new ArrayList<>());
        vector.add(new ArrayList<>());
        vector.add(new ArrayList<>());

        for (String param : initVec) {
            if (param.contains("i:")) {
                String[] dig = param.split(" ");
                int position = 0;
                for (int i = 0; i < dig.length; i++) {
                    if (dig[i].equals("|"))
                        position++;
                    if (dig[i].contains(":"))
                        vector.get(position).add(Long.parseLong(dig[i].substring(dig[i].indexOf(':') + 1, dig[i].length())));
                    try {
                        vector.get(position).add(Long.parseLong(dig[i]));
                    } catch (Exception e) {
                    }
                }
            }
        }
        return vector;
    }
}
