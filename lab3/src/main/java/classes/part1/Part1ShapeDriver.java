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
        final double[] large = {Integer.MIN_VALUE};
        final double[] small = {Integer.MAX_VALUE};

        li.forEach(e -> {
            if(e > large[0]) large[0] = e;
            if(e < small[0]) small[0] = e;
        });

        return new double[]{small[0], large[0]};

    }

}
