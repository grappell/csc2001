package classes.part1;

public class Utility {

    public static double perimeter(Circle c) {
        return 2 * Math.PI * c.getRadius();
    }

    public static double area(Circle c) {
        return Math.PI * c.getRadius() * c.getRadius();
    }

    public static double perimeter(Rectangle r) {
       double a = r.getTopLeft().getX() - r.getBottomRight().getX();
       double b = r.getTopLeft().getY() - r.getBottomRight().getY();
       return Math.abs(2 * a) + Math.abs(2 * b);
    }

    public static double area(Rectangle r) {
        double a = r.getTopLeft().getX() - r.getBottomRight().getX();
        double b = r.getTopLeft().getY() - r.getBottomRight().getY();
        return Math.abs(a * b);
    }

}
