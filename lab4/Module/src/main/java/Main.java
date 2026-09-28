public class Main {
    public static void main(String[] args) {
        IntArray arr = new IntArray();
        arr.addToEnd(1);
        arr.addToEnd(2);
        arr.addToEnd(3);
        arr.addToEnd(4);

        arr.stream().forEach(Main::out);

        out(" -------- ");

        out(arr.length());

        out(" -------- ");

        arr.remove(0);
        arr.stream().forEach(Main::out);

        out(" -------- ");
        arr.addToStart(5);
        arr.stream().forEach(Main::out);

        out(" -------- ");
        arr.insert(2, 84729);
        arr.insert(2, -84729);
        arr.stream().forEach(Main::out);
    }

    public static <T> void out(T o) {
        System.out.println(o);
    }
}
