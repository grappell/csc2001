import java.util.List;

public class Main {
    public static void main(String[] args) {

        IntArray arr = new IntArray();
        arr.addToEnd(1);
        arr.addToEnd(2);
        arr.addToEnd(3);
        arr.addToEnd(4);

        out("Starting: ");
        arr.stream().forEach(Main::out);

        out(" -------- ");

        out("Len: " + arr.length());

        out(" -------- ");

        arr.remove(0);
        out("Remove:");
        arr.stream().forEach(Main::out);

        out(" -------- ");
        arr.addToStart(5);
        out("Add to start: ");
        arr.stream().forEach(Main::out);

        out(" -------- ");
        arr.insert(2, 84729);
        arr.insert(2, -84729);
        out("Insert:");
        arr.stream().forEach(Main::out);

        out(" -------- ");
        arr.remove(2);
        arr.set(2, 80085);
        out("Remove + set: ");
        arr.stream().forEach(Main::out);

        out(" -------- ");
        var temp = new IntArray(List.of(5, 2, 80085, 3, 4, 10));
        out("Elts: " + arr.equalElts(temp));
        out("Equals: " + arr.equals(temp));

        out(" -------- ");
        out("Get: " + arr.get(2));

    }

    public static <T> void out(T o) {
        System.out.println(o);
    }
}
