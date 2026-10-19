import java.util.*;

public class LLQueue<T> extends Testable<T> {


    private static class Node<T> {
        T value;
        Node<T> next;

        /**
         * Constructs a linked-list node containing the provided value and a reference to its successor.
         * @param value a value to store
         * @param next reference to successor
         */
        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> front, rear;
    private int size = 0;

    /**
     * Adds an element to the end of the queue
     * Example {1, 2, 3}.enqueue(4) -> {1, 2, 3, 4}
     * @param element the element to add
     */
    public void enqueue(T element) {
        rear = new Node<>(element, rear);
        size++;
    }

    /**
     * Removes and returns the oldest element in the queue.
     * Example {1, 2, 3}.dequeue() -> returns 1 and mutates to {2, 3}
     * @throws NoSuchElementException given an empty queue
     * @return the dequeued element
     */
    public T dequeue() {
        if (size == 0) throw new NoSuchElementException("Cannot dequeue from empty queue");

        transferRearToFront();
        T result = front.value;
        front = front.next;
        size--;

        return result;
    }


    /**
     * Returns the oldest element without removing it
     * Example: {1, 3}.peek() -> returns 1
     * @throws NoSuchElementException when empty
     * @return the oldest element on the stack
     */
    public T peek() {
        if (size == 0) throw new NoSuchElementException("Empty Queue");

        transferRearToFront();
        return front.value;
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
     * Reverses the rear list iteratively and makes it the new front list, preserving ordering.
     */
    private void transferRearToFront() {
        if (front != null) return;

        Node<T> previous = null;
        Node<T> current = rear;

        while (current != null) {
            Node<T> next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        front = previous;
        rear = null;
    }

    /**
     * Creates and returns a new empty queue, independent of the current queue.
     * Example: {1, 2, 3}.emptyQueue() -> AQueue<String>
     * @return a new, untouched empty queue
     */
    public static LLQueue<String> emptyQueue() {
        return new LLQueue<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LLQueue<?> other)) return false;
        if (size != other.size) return false;
        return toList().equals(other.toList());
    }

    private List<T> toList() {

        List<T> result = new ArrayList<>(size);
        Node<T> current = front;

        while (current != null) {
            result.add(current.value);
            current = current.next;
        }

        Deque<T> reversed = new LinkedList<>();
        current = rear;

        while (current != null) {
            reversed.push(current.value);
            current = current.next;
        }

        while (!reversed.isEmpty()) {
            result.add(reversed.pop());
        }

        return result;
    }
}
