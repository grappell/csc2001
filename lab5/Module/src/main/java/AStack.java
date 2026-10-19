import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

public class AStack<T> extends Testable<T> {

    private final ArrayList<T> list = new ArrayList<>();

    // Push a new element onto the stack.
    // Example: stack.push(291) --> returns void
    public void push(T item, Comparator<? super T> comparator) {
        for (int i = 0; i < list.size(); i++) {
            if(comparator.compare(list.get(i), item) >= 0) {
                list.add(i, item);
                return;
            }
        }
        list.add(item);
    }

    // Pop an element off the stack
    // Example: stack.pop() --> 291
    public T pop() {
        if(isEmpty()) throw new NoSuchElementException("Stack is empty");
        return list.removeLast();
    }

    // Get the element on the top of the stack
    // Example: stack.peep() --> 291
    public T peek() {
        if(isEmpty()) throw new NoSuchElementException("Stack is empty");
        return list.getLast();
    }

    // Check if the stack is empty
    // Example: (on an empty stack) stack.isEmpty() --> true
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Get the size of the stack
    // Example: stack.size() --> 3
    public int size() {
        return list.size();
    }

    // Get a stream of the stack, from top to bottom
    // Example: stack.stream() --> Stream<T>
    public Stream<T> stream() {
        return list.stream();
    }

    // Creates and returns a new, untouched stack
    // Example: AStack.emptyStack() -> ASack{};
    public static AStack<String> emptyStack() {
        return new AStack<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AStack<?> other)) return false;
        return Objects.equals(list, other.list);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(list);
    }

}
