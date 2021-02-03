import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task3 {
    public static final String USAGE = "usage";
    private static int size;
    private static int volume;
    private static boolean isFindDate = false;

    public static void main(String[] args) throws IOException, ParseException {
        if (args.length != 3) {
            System.out.println(USAGE);
            return;
        }

        String s2 = "2020-01-01T12:00:00 2020-01-01T13:00:00";
        SimpleDateFormat dateReadFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss");
        Date date1 = dateFormat.parse(args[1]);
        Date date2 = dateFormat.parse(args[2]);
        if (date1.getTime() >= date2.getTime()) {
            System.out.println(USAGE);
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(new File(args[0])));
        reader.readLine();
        size = Integer.parseInt(reader.readLine());
        volume = Integer.parseInt(reader.readLine());
        int iTop = 0;
        int iScoop = 0;
        int iErrorTop = 0;
        int iErrorScoop = 0;
        int volStart = 0;
        int volEnd = 0;
        int onTop = 0;
        int noTop = 0;
        int onScoop = 0;
        int noScoop = 0;
        while (reader.ready()) {
            String s = reader.readLine();
            String str = s.substring(0, s.indexOf('Z') + 1);
            Date date = dateReadFormat.parse(str);
            if (!isFindDate && date.getTime() >= date1.getTime()){
                isFindDate = true;
                volStart = volume;
            }
            boolean isWanna = s.contains("top up");
            String litr = isWanna? s.substring(s.indexOf("top up") + 7, s.lastIndexOf("l"))
                    : s.substring(s.indexOf("scoop") + 6, s.lastIndexOf("l"));
            int vol = Integer.parseInt(litr);
            if (isFindDate) {
                if (isWanna) {
                    iTop++;
                    if (isTop(vol)) {
                        volume += vol;
                        onTop += vol;
                    } else {
                        noTop += vol;
                        iErrorTop++;
                    }
                } else {
                    iScoop++;
                    if (isScoop(vol)) {
                        volume -= vol;
                        onScoop += vol;
                    } else {
                        noScoop += vol;
                        iErrorScoop++;
                    }
                }
            } else {
                if (isWanna && isTop(vol)) {
                    volume += vol;
                } else if (!isWanna && isScoop(vol)) {
                    volume -= vol;
                }
            }
            if (isFindDate && date.getTime() > date2.getTime()) {
                volEnd = volume;
                isFindDate = false;
                break;
            }
        }
        if (isFindDate) {
            volEnd = volume;
        }

        System.out.println("За указанный период:");
        System.out.println("Количество попыток налить воду в бочку = " + iTop);
        System.out.println("Процент ошибок налить воду в бочку = " + iErrorTop * 100 / iTop + "%");
        System.out.println("Объем воды, который был налит в бочку = " + onTop);
        System.out.println("Объем воды, который не был налит в бочку = " + noTop);
        System.out.println("Количество попыток вычерпать воду из бочки = " + iScoop);
        System.out.println("Процент ошибок вычерпать воду из бочки = " + iErrorScoop * 100 / iScoop + "%");
        System.out.println("Объем воды, который был вычерапн из бочки = " + onScoop);
        System.out.println("Объем воды, который не был вычерпан из бочки = " + noScoop);
        System.out.println("Объем воды в бочке в начале указанного периода = " + volStart);
        System.out.println("Объем воды в бочке в конце указанного периода = " + volEnd);
    }

    private static boolean isTop(int vol) {
        if (volume + vol > size) {
            return false;
        }
        return true;
    }

    private static boolean isScoop(int vol) {
        if (volume - vol < 0) {
            return false;
        }
        return true;
    }
}
