import java.util.ArrayList;
import java.util.List;

public class Task1 {
    private static final char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };
    private static final String USAGE = "usage";

    // Конвертация числа nb из 10-й системы числения в base
    String itoBase(int nb, String base) {
        return Integer.toString(nb, base.length());
    }

    // Конвертация числа nb из baseSrc системы числения в baseDst
    /*
        Сначала число nb конвентируем из baseSrc системы числения в 10-ю
        А затем из 10-й системы числения в baseDst
     */
    String itoBase(String nb, String baseSrc, String baseDst) {
        char[] num = nb.toLowerCase().toCharArray();
        final int BASE_SRC = baseSrc.length();
        int decimalNumber = 0;  //  Десятичное число
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < BASE_SRC; j++) {
                if (num[i] == digits[j]) {
                    decimalNumber += Math.pow(BASE_SRC, num.length - i - 1)
                            * Integer.parseInt(String.valueOf(j));
                }
            }
        }
        return itoBase(decimalNumber, baseDst);
    }

    // проверка корректности системы типа системы счисления
    private static boolean isBase(String base) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < base.length(); i++)
            str.append(digits[i]);
        return base.equalsIgnoreCase(str.toString());
    }

    // проверка корректности ввода числа
    private static boolean isNum(String s, int numberSystem) {
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < numberSystem & i < digits.length; i++) {
            list.add(digits[i]);
        }
        char[] num = s.toLowerCase().toCharArray();
        for (char c: num) {
            if (!list.contains(c))
                return false;
        }
        return true;
    }
    /* входящие данные метода main:
        args[0] - число
        args[1] - тип системы счисления
        args[2] - тип системы счисления (необязателен)
     */
    public static void main(String[] args) throws Exception {
        Task1 task1 = new Task1();
        if (args.length < 2 || args.length > 3 ||
                !isNum(args[0], args.length == 2? 10 : args[1].length())) {   //  Проверка корректности ввода
            System.out.println(USAGE);
            return;
        }
        for (int i = 1; i < args.length; i++) {
            if (!isBase(args[i])) {
                System.out.println(USAGE);
                return;
            }
        }
        System.out.println(args.length == 2? task1.itoBase(Integer.parseInt(args[0]), args[1]):
                        task1.itoBase(args[0], args[1], args[2]));
    }
}
