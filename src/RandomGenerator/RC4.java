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
    @Override
    public String help() {
        System.out.println("Пример ввода: ");
        System.out.println("/g:rc4 /i: 171 146 99 196 219 238 21 146 99 66 41 135 140 92 122 219 106 172 19 175 200 44 90 195 116 " +
                "148 156 88 33 230 225 169 227 162 81 45 29 95 40 137 202 2 22 247 203 3 45 168 52 237 " +
                "237 129 251 130 235 141 120 152 103 63 213 86 200 191 100 239 61 84 235 188 9 13 124 24 207 " +
                "220 254 49 21 93 175 169 123 153 12 135 177 79 41 1 9 93 95 24 123 30 46 62 158 220 195 185 72 " +
                "152 41 174 3 205 9 109 65 37 85 198 25 93 119 247 208 18 167 246 209 123 237 205 185 182 229 225 " +
                "19 135 189 19 248 43 24 215 71 69 130 143 65 133 176 150 140 68 142 207 46 247 34 169 196 9 162 235 " +
                "141 58 193 250 245 167 3 144 70 7 40 91 186 172 59 83 205 181 109 38 184 113 5 112 50 40 245 56 87 63 49 " +
                "106 26 140 182 233 47 231 137 65 80 188 12 184 233 171 6 42 183 54 162 215 143 145 203 248 189 236 11 214 " +
                "116 146 201 181 166 123 227 11 70 63 3 1 71 106 199 115 239 175 188 73 12 157 102 63 68 246 36 239 100 26 " +
                "115 57 190 95 64 222 227 176 /n:10000 /f: random.txt\n");

        System.out.println("Введите параметры: инициализирующий вектор из чисел от 1 до 256");
        return "";
    }
}


