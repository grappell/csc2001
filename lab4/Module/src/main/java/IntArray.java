import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * The type Int array.
 */
public class IntArray {

    private int[] arr = new int[3];
    private int activeElementEnd = 0;

    /**
     * Instantiates a new IntArray. Canonical constructor.
     */
    public IntArray() {}

    /**
     * Instantiates a new IntArray from an existing list.
     * Example: new IntArray(List.of(1, 2, 3, 4)) --> {1, 2, 3, 4}
     * @param from list of Integers to seed the array. Insertion ordering depends on list implementation used.
     */
    public IntArray(List<Integer> from) {
        from.forEach(this::addToEnd);
    }

    /**
     * Add to start of ArrayList.
     * Example: {1, 2, 3}.addToStart(4) --> {4, 1, 2, 3}
     * @param e the element to add to the start of the ArrayList
     */
    public void addToStart(int e) {
        var temp = getResizeAwareTemp();
        System.arraycopy(arr, 0, temp, 1, activeElementEnd);
        temp[0] = e;
        arr = temp;
        activeElementEnd++;
    }

    /**
     * Add to end of ArrayList.
     * Example: {1, 2, 3}.addToEnd(4) --> {1, 2, 3, 4}
     * @param e the element to add to the end
     */
    public void addToEnd(int e) {
        if(activeElementEnd + 1 == arr.length) {
            var temp = getResizeAwareTemp();
            System.arraycopy(arr, 0, temp, 0, activeElementEnd);
            arr = temp;
        }
        arr[activeElementEnd++] = e;
    }

    /**
     * Insert an element at a specific index
     * Example: {1, 2, 3}.insert(1, 10) --> {1, 10, 2, 3}
     * @param index the index at witch to insert
     * @param e     the element to insert
     */
    public void insert(int index, int e) {

        if (index < 0 || index >= activeElementEnd) {
            throw new IndexOutOfBoundsException();
        }

        var temp = getResizeAwareTemp();
        System.arraycopy(arr, 0, temp, 0, index);
        System.arraycopy(arr, index, temp, index + 1, activeElementEnd - index);
        activeElementEnd++;
        temp[index] = e;
        arr = temp;
    }

    /**
     * Remove a specific element at an index and return the removed element
     * Example: {1, 2, 3}.remove(1) --> {1, 3} and returns 2
     * @param index the index to remove
     * @return the integer that was removed
     * @throws IndexOutOfBoundsException if provided index is < 0 or outside the array
     */
    public int remove(int index) {

        if (index < 0 || index >= activeElementEnd) {
            throw new IndexOutOfBoundsException();
        }

        var res = arr[index];
        System.arraycopy(arr, index + 1, arr, index, arr.length - index - 1);
        activeElementEnd--;
        return res;
    }

    /**
     * Set a specific element to a new value
     * Example: {0, 1, 2}.set(0, 10) -->  {10, 1, 2}
     * @param index  the index to set
     * @param newVal the new val to set said index to
     * @throws IndexOutOfBoundsException if provided index is < 0 or outside the array
     */
    public void set(int index, int newVal) {
        if (index < 0 || index >= activeElementEnd) {
            throw new IndexOutOfBoundsException();
        }
        arr[index] = newVal;
    }

    /**
     * Get a specific index
     * Example: {1, 2, 3}.get(2) --> returns 3
     * @param index the index to get
     * @return the integer at that index
     * @throws IndexOutOfBoundsException if provided index is < 0 or outside the array
     */
    public int get(int index) {
        if (index < 0 || index >= activeElementEnd) {
            throw new IndexOutOfBoundsException();
        }
        return arr[index];
    }

    /**
     * If the ArrayList is empty
     * Example: {0, 1, 2}.empty() --> returns false
     * Example: {}.empty() --> returns true
     * @return if the list is empty
     */
    public boolean empty() {
        return activeElementEnd == 0;
    }

    /**
     * Checks that the elements in a list mach up to the length of the smallest list
     * Example: {1, 2, 3}.equalElts({1, 2, 3, 4, 5}) --> returns true;
     * @param o ArrayList to compare against
     * @return true if the lists are equal
     */
    public boolean equalElts(IntArray o) {
        var min = Math.min(activeElementEnd, o.activeElementEnd);
        for(int i = 0; i < min; i++) {
            if(get(i) != o.get(i)) return false;
        }
        return true;
    }

    /**
     * Create a stream of the elements
     * Example: {1, 2, 3}.stream() -->  returns a IntStream of 1, 2, 3
     * @return the IntStream
     */
    public IntStream stream() {
        return Arrays.stream(arr);
    }

    /**
     * Get the length of the ArrayList
     * Example: {1, 2, 3}.length() --> returns 4
     * @return the length
     */
    public int length() {
        return activeElementEnd;
    }

    private int[] getResizeAwareTemp() {
        return new int[activeElementEnd + 1 == arr.length ? arr.length * 2 : arr.length];
    }

    /**
     * Check if two ArrayLists are equal
     * @param o the reference object with which to compare.
     * @return true if the two lists are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IntArray intArray = (IntArray) o;
        return activeElementEnd == intArray.activeElementEnd && Objects.deepEquals(arr, intArray.arr);
    }

    /**
     * Get a hash for this ArrayList
     * @return a hash of this ArrayList
     */
    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(arr), activeElementEnd);
    }
}
