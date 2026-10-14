import java.util.Comparator;
import java.util.stream.Stream;

public interface Stack<T> {
     void push(T item, Comparator<? super T> comparator);
     T pop();
     T peek();
     boolean isEmpty();
     int size();
     Stack<T> emptyStack();
     Stream<T> stream();
}
