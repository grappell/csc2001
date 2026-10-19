import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.*;

public class LLStack<T> extends Testable<T> {

    /**
     * A node in the LinkedList
     * @param <N> the of data within
     */
    public class Node<N> {

        private N data;
        private Node<N> next;

        /**
         * Instantiates a new Node.
         * @param data the data
         * @param next the next
         */
        public Node(N data, Node<N> next) {
            this.data = data;
            this.next = next;
        }

        /**
         * Instantiates a new Node with the next node being null
         * @param data the data
         */
        public Node(N data) {
            this(data, null);
        }

        /**
         * Gets data.
         * @return the data
         */
        public N getData() {
            return data;
        }

        /**
         * Gets next.
         * @return the next
         */
        public Node<N> getNext() {
            return next;
        }

        /**
         * Sets data.
         * @param data the data
         */
        public void setData(N data) {
            this.data = data;
        }

        /**
         * Sets next.
         * @param next the next
         */
        public void setNext(Node<N> next) {
            this.next = next;
        }

        /**
         * Check if this node has a next link
         * @return true if it has a next node
         */
        public boolean hasNext() {
            return next != null;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o; // Note: this is typechecked above
            return Objects.equals(data, node.data) && Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, next);
        }
    }

    private Node<T> head = null;
    private int length = 0;

    /**
     * Add to the end of the list.
     * @param item the item
     */
    private void addLast(T item) {
        Node<T> node = new Node<>(item);
        length++;
        if(head == null) head = node;
        else getLast().setNext(node);
    }

    /**
     * Gets first node of the list
     * @return the first node
     */
    private Node<T> getFirst() {
        return head;
    }

//    public void(push)

    /**
     * Insert after a specific element
     * @param item  the item to insert
     * @apiNote will fail and return false if the length of the list is 1 or the after data doesn't exist
     */
    public void push(T item, Comparator<? super T> comparator) {

        Node<T> insert = new Node<>(item);

        if (head == null || comparator.compare(item, head.getData()) <= 0) {
            insert.setNext(head);
            head = insert;
            length++;
            return;
        }

        Node<T> current = head.getNext();
        Node<T> previous = head;

        while(current != null && comparator.compare(item, current.getData()) > 0) {
            previous = current;
            current = current.getNext();
        }

        insert.setNext(current);
        previous.setNext(insert);
        length++;

    }

    /**
     * Pop off the head of the list
     * @return the element removed
     */
    public T pop() {
        if(isEmpty()) throw new NoSuchElementException("Empty Stack");
        var popped = head;
        head = head.getNext();
        length--;
        return popped.getData();
    }

    /**
     * Gets last node of the list
     * @return the last node
     */
    private Node<T> getLast() {
        var current = head;
        while(current.hasNext()) {
            current = current.getNext();
        }
        return current;
    }

    /**
     * Gets first node of the list
     * @return the first node
     */
    public T peek() {
        if(isEmpty()) throw new NoSuchElementException("Empty Stack");
        return head.getData();
    }

    /**
     * Get the length of the list
     * @return the length of the list
     */
    public int size() {
        return length;
    }

    // Recompute the length of the list when we combine the lists together
    private void recomputeLength() {
        var current = head;
        var ll = 1;
        while(current.hasNext()) {
            ll++;
            current = current.getNext();
        }
        length = ll;
    }

    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Make a stream of all the data within the linked list
     *
     * @return a stream
     */
    public Stream<T> stream() {
        Stream.Builder<T> builder = Stream.builder();
        var current = head;
        while(current.hasNext()) {
            builder.accept(current.getData());
            current = current.getNext();
        }
        return builder.add(current.getData()).build();
    }

    /**
     * Copy the entire list into a new list
     *
     * @return a new copy of the original list
     */
    public LLStack<T> copyList() {
        LLStack<T> ll = new LLStack<>();
        stream().forEach(ll::addLast);
        return ll;
    }

    /**
     * Append linked lists.
     *
     * @implNote make a copy of both lists, then set the first list's tail's next to the head of the second list
     *
     * @param list the list to append to the end of this linked list
     * @return the combined linked lists
     */
    public LLStack<T> append(LLStack<T> list) {
        getLast().setNext(list.getFirst());
        recomputeLength();
        return this;
    }

    /**
     * Creates and returns an empty LLStack
     * Example: LLStack.emptyStack() -> new LLStack{};
     * @return a new, untouched LLStack
     */
    public static LLStack<String> emptyStack() {
        return new LLStack<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LLStack<?> other)) return false;
        if (length != other.length) return false;

        Node<?> current = head;
        Node<Object> otherCurrent = (Node<Object>) other.head;

        while (current != null && otherCurrent != null) {
            if (!Objects.equals(current.getData(), otherCurrent.getData())) {
                return false;
            }
            current = current.getNext();
            otherCurrent = otherCurrent.getNext();
        }

        return current == null && otherCurrent == null;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Node<?> current = head;

        while (current != null) {
            result += Objects.hashCode(current.data);
            current = current.next;
        }

        return result;
    }

}
