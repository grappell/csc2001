import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class AQueue<T> extends Testable<T> {

    T[] queue = makeArray(3);
    int front = 0;
    int rear = 0;
    int size = 0;

    /**
     * Adds the given element to the end of the queue, preserving ordering. Expands the underlying array when necessary
     * Example: {1, 2}.enqueue(3) -> {1, 2, 3}
     * @param element the elemt to add
     */
    public void enqueue(T element) {

        if(size == queue.length) {  // Check whether the array is full

            // If full, create a larger array
            var temp = makeArray(size * 2);

            // Copy existing elements in FIFO order
            int firstCopy = Math.min(size, queue.length - rear);
            int secondCopy = size - firstCopy;

            System.arraycopy(queue, rear, temp, 0, firstCopy);
            System.arraycopy(queue, 0, temp, firstCopy, secondCopy);

            // Update front and rear after resizing
            rear = 0;
            front = size;
            queue = temp;

        }

        var endPtr = front;
        front = (front + 1) % queue.length; // Advance front circularly
        queue[endPtr] = element; // Insert the new element at front
        size++; // Increment size

    }

    /**
     * Removes and returns the oldest element in the queue.
     * Example {1, 2, 3}.dequeue() -> returns 1 and mutates to {2, 3}
     * @throws NoSuchElementException given an empty queue
     * @return the dequeued element
     */
    public T dequeue() {
        if(isEmpty()) throw new NoSuchElementException("Cannot dequeue from empty queue");  // Check whether queue is empty
        T result = queue[rear]; // Save the element at rear
        queue[rear] = null; // Clear the removed position
        rear = (rear + 1) % queue.length; // Advance rear circularly
        size--; // Decrement size
        return result;
    }

    /**
     * Returns the oldest element without removing it
     * Example: {1, 3}.peek() -> returns 1
     * @throws NoSuchElementException when empty
     * @return the oldest element on the stack
     */
    public T peek() {
        if(isEmpty()) throw new NoSuchElementException("Empty Queue");  // Check whether queue is empty
        return queue[rear]; // Return element at rear
    }

    /**
     * Returns the number of elements currently stored in the queue.
     * Example: {1, 2, 3}.size() -> 3
     * @return the number of elements currently stored in the queue.
     */
    public int size() {
        return size;
    }

    /**
     * Determines whether the queue is empty
     * {1, 2, 3}.isEmpty() -> false
     * @return true if the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Creates and returns a new empty queue, independent of the current queue.
     * Example: {1, 2, 3}.emptyQueue() -> AQueue<String>
     * @return a new, untouched empty queue
     */
    public static AQueue<String> emptyQueue() {
        return new AQueue<>();
    }

    /**
     * Creates a T array of a given length. Used internally
     */
    @SuppressWarnings("unchecked")
    private T[] makeArray(int length) {
        return (T[]) new Object[length];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AQueue<?> other)) return false;
        if (size != other.size) return false;

        for (int i = 0; i < size; i++) {
            Object a = queue[(rear + i) % queue.length];
            Object b = other.queue[(other.rear + i) % other.queue.length];

            if (!Objects.equals(a, b)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < size; i++) {
            result = result + Objects.hashCode(queue[(rear + i) % queue.length]);
        }
        return result;
    }
}
