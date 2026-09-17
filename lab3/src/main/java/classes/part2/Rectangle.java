package classes.part2;

import classes.part1.Point;

public class Rectangle {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public double perimeter() {
        double a = topLeft.getX() - bottomRight.getX();
        double b = topLeft.getY() - bottomRight.getY();
        return Math.abs(2 * a) + Math.abs(2 * b);
    }

    public double area() {
        double a = topLeft.getX() - bottomRight.getX();
        double b = topLeft.getY() - bottomRight.getY();
        return Math.abs(a * b);
    }
}
