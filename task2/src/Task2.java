import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
    private static final String USAGE = "usage";

    //  Поиск пересечения сферы и линии
    private String findIntersection(Sphere sphere, Line line) {
        Coord diffLine = line.getB().diffCoord(line.getA());
        Coord diffLineSphere = line.getA().diffCoord(sphere.getCentre());
        double a = diffLine.sumMultiCoord(diffLine);
        double b = 2 * diffLine.sumMultiCoord(diffLineSphere);
        double c = diffLineSphere.sumMultiCoord(diffLineSphere) - sphere.getRadius() * sphere.getRadius();
        double d = b * b - 4 * a * c;
        if (d < 0) return "Коллизий не найдено";
        if (d == 0) {
            double t = (-1 * b) / (2 * a);
            Coord coord = diffLine.coordIntersection(line.getA(), t);
            return coord.toString();
        }
        double t1 = (-1 * b + Math.sqrt(d)) / (2 * a);
        double t2 = (-1 * b - Math.sqrt(d)) / (2 * a);
        Coord coord1 = diffLine.coordIntersection(line.getA(), t1);
        Coord coord2 = diffLine.coordIntersection(line.getA(), t2);
        return coord1.toString() + ", " + coord2.toString();
    }

    private Line createLine(String str) {
        Coord[] coord = findCoords(str);
        return new Line(coord[0], coord[1]);
    }

    private Sphere createSphere(String str) {
        Coord centre = findCoords(str)[0];
        int indexRadius = str.indexOf("radius");
        int indexRadiusEnd = str.indexOf(',', indexRadius);
        String radius = str.substring(str.indexOf(':', indexRadius) + 1,  indexRadiusEnd != -1? indexRadiusEnd: str.length());
        return new Sphere(centre, Double.parseDouble(radius));
    }

    //  Поиск координат на выделенном участке строки
    private Coord[] findCoords(String coord) {
        Pattern pattern = Pattern.compile("\\[.*?]");
        Matcher matcher = pattern.matcher(coord);
        List<Coord> coordList = new ArrayList<>();
        while (matcher.find()) {
            coordList.add(createCoord(coord.substring(matcher.start() + 1, matcher.end() - 1)));
        }
        Coord[] coords = new Coord[coordList.size()];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = coordList.get(i);
        }
        return coords;
    }

    //  Пример входящих данных - " 1.0, 2, 6,5"
    private Coord createCoord(String strCoord) {
        String[] coord = strCoord.split(",");
        double[] xyz = new double[3];
        for (int j = 0; j < 3; j++) {
            xyz[j] = Double.parseDouble(coord[j]);
        }
        return new Coord(xyz[0], xyz[1], xyz[2]);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(USAGE);
            return;
        }

        Task2 task2 =new Task2();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(args[0])))) {
            while (bufferedReader.ready()) {
                String s = bufferedReader.readLine();
                int indexLine = s.indexOf("line");
                Line line = task2.createLine(s.substring(s.indexOf('{', indexLine) + 1, s.indexOf('}', indexLine)));
                int indexSphere = s.indexOf("sphere");
                Sphere sphere = task2.createSphere(s.substring(s.indexOf('{', indexSphere) + 1, s.indexOf('}', indexSphere)));
                System.out.println(task2.findIntersection(sphere, line));
            }
        } catch (Exception e) {
            System.out.println(USAGE);
        }

    }
}

class Sphere {
    private Coord centre;
    private double radius;

    public Sphere(Coord centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }

    public Coord getCentre() {
        return centre;
    }

    public double getRadius() {
        return radius;
    }
}

class Line {
    private Coord a;
    private Coord b;

    public Line(Coord a, Coord b) {
        this.a = a;
        this.b = b;
    }

    public Coord getA() {
        return a;
    }

    public Coord getB() {
        return b;
    }
}

class Coord {
    private double x;
    private double y;
    private double z;

    public Coord(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //  Нахождение разности координат
    public Coord diffCoord(Coord coord) {
        return new Coord(this.x - coord.x, this.y - coord.y, this.z - coord.z);
    }

    //  Нахождение суммы произведения координат
    public double sumMultiCoord(Coord coord) {
        return this.x * coord.x + this.y * coord.y + this.z * coord.z;
    }

    //  Нахождение координаты пересечения
    public Coord coordIntersection(Coord coord, double t) {
        double x = this.x  * t + coord.x;
        double y = this.y  * t + coord.y;
        double z = this.z  * t + coord.z;
        return new Coord(x, y, z);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + ']';
    }
}