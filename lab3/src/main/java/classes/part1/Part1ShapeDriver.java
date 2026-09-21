package classes.part1;

import java.util.ArrayList;
import java.util.Arrays;

public class Part1ShapeDriver {

    public static void main(String[] args) {
        Circle[] circle = {
            new Circle(new Point(0, 0),  2),
            new Circle(new Point(1, 1), 1),
            new Circle(new Point(-1, -1), 3)
        };

        Rectangle[] rectangle = {
            new Rectangle(new Point(0, 0), new Point(1, 1)),
            new Rectangle(new Point(0, 0), new Point(3, 3)),
            new Rectangle(new Point(0, 0), new Point(10, 10)),
        };

        ArrayList<Double> computedList = new ArrayList<>();

        for(Circle c: circle) {
            computedList.add(Utility.area(c));
            computedList.add(Utility.perimeter(c));
        }

        for(Rectangle r: rectangle) {
            computedList.add(Utility.area(r));
            computedList.add(Utility.perimeter(r));
        }

        Arrays.stream(smallLarge(computedList)).forEach(System.out::println);

    }

    public static double[] smallLarge(ArrayList<Double> li) {
        var min = li.stream().min(Double::compareTo).orElse(Double.MAX_VALUE);
        var max = li.stream().max(Double::compareTo).orElse(Double.MIN_VALUE);
        return new double[]{min, max};
    }

}
