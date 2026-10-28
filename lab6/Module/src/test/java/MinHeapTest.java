import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    void testRemoveFail() {
        assertThrows(NoSuchElementException.class, () -> new MinHeap().removeMin());
    }

    @Test
    void testEmptyConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new MinHeap(0));
    }

    @Test
    void testPeek() {
        var h = new MinHeap(new int[]{1, 2, 3, 4});
        assertEquals(1, h.peek());
        assertArrayEquals(new int[]{1, 2, 3, 4}, h.toArray());
    }

    @Test
    void testInsertMinAndPercolate() {
        var h = new MinHeap(new int[]{1, 2, 3, 4});
        h.insert(-1);
        assertEquals(-1, h.peek());
    }

    @Test
    void removeMinSwapsLeft() {
        var h = new MinHeap(new int[]{1, 2, 2});
        h.removeMin();
        assertArrayEquals(new int[]{2, 2}, h.toArray()); // would be {2, 0, 2} for right
    }

    @Test
    void testDuplicatesAndNegativesAndGrowth() {
        var h = new MinHeap(2);
        var li = new int[]{-10, -20, -190380283};
        for(int i = 0; i < 10; i++) {
            h.insert(li[i % li.length]);
        }
        assertEquals(10, h.size());
    }

    @Test
    void testToArrayNoMutate() {
        var h = new MinHeap(new int[]{1, 2, 3, 4});
        h.toArray()[1] = 1000; // attempt to mutate
        assertArrayEquals(new int[]{1, 2, 3, 4},  h.toArray());
    }

    @Test
    void testInsertBasedSorting() {
        var h = new MinHeap();
        var truth = new ArrayList<Integer>();

        for(int i = 0; i < 10; i++) {
            var rand = (int) (Math.random() * Integer.MAX_VALUE - (Integer.MIN_VALUE / 2.0));
            h.insert(rand);
            truth.add(rand);
        }

        var experiment = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++) {
            experiment.add(h.removeMin());
        }

        assertArrayEquals(truth.stream().sorted().toArray(), experiment.toArray());
    }

    @Test
    void testArguedConstructor() {
        assertThrows(IllegalArgumentException.class, () ->  new MinHeap(null));
        var empty = new MinHeap(new int[0]);
        var oneVal = new MinHeap(new int[]{1});
        assertNotEquals(empty.toArray(), oneVal.toArray());
    }

    @Test
    void testMutateArguedConstructor() {
        var input = new int[]{10, 1, 3812, 23};
        new MinHeap(input);
        assertArrayEquals(new int[]{10, 1, 3812, 23}, input);
    }

    @Test
    void testArguedConstructorSupportsGrowth() {
        var h = new MinHeap(new int[]{1, 2, 3, 4});
        for(int i = 0; i < 50; i++) {
            var rand = (int) (Math.random() * Integer.MAX_VALUE - (Integer.MIN_VALUE / 2.0));
            h.insert(rand);
        }
    }

    @Test
    void testHeapSortEmpty() {
        assertThrows(IllegalArgumentException.class, () -> MinHeap.heapSort(null));
    }

    @Test
    void testHeapSortDuplicatesAndNegatives() {
        var h = MinHeap.heapSort(new int[]{-1, -1, -1, -1, -1, -1, -1, -1});
        assertEquals(8, h.length);
    }

    @Test
    void testNoInputMutate() {
        var input = new int[]{2, 5, 1, 4, -10};
        var sorted = MinHeap.heapSort(input); // if broken, would mutate the values within
        assertEquals(Arrays.stream(input).sum(), Arrays.stream(sorted).sum());
    }
}
