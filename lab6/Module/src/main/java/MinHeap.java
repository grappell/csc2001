import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * <p>
 * A min-heap of ints stored in an array, as presented in lecture.
 * </p><p>
 * The heap is a complete binary tree kept in heap[1] ... heap[size];
 * heap[0] is never used. For the node at index i:
 *     left child  = 2 * i
 *     right child = 2 * i + 1
 *     parent      = i / 2 
 * </p><p>
 * Heap-order property: every parent is <= its children, so the
 * minimum is always at heap[1]. Duplicate values are allowed.
 * </p><p>
 * Do not rename the class, the public methods, or change their
 * parameters/return types. You may add private helper methods.
 * </p>
 */
public class MinHeap {

    /** Number of values a new heap can hold before it has to grow. */
    public static final int DEFAULT_CAPACITY = 10;

    private int[] heap;   // heap[0] is unused; the root is heap[1]
    private int size;     // values live in heap[1] ... heap[size]

    /** Creates an empty heap with room for DEFAULT_CAPACITY values. */
    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty heap with room for capacity values before it grows.
     * (The array needs capacity + 1 slots, since index 0 is unused.)
     * throws IllegalArgumentException if capacity is less than 1
     */
    public MinHeap(int capacity) {
        if(capacity < 1) throw new IllegalArgumentException("Invalid Capacity");
        heap = new int[capacity];
    }

    /**
     * Builds a heap from values bottom-up in O(n): copies them into
     * heap[1] ... heap[n] in the given order, then calls buildHeap().
     * values itself must not be changed. (Make the array exactly big
     * enough: n + 1 slots, but at least 2.)
     * throws IllegalArgumentException if values is null
     */
    public MinHeap(int[] values) {
        if(values == null) throw new IllegalArgumentException("No values");
        size = values.length;
        heap = new int[Math.max(2, size + 1)];
        System.arraycopy(values, 0, heap, 1, size);
        buildHeap();
    }

    /**
     * Adds value to the heap: puts it in the next open spot (index size + 1),
     * then percolates it up until its parent is no larger.
     * Grows the array first if it is full.
     */
    public void insert(int value) {
        if((size + 1) == heap.length) grow();
        var insert = getNextOpenSlot();
        heap[insert] = value;
        percolateUp(insert);
        size++;
    }

    private int getNextOpenSlot() {
        return size + 1;
    }

    /**
     * Removes and returns the smallest value: the last leaf moves to the
     * root, then percolates down until it is no larger than its children.
     * throws NoSuchElementException if the heap is empty
     */
    public int removeMin() {
        if(size == 0) throw new NoSuchElementException("Empty");
        var min = heap[1];
        swap(1, size--);
        percolateDown(1);
        return min;
    }

    /**
     * Returns the smallest value without removing it.
     * throws NoSuchElementException if the heap is empty
     */
    public int peek() {
        if(size == 0) throw new NoSuchElementException("Empty");
        return heap[1];
    }

    /**
     * Returns the number of values in the heap.
     */
    public int size() {
        return size;
    }

    /**
     * Reports whether the heap has no values.
     * return true if the heap is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the heap's values in array order (heap[1] ... heap[size]) as a
     * new array of length size(). Changing the returned array must not
     * change the heap.
     * Example: after inserting 3, 5, 8, 10, 7, 12, 9 this returns
     * {3, 5, 8, 10, 7, 12, 9}.
     * (The insert order matches the heap array order in this example)
     */
    public int[] toArray() {
        return Arrays.copyOfRange(heap,1, size + 1);
    }

    /**
     * Moves the value at index i up while it is smaller than its parent.
     * (Stop when it reaches the root or its parent is <= it.)
     */
    private void percolateUp(int i) {
        for(int active = i; active != 1 && heap[getParent(active)] > heap[active]; active = getParent(active)) {
            swap(active, getParent(active));
        }
    }

    private int getParent(int i) {
//        if(i == 1) return -1;
        if(i % 2 == 0) return i / 2;
        return (i - 1) / 2;
    }

    /**
     * Moves the value at index i down, always swapping with the smaller
     * child (the LEFT child if both children are equal), until it is no
     * larger than its children or reaches a leaf.
     */
    private void percolateDown(int i) {
        var active = i;
        while(true) {
            var left = 2 * active;
            var right = 2 * active + 1;
            var smallest = active;

            if(left < size && heap[left] < heap[smallest]) smallest = left;
            if(right < size && heap[right] < heap[smallest]) smallest = right;
            if(smallest == active) break;

            swap(active, smallest);
            active = smallest;
        }
    }

    /**
     * Exchanges heap[a] and heap[b].
     * a and b are the indices of the values to swap
     */
    private void swap(int a, int b) {
        var temp = heap[b];
        heap[b] = heap[a];
        heap[a] = temp;
    }

    /** Doubles the array's capacity, keeping every value at the same index. */
    private void grow() {
        var temp = new int[heap.length * 2];
        System.arraycopy(heap, 0, temp, 0, heap.length);
        heap = temp;
    }

    /**
     * Bottom up heap construction.
     * Restores heap order over heap[1] ... heap[size] by percolating down
     * each non-leaf node, starting at the last one (index size / 2) and
     * working back to the root (index 1).
     */
    private void buildHeap() {
        for (int i = size / 2; i >= 1; i--) percolateDown(i);
    }

    /**
     * Returns a new array holding values in ascending order: builds a
     * MinHeap from values with the MinHeap(int[]) constructor, then calls
     * removeMin() repeatedly. values itself must not be changed.
     * throws IllegalArgumentException if values is null
     */
    public static int[] heapSort(int[] values) {
        if(values == null) throw new IllegalArgumentException("Cannot process null input");
        var sub = new MinHeap(values);
        for(int i = 0; i < values.length; i++) {
            values[i] = sub.removeMin();
        }
        return values;
    }
}
