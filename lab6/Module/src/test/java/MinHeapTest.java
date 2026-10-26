import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MinHeap.
 *
 * These starter tests (with these exact names) should all pass by the
 * time you're done. They are NOT enough on their own - add your own
 * tests (see the TODO list at the bottom and the lab handout).
 */
public class MinHeapTest {

    // ---- starter tests ----

    @Test
    void newHeapIsEmpty() {
        MinHeap h = new MinHeap();
        assertTrue(h.isEmpty());
        assertEquals(0, h.size());
        assertArrayEquals(new int[0], h.toArray());
    }

    @Test
    void peekOnEmptyHeapThrows() {
        assertThrows(NoSuchElementException.class, () -> new MinHeap().peek());
    }

    @Test
    void insertOneValue() {
        MinHeap h = new MinHeap(1);
        h.insert(42);
        assertFalse(h.isEmpty());
        assertEquals(1, h.size());
        assertEquals(42, h.peek());
        assertArrayEquals(new int[]{42}, h.toArray());
    }

    // TODO: add your own tests. At a minimum, cover:
    //   - removeMin() on an empty heap throws NoSuchElementException
    //   - new MinHeap(0) throws IllegalArgumentException
    //   - peek() does not remove the value
    //   - inserting a new minimum percolates all the way to the root
    //   - removeMin() when both children are equal swaps with the LEFT child
    //   - duplicate and negative values
    //   - inserting more values than the initial capacity (growing)
    //   - toArray() returns a copy (changing it does not change the heap)
    //   - many inserts followed by removeMin() return values in sorted order
    //   - new MinHeap(int[]): an example that comes out DIFFERENT from inserting
    //     the same values one at a time, an empty array, one value, and that the
    //     caller's array is not changed
    //   - a heap built from an array still supports insert (it must grow) and removeMin
    //   - heapSort: empty array, duplicates, negatives, input not changed
}
