package classes.part2;

import classes.part1.Point;

public class Circle {

    private final Point center;
    private final double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }

}
