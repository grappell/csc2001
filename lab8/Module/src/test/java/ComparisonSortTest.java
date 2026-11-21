import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.processor.core.CompositeProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonSortTest {

    // ---- starter tests ----

    @Test
    void testMainAndTime() throws IOException {
        ComparisonSort.main(new String[]{"1000"});
        ComparisonSort.main(new String[]{"-1"});
    }
    
    @Test
    void testMergeSort() {
        var a = new Random().ints(10).toArray();
        var sorted = Arrays.stream(a).sorted().toArray();
        assertArrayEquals(sorted, ComparisonSort.sort(a, new AtomicInteger()));
    }

    @Test
    void testInsertionSort() {
        assertNotEquals(0, ComparisonSort.insertionSort(new Random().ints(10).toArray()));
    }

    @Test
    void testMerge() {
        var a = new Random().ints(10).sorted().toArray();
        var b = new Random().ints(10).sorted().toArray();
        var merged = ComparisonSort.merge(a, b, new AtomicInteger());
        ArrayList<Integer> correct = new ArrayList<>();
        Arrays.stream(a).forEach(correct::add);
        Arrays.stream(b).forEach(correct::add);
        correct.sort(Integer::compareTo);
        assertArrayEquals(correct.stream().mapToInt(e -> e).toArray(), merged);
    }

    @Test
    void testRandom() {
        assertEquals(10, ComparisonSort.createRandom(10).length);
    }



}
