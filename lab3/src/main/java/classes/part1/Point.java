package classes.part1;

public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double val) {
        x = val;
    }

    public void setY(double val) {
        y = val;
    }

    public void rotate() {
        var temp = x;
        x = -y;
        y = temp;
    }
}
