import java.util.Comparator;

public class Main {

    // Check all methods on the linked list
    public void main() {

        LinkedList<Session> ll = new LinkedList<>();

        var s1 = new Session(0, "topic1",  "mentor1", "dep1", "date1", "time1", "loc1", 100);
        var s2 = new Session(1, "topic2",  "mentor2", "dep2", "date2", "time2", "loc2", 100);
        var s3 = new Session(2, "topic3",  "mentor3", "dep3", "date3", "time3", "loc3", 100);
        var s4 = new Session(3, "topic4",  "mentor3", "dep4", "date4", "time4", "loc4", 100);

        ll.addFirst(s1);
        ll.addLast(s4);
        ll.insert(s3, Comparator.comparingInt(Session::getSessionID));
        ll.insert(s2, Comparator.comparingInt(Session::getSessionID));

        out("\n --- Elements inserted out of order --- ");
        ll.stream().forEach(this::out);

        out("\n --- Remove session with ID 1 --- ");
        ll.remove(s2);
        ll.stream().forEach(this::out);

        out("\n --- Get the last element in the list --- ");
        out(ll.getLast().getData());

        out("\n --- Get the first element in the list --- ");
        out(ll.getFirst().getData());

        out("\n --- Get the index of a specific element ---");
        out("Index of ID 1: " + ll.getIndex(s1));

        out("\n --- Get the first occurrence of a element (search) --- ");
        out("Get element with ID 3: " + ll.getFirst(e -> e.getSessionID() == 3).get().getData());

        out("\n --- Get an element at a specific index --- ");
        out("Element at index 2: " + ll.at(2).getData());

        out("\n --- Get the length of the LinkedList --- ");
        out("Length: " + ll.getLength());

        out("\n --- Append to the end of the LinkedList ---");

        LinkedList<Session> ll2 = new LinkedList<>();
        var n1 = new Session(100, "topic100",  "mentor100", "dep100", "date100", "time100", "loc100", 100);
        var n2 = new Session(200, "topic200",  "mentor200", "dep200", "date200", "time200", "loc200", 100);
        ll2.addLast(n1);
        ll2.addLast(n2);

        out("List to append: ");
        ll2.stream().forEach(this::out);

        out("The two lists appended:");
        ll.append(ll2);
        ll.stream().forEach(this::out);
    }

    // Generically write to output
    public <T> void out(T o) {
        System.out.println(o);
    }
}