package CryptoMethods;

import java.util.ArrayDeque;
import java.util.Deque;

public class Num implements Comparable<Num> {
    static long defaultBase = 3037000499L;
    long base;
    long[] arr;
    boolean isNegative;
    int len;

    /**
     * @param s
     */
    public Num(String s) {
        base = defaultBase;
        constructStringNum(s);
    }

    private void constructStringNum(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Needs a valid String argument");
        }

        if ((s.charAt(0) == '-') || (s.charAt(0) == '+')) {
            if (s.charAt(0) == '-') {
                isNegative = true;
            } else if (s.charAt(0) == '+') {
                isNegative = false;
            }
            s = s.substring(1, s.length());
            if (s.isEmpty()) {
                throw new IllegalArgumentException("Needs a valid String argument");
            }

        }

        long arrLength = (long) Math.ceil(((s.length() + 1) * (Math.log(10) / Math.log(base))) + 1);

        arr = new long[(int) arrLength];

        try {
            recursive(s, 0);
        } catch (Exception E) {
            E.printStackTrace();
            throw new IllegalArgumentException("Error in handling input");
        }

        len = getLengthWithoutLeadingZeros(arr);

        if ((len == 1) && (arr[0] == 0)) {
            isNegative = false;
        }
    }

    private long getBaseLength() {
        return Long.toString(base()).length();
    }

    private Num(long[] arr, int size, boolean isNegative, long base) {
        this.arr = arr;
        this.len = size;
        this.isNegative = isNegative;
        this.base = base;
    }

    private void recursive(String quotient, int index) {
        long quotientLength = quotient.length();

        if (quotientLength < 19) {
            if (Long.parseLong(quotient) == 0) {
                len = index;
                return;
            }
        }
        long baseLength = getBaseLength();
        long arrLength = 19 - baseLength;
        long temporaryLength = (quotientLength / arrLength) + ((quotientLength % arrLength) == 0 ? 0 : 1);
        long temporaryNumber;
        long temporaryNumber2;
        String temp;
        long remainder = 0;
        String quotientString = "";
        long remLength;
        long subLength;

        for (long i = 0; i < temporaryLength; i = i + 1) {

            subLength = i * arrLength;

            remLength = Long.toString(remainder).length();
            temp = Long.toString(remainder).concat(quotient.substring((int) (subLength), (int) ((subLength + arrLength) < quotientLength ? (subLength + arrLength) : quotientLength)));
            temporaryNumber = Long.valueOf(temp);

            // Quotient zero handling
            if (i != 0) {
                if (i != (temporaryLength - 1)) {
                    if (remainder == 0) {

                        int iter = 0;

                        temporaryNumber2 = Long.valueOf(quotient.substring((int) (subLength), (int) (subLength + iter + 1)));

                        while ((iter < (arrLength - 1)) && (temporaryNumber2 < base())) {

                            quotientString = quotientString.concat("0");
                            iter++;
                            temporaryNumber2 = Long.valueOf(quotient.substring((int) (subLength), (int) (subLength + iter + 1)));
                        }
                    } else if (baseLength != remLength) {
                        temporaryNumber2 = Long.valueOf(Long.toString(remainder).concat(quotient.substring((int) (subLength), (int) ((subLength + baseLength) - remLength))));
                        if (base() <= temporaryNumber2) {
                            for (int z = 0; z < (baseLength - remLength - 1); z++) {
                                quotientString = quotientString.concat("0");
                            }
                        } else {
                            for (int z = 0; z < (baseLength - remLength); z++) {
                                quotientString = quotientString.concat("0");
                            }

                        }
                    }
                } else if (i == (temporaryLength - 1)) {

                    if (remainder == 0) {

                        temporaryNumber2 = Long.valueOf(quotient.substring((int) (subLength), (int) (subLength + 1)));
                        int iter = (int) subLength + 1;
                        while ((iter < quotient.length()) && (temporaryNumber2 < base())) {
                            quotientString = quotientString.concat("0");
                            iter++;
                            temporaryNumber2 = Long.valueOf(quotient.substring((int) (subLength), (iter)));
                        }
                    } else if (baseLength != remLength) {

                        Long lastPart = Long.parseLong(Long.toString(remainder).concat(quotient.substring((int) subLength, (int) quotientLength)));
                        if (lastPart < base()) {
                            for (int z = 0; z < (quotient.substring((int) subLength, (int) quotientLength).length() - 1); z++) {
                                quotientString = quotientString.concat("0");
                            }

                        } else {
                            temporaryNumber2 = Long.valueOf(Long.toString(remainder).concat(quotient.substring((int) (subLength), (int) ((subLength + baseLength) - remLength))));
                            if (base() <= temporaryNumber2) {
                                for (int z = 0; z < (baseLength - remLength - 1); z++) {
                                    quotientString = quotientString.concat("0");
                                }
                            } else {
                                for (int z = 0; z < (baseLength - remLength); z++) {
                                    quotientString = quotientString.concat("0");
                                }

                            }
                        }
                    }

                }
            }

            remainder = temporaryNumber % base();

            if (!quotientString.isEmpty() && (quotientString.length() < 19) && (Long.parseLong(quotientString) == 0)) {
                quotientString = "";
            }
            quotientString = quotientString.concat(Long.toString(temporaryNumber / base()));

        }

        arr[index] = remainder;
        recursive(quotientString, index + 1);

    }

    private void constructLongNum(long x, long base) {
        this.base = base;
        if (x == Long.MIN_VALUE) {
            constructStringNum(Long.toString(x)); // Long.MIN_VALUE overflows. So process it as a string.
        } else {
            if (x == 0) {
                len = 1;
                arr = new long[len];
                arr[len - 1] = x;
            } else {
                this.isNegative = x < 0 ? true : false;
                int i = 0;
                x = Math.abs(x);
                len = getNumberLength(x);
                arr = new long[len];
                i = 0;
                while (x > 0) {
                    arr[i] = x % base;
                    x = x / base;
                    i = i + 1;
                }
            }
        }
    }

    public Num(long x) {
        constructLongNum(x, defaultBase);
    }

    /**
     * @param x
     * @param base
     */
    private Num(long x, long base) {
        constructLongNum(x, base);
    }

    private int getNumberLength(long x) {
        int i = 0;
        while (x > 0) {
            x = x / base();
            i = i + 1;
        }
        return i;
    }

    public static Num add(Num a, Num b) {
        if (isNumberZero(a)) {
            return b;
        }
        if (isNumberZero(b)) {
            return a;
        }
        boolean isNegative;
        if (isSignEqual(a, b)) {
            if (a.isNegative && b.isNegative) {
                isNegative = true;
            } else {
                isNegative = false;
            }
            long[] firstNumber = a.arr;
            long[] secondNumber = b.arr;
            int sizeOfNumA = a.len;
            int sizeOfNumB = b.len;
            int sizeOfSum = (sizeOfNumA > sizeOfNumB ? sizeOfNumA : sizeOfNumB) + 1;
            long[] sum = new long[sizeOfSum];
            long carry = 0;
            int i = 0;
            int j = 0;
            int k = 0;
            long base = a.base();
            while ((i < sizeOfNumA) && (j < sizeOfNumB)) {
                sum[k] = (firstNumber[i] + secondNumber[j] + carry) % base;
                carry = (firstNumber[i] + secondNumber[j] + carry) / base;
                i++;
                j++;
                k++;
            }
            if (i < sizeOfNumA) {
                while (i < sizeOfNumA) {
                    sum[k] = (firstNumber[i] + carry) % base;
                    carry = (firstNumber[i] + carry) / base;
                    i++;
                    k++;
                }
            } else if (j < sizeOfNumB) {
                while (j < sizeOfNumB) {
                    sum[k] = (secondNumber[j] + carry) % base;
                    carry = (secondNumber[j] + carry) / base;
                    j++;
                    k++;
                }
            }
            if (carry != 0) {
                sum[k] = carry;
            } else {
                k--;
            }
            return new Num(sum, k + 1, isNegative, base);
        } else {
            return subtract(a, negateNumber(b));
        }
    }

    private static boolean isSignEqual(Num a, Num b) {
        if ((a.isNegative && b.isNegative) || (!a.isNegative && !b.isNegative)) {
            return true;
        } else {
            return false;
        }
    }

    private static Num negateNumber(Num a) {
        return new Num(a.arr, a.len, !a.isNegative, a.base());
    }

    private static int unsignedCompare(Num a, Num b) {
        int sizeOfNumA = a.len;
        int sizeOfNumB = b.len;
        if (sizeOfNumA > sizeOfNumB) {
            return 1;

        } else if (sizeOfNumA < sizeOfNumB) {
            return -1;

        } else {
            int x = sizeOfNumA - 1;
            while (x >= 0) {
                if (a.arr[x] < b.arr[x]) {
                    return -1;
                } else if (a.arr[x] > b.arr[x]) {
                    return 1;
                }
                x--;
            }
            return 0;
        }
    }


    public static Num subtract(Num a, Num b) {
        if (isNumberZero(a)) {
            return negateNumber(b);
        }
        if (isNumberZero(b)) {
            return a;
        }

        if (a.compareTo(b) == 0) {
            return new Num(0, a.base());
        }

        if (isSignEqual(a, b)) {
            boolean isNegative = a.compareTo(b) == -1 ? true : false;
            int sizeOfNumA = a.len;
            int sizeOfNumB = b.len;
            int sizeOfDifference, firstNumberLength, secondNumberLength;
            long base = a.base();
            long[] firstNumber;
            long[] secondNumber;
            if (unsignedCompare(a, b) == -1) {
                sizeOfDifference = sizeOfNumB;
                firstNumber = b.arr;
                secondNumber = a.arr;
                firstNumberLength = sizeOfNumB;
                secondNumberLength = sizeOfNumA;
            } else {
                sizeOfDifference = sizeOfNumA;
                firstNumber = a.arr;
                secondNumber = b.arr;
                firstNumberLength = sizeOfNumA;
                secondNumberLength = sizeOfNumB;
            }
            long[] difference = new long[sizeOfDifference];
            int i = 0;
            int j = 0;
            int k = 0;
            long carry = 0;
            while ((i < firstNumberLength) && (j < secondNumberLength)) {
                if ((firstNumber[i] - carry) >= secondNumber[j]) {
                    difference[k] = firstNumber[i] - secondNumber[j] - carry;
                    carry = 0;
                } else {
                    difference[k] = (firstNumber[i] + base) - secondNumber[j] - carry;
                    carry = 1;
                }
                i++;
                j++;
                k++;

            }
            while (i < firstNumberLength) {
                if ((firstNumber[i] - carry) >= 0) {
                    difference[k] = firstNumber[i] - carry;
                    carry = 0;
                } else {
                    difference[k] = (firstNumber[i] + base) - carry;
                    carry = 1;
                }
                i++;
                k++;
            }
            return new Num(difference, getLengthWithoutLeadingZeros(difference), isNegative, base);
        } else {
            return add(a, negateNumber(b));
        }

    }

    private static int getLengthWithoutLeadingZeros(long[] a) {
        int len = a.length;
        int i = len - 1;
        int count = 0;
        while ((i > 0) && (a[i] == 0)) {
            count++;
            i--;
        }
        return len - count;
    }

    public static Num product(Num a, Num b) {
        if (isNumberZero(a) || isNumberZero(b)) {
            return new Num(0, a.base());
        }
        boolean isNegative = isSignEqual(a, b) ? false : true;
        int sizeOfLargerNum;
        int sizeOfSmallerNum;
        int unsignedCompare = unsignedCompare(a, b);
        long[] firstNumber;
        long[] secondNumber;
        long base = a.base();
        if (unsignedCompare == -1) {
            firstNumber = b.arr;
            sizeOfLargerNum = b.len;
            secondNumber = a.arr;
            sizeOfSmallerNum = a.len;

        } else {
            firstNumber = a.arr;
            sizeOfLargerNum = a.len;
            secondNumber = b.arr;
            sizeOfSmallerNum = b.len;

        }
        int sizeOfProduct = sizeOfLargerNum + sizeOfSmallerNum;
        long[] product = new long[sizeOfProduct];
        long carry = 0;
        int k = 0;
        long prod;
        for (int i = 0; i < sizeOfLargerNum; i++) {
            carry = 0;
            for (int j = 0; j < sizeOfSmallerNum; j++) {
                k = i + j;
                prod = product[k] + (firstNumber[i] * secondNumber[j]) + carry;
                product[k] = prod % base;
                carry = prod / base;
            }
            if (carry != 0) {
                product[k + 1] = product[k + 1] + carry;
            }
        }
        if (carry != 0) {
            k++;
            product[k] = carry;
        }
        if ((a.isNegative && b.isNegative) || (!a.isNegative && !b.isNegative)) {
            isNegative = false;
        } else {
            isNegative = true;
        }

        return new Num(product, k + 1, isNegative, base);
    }


    public static Num divide(Num a, Num b) {
        Num zero = new Num(0, a.base());
        Num one = new Num(1, a.base());
        Num minus_one = new Num(-1, a.base());

        Num sign = null;
        if (isSignEqual(a, b)) {
            sign = one;
        } else {
            sign = minus_one;
        }

        Num dividend = a.isNegative ? negateNumber(a) : a;
        Num divisor = b.isNegative ? negateNumber(b) : b;

        if (divisor.compareTo(zero) == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        if (divisor.compareTo(one) == 0) {
            return product(dividend, sign);
        }
        if (divisor.compareTo(dividend) == 0) {
            return sign;
        }
        if (divisor.compareTo(dividend) > 0) {
            return zero;
        }
        Num low = zero;
        Num high = dividend;
        while (true) {
            Num quotient = add(low, (subtract(high, low)).by2());
            Num divisor_quotient_product = product(divisor, quotient);
            Num remainder = subtract(divisor_quotient_product, dividend);
            if (remainder.isNegative) {
                remainder.isNegative = false;
                if ((remainder).compareTo(divisor) < 0) {
                    return product(quotient, sign);
                }
            } else {
                if ((remainder).compareTo(zero) == 0) {
                    return product(quotient, sign);
                }
            }

            if (divisor_quotient_product.compareTo(dividend) == -1) {
                low = quotient;
            } else {
                high = quotient;
            }
        }
    }

    private static boolean isNumberZero(Num a) {
        return (a.len == 1) && (a.arr[0] == 0);
    }

    public static Num mod(Num dividend, Num divisor) {
        Num zero = new Num(0, dividend.base());
        Num one = new Num(1, dividend.base());
        if (divisor.compareTo(zero) == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        if (divisor.compareTo(one) == 0) {
            return zero;
        }
        if (divisor.compareTo(dividend) == 0) {
            return zero;
        }
        if (divisor.compareTo(dividend) > 0) {
            return dividend;
        }
        Num low = zero;
        Num high = dividend;
        while (true) {
            Num quotient = add(low, ((subtract(high, low)).by2()));
            Num divisor_quotient_product = product(divisor, quotient);
            Num remainder = subtract(divisor_quotient_product, dividend);
            if (remainder.isNegative) {
                remainder.isNegative = false;
                if (remainder.compareTo(divisor) < 0) {
                    return remainder;
                }
            } else {
                if ((remainder).compareTo(zero) == 0) {
                    return remainder;
                }
            }

            if (divisor_quotient_product.compareTo(dividend) == -1) {
                low = quotient;
            } else {
                high = quotient;
            }
        }
    }

    @Override
    public int compareTo(Num other) {
        if ((this.isNegative && other.isNegative) || (!this.isNegative && !other.isNegative)) {
            if (this.len > other.len) {
                return (this.isNegative && other.isNegative) ? -1 : 1;
            } else if (this.len == other.len) {
                int i = this.len - 1;
                int isEqual = 0;
                while (i >= 0) {
                    if (this.arr[i] < other.arr[i]) {
                        isEqual = (this.isNegative && other.isNegative) ? 1 : -1;
                        break;
                    } else if (this.arr[i] > other.arr[i]) {
                        isEqual = (this.isNegative && other.isNegative) ? -1 : 1;
                        break;
                    }
                    i--;
                }
                return isEqual;
            } else {
                return (this.isNegative && other.isNegative) ? 1 : -1;
            }
        } else if (this.isNegative) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        Num n = convertBase(1000000000);
        String one_zero = "0";
        String two_zero = "00";
        String three_zero = "000";
        String four_zero = "0000";
        String five_zero = "00000";
        String six_zero = "000000";
        String seven_zero = "0000000";
        String eight_zero = "00000000";

        StringBuilder sb = new StringBuilder();
        if (n.isNegative && !isNumberZero(n)) {
            sb.append('-');
        }
        for (int i = n.len - 1; i >= 0; i--) {
            String temp = Long.toString(n.arr[i]);
            int tempLength = temp.length();
            int zeroesToAppend = 9 - tempLength;
            if (i != n.len - 1 && zeroesToAppend > 0) {
                switch (zeroesToAppend) {
                    case 1:
                        sb.append(one_zero);
                        break;
                    case 2:
                        sb.append(two_zero);
                        break;
                    case 3:
                        sb.append(three_zero);
                        break;
                    case 4:
                        sb.append(four_zero);
                        break;
                    case 5:
                        sb.append(five_zero);
                        break;
                    case 6:
                        sb.append(six_zero);
                        break;
                    case 7:
                        sb.append(seven_zero);
                        break;
                    case 8:
                        sb.append(eight_zero);
                        break;
                    default:
                        break;
                }
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    public long base() {
        return base;
    }

    public Num convertBase(int newBase) {
        if (newBase != base) {
            int i = this.len - 1;
            Num newNum = new Num(this.arr[i], newBase);
            Num baseNum = new Num(base(), newBase);
            while (i > 0) {
                Num prodNum = product(newNum, baseNum);
                newNum = add(prodNum, new Num(this.arr[i - 1], newBase));
                i--;
            }
            newNum.isNegative = this.isNegative;
            return newNum;
        } else {
            return this;
        }
    }

    public Num by2() {
        long carry = 0;
        long[] newArr = new long[this.len];
        for (int i = this.len - 1; i >= 0; i--) {
            long remainder = this.arr[i] + (carry * base());
            newArr[i] = remainder >> 1;
            carry = remainder - (newArr[i] * 2);
        }

        return new Num(newArr, getLengthWithoutLeadingZeros(newArr), this.isNegative, base());
    }


    public static void main(String[] args) {
        Num a = new Num(1234567890);
        Num b = new Num(987654321);
        System.out.println(divide(a, b));
        System.out.println(mod(a, b));
        System.out.println(add(a,b));
    }
}