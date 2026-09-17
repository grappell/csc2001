import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.*;

/**
 * The type Linked list.
 *
 * @param <T> the type parameter
 */
public class LinkedList<T> {

    /**
     * A node in the LinkedList
     *
     * @param <N> the of data within
     */
    public class Node<N> {

        private N data;
        private Node<N> next;

        /**
         * Instantiates a new Node.
         *
         * @param data the data
         * @param next the next
         */
        public Node(N data, Node<N> next) {
            this.data = data;
            this.next = next;
        }

        /**
         * Instantiates a new Node with the next node being null
         *
         * @param data the data
         */
        public Node(N data) {
            this(data, null);
        }

        /**
         * Gets data.
         *
         * @return the data
         */
        public N getData() {
            return data;
        }

        /**
         * Gets next.
         *
         * @return the next
         */
        public Node<N> getNext() {
            return next;
        }

        /**
         * Sets data.
         *
         * @param data the data
         */
        public void setData(N data) {
            this.data = data;
        }

        /**
         * Sets next.
         *
         * @param next the next
         */
        public void setNext(Node<N> next) {
            this.next = next;
        }

        /**
         * Check if this node has a next link
         *
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
     *
     * @param item the item
     */
    public void addLast(T item) {
        Node<T> node = new Node<>(item);
        length++;
        switch (head) {
            case null -> head = node;
            default -> getLast().setNext(node);
        }
    }

    /**
     * Add to the start of the list
     *
     * @param item the item to add
     */
    public void addFirst(T item) {
        Node<T> node = new Node<>(item);
        length++;
        if (head != null) node.setNext(head);
        head = node;
    }


    /**
     * Insert after a specific element
     *
     * @param item  the item to insert
     * @apiNote will fail and return false if the length of the list is 1 or the after data doesn't exist
     */
    public void insert(T item, Comparator<? super T> comparator) {

        Node<T> insert = new Node<>(item);

        if (head == null || comparator.compare(item, head.getData()) <= 0) {
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
     * Remove an element from the linked list
     *
     * @param item the item to remove
     * @return the removed item if present, or null if absent
     */
    public T remove(T item) {

        if(!head.hasNext()) {
            var temp = head;
            head = null;
            length--;
            return temp.getData();
        }

        var previous = head;
        var current = head.getNext();

        while(current != null) {
            if(current.getData().equals(item)) {
                previous.setNext(current.getNext());
                length--;
                return current.getData();
            }
            previous = current;
            current = current.getNext();
        }

        return null;
    }

    /**
     * Pop off the head of the list
     *
     * @return the element removed
     */
    public T pop() {
        var popped = head;
        head = head.getNext();
        return popped.getData();
    }

    /**
     * Gets last node of the list
     *
     * @return the last node
     */
    public Node<T> getLast() {
        var current = head;
        while(current.hasNext()) {
            current = current.getNext();
        }
        return current;
    }

    /**
     * Gets first node of the list
     *
     * @return the first node
     */
    public Node<T> getFirst() {
        return head;
    }

    /**
     * Gets index of an item in the list
     *
     * @param item the item to find
     * @return the index of that item
     */
    public int getIndex(T item) {
        var current = head;
        var index = 0;
        while (current != null) {
            if(current.getData().equals(item)) return index;
            index++;
            current = current.getNext();
        }
        return -1;
    }

    public Optional<Node<T>> getFirst(Function<T, Boolean> specific) {
        var current = head;
        while (current != null) {
            if(specific.apply(current.getData())) return Optional.of(current);
            current = current.getNext();
        }
        return Optional.empty();
    }

    public Node<T> at(int index) {
        var curr = head;
        while(curr != null) {
            if(index == 0) return curr;
            curr = curr.next;
            index--;
        }
        return null;
    }

    /**
     * Get the length of the list
     *
     * @return the length of the list
     */
    public int getLength() {
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
    public LinkedList<T> copyList() {
        LinkedList<T> ll = new LinkedList<>();
        stream().forEach(ll::addLast);
        return ll;
    }

    /**
     * Append linked lists.
     *
     * @implNote make a copy of both lists, then set the first list's tail's next to the head of the second list
     *
     * @param <T> the type parameter
     * @param a   the first list
     * @param b   the second list
     * @return the combined linked lists
     */
    public static <T> LinkedList<T> append(LinkedList<T> a, LinkedList<T> b) {
        LinkedList<T> ll = a.copyList();
        ll.getLast().setNext(b.getFirst());
        ll.recomputeLength();
        return  ll;
    }

    /**
     * Append linked lists.
     *
     * @implNote make a copy of both lists, then set the first list's tail's next to the head of the second list
     *
     * @param list the list to append to the end of this linked list
     * @return the combined linked lists
     */
    public LinkedList<T> append(LinkedList<T> list) {
        getLast().setNext(list.getFirst());
        recomputeLength();
        return this;
    }

}