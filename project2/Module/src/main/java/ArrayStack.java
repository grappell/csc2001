import java.util.ArrayList;
import java.util.stream.Stream;

public class ArrayStack<T> {

    private final ArrayList<T> li = new ArrayList<>();

    // Push a new element onto the stack.
    // Example: stack.push(291) --> returns void
    public void push(T item) {
        li.add(item);
    }

    // Pop an element off the stack
    // Example: stack.pop() --> 291
    public T pop() {
        return li.removeLast();
    }

    // Get the element on the top of the stack
    // Example: stack.peep() --> 291
    public T peek() {
        return li.getLast();
    }

    // Check if the stack is empty
    // Example: (on an empty stack) stack.isEmpty() --> true
    public boolean isEmpty() {
        return li.isEmpty();
    }

    // Get the size of the stack
    // Example: stack.size() --> 3
    public int size() {
        return li.size();
    }

    // Get a stream of the stack, from top to bottom
    // Example: stack.stream() --> Stream<T>
    public Stream<T> stream() {
        return li.reversed().stream();
    }

}
