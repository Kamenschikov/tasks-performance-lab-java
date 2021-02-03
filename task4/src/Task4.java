import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4 {
    // сравнение строк через регулярные выражения
    static private String strComparison(String s1, String s2) {
        Pattern pattern = Pattern.compile(s2.replace("*", ".*"));
        Matcher matcher = pattern.matcher(s1);
        String str = "";
        while (matcher.find()) {
            str = s1.substring(matcher.start(), matcher.end());
            if (str.equals(s1)) {
                return "OK";
            }
        }
        return "KO";
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println(strComparison(args[0], args[1]));
        }
    }
}
