import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ArrayStack<T> {

    private T[] arr = makeArray(2);
    private int ptr = 0;

    // Push a new element onto the stack.
    // Example: stack.push(291) --> returns void
    public void push(T item) {
        if(ptr == arr.length) {
            var temp = makeArray(arr.length * 2);
            System.arraycopy(arr, 0, temp, 0, arr.length);
            arr = temp;
        }
        arr[ptr++] = item;
    }

    // Pop an element off the stack
    // Example: stack.pop() --> 291
    public T pop() {
        if(isEmpty()) throw new NoSuchElementException("Stack is empty");
        return arr[--ptr];
    }

    // Get the element on the top of the stack
    // Example: stack.peep() --> 291
    public T peek() {
        if(isEmpty()) throw new NoSuchElementException("Stack is empty");
        return arr[ptr - 1];
    }

    // Check if the stack is empty
    // Example: (on an empty stack) stack.isEmpty() --> true
    public boolean isEmpty() {
        return ptr == 0;
    }

    // Get the size of the stack
    // Example: stack.size() --> 3
    public int size() {
        return ptr;
    }

    // Get a stream of the stack, from top to bottom
    // Example: stack.stream() --> Stream<T>
    public Stream<T> stream() {
        return List.of(Arrays.copyOfRange(arr, 0, ptr)).reversed().stream();
    }

    @SuppressWarnings("unchecked")
    private T[] makeArray(int len) {
        return (T[]) new Object[len];
    }

}
