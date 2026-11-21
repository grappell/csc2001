import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**********************
 Name(s): Grayson Appell
 Responsibilities Descriptions: I did it all
 Sources you used: https://www.baeldung.com/java-insertion-sort
 ***********************/

public class ComparisonSort {

    public static void main(String[] args) throws IOException  {

        var in = -1;

        if(args.length == 0) {

            System.out.println("Input capacity");

            Scanner scanner = new Scanner(System.in);
            in = scanner.nextInt();

        } else {
            in = Integer.parseInt(args[0]);
            System.out.println("Capacity: " + args[0]);
        }

        if(in == -1) {
            var lengths = List.of(1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 100_000, 500_000);
            lengths.forEach(l -> {

                System.out.println("\n ----------- For " + l + " elements ----------- ");

                var array = createRandom(l);
                var arraySorted = Arrays.stream(array).sorted().toArray();

                time("Insertion Sort (Unsorted)", ComparisonSort::insertionSort, Arrays.copyOf(array, array.length));
                time("Insertion Sort (Sorted)", ComparisonSort::insertionSort, Arrays.copyOf(arraySorted, arraySorted.length));

                time("Merge Sort (Unsorted)", ComparisonSort::mergeSort, Arrays.copyOf(array, array.length));
                time("Merge Sort (Sorted)", ComparisonSort::mergeSort, Arrays.copyOf(arraySorted, arraySorted.length));

                time("Quick Sort (Unsorted)", a -> { Arrays.sort(a); return -1L; }, Arrays.copyOf(array, array.length));
                time("Quick Sort (Sorted)", a -> { Arrays.sort(a); return -1L; }, Arrays.copyOf(arraySorted, arraySorted.length));

            });
        } else {

            var array = createRandom(in);
            var arraySorted = Arrays.stream(array).sorted().toArray();

            time("Insertion Sort (Unsorted)", ComparisonSort::insertionSort, Arrays.copyOf(array, array.length));
            time("Insertion Sort (Sorted)", ComparisonSort::insertionSort, Arrays.copyOf(arraySorted, arraySorted.length));

            time("Merge Sort (Unsorted)", ComparisonSort::mergeSort, Arrays.copyOf(array, array.length));
            time("Merge Sort (Sorted)", ComparisonSort::mergeSort, Arrays.copyOf(arraySorted, arraySorted.length));

            time("Quick Sort (Unsorted)", a -> { Arrays.sort(a); return -1L; }, Arrays.copyOf(array, array.length));
            time("Quick Sort (Sorted)", a -> { Arrays.sort(a); return -1L; }, Arrays.copyOf(arraySorted, arraySorted.length));
        }


    }

    /**
     * create an array of size capacity with random integer values.
     * @ param: int capacity
     * @ return: int[capacity]
     */
    public static int[] createRandom (int capacity) {
        return new Random().ints(capacity).toArray();
    }

    /**
     * create insertion sort using the given integer array
     * @ param: int[]
     * @ return: int number of comparison
     */
    public static long insertionSort (int[] list) {
        long count = 0;
        for (int i = 1; i < list.length; i++) {
            int key = list[i];
            int j = i - 1;
            while (j >= 0 && list[j] > key) {
                count++;
                list[j + 1] = list[j];
                j--;
            }
            list[j + 1] = key;
        }
        return count;
    }

    /**
     * create merge sort using the given integer array
     * @ param: int[]
     * @ return: int number of comparison
     */
    public static long mergeSort (int[] list) {
        AtomicInteger counter = new AtomicInteger(0);
        sort(list, counter);
        return counter.get();
    }

    public static int[] sort(int[] li, AtomicInteger counter) {
        int len = li.length;
        if(len <= 1) return li;
        int mid = Math.floorDiv(len, 2);
        int[] left = Arrays.copyOfRange(li, 0, mid);
        int[] right = Arrays.copyOfRange(li, mid, len);
        return merge(sort(left, counter), sort(right, counter), counter);
    }

    public static int[] merge(int[] a, int[] b, AtomicInteger counter) {

        ArrayList<Integer> lia = Arrays.stream(a).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> lib = Arrays.stream(b).boxed().collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Integer> lif = new ArrayList<>();

        while(!lia.isEmpty() && !lib.isEmpty()) {
            counter.incrementAndGet();
            lif.add(lia.getFirst() <= lib.getFirst() ? lia.removeFirst() : lib.removeFirst());
        }

        lif.addAll(lia);
        lif.addAll(lib);

        return lif.stream().mapToInt(e -> e).toArray();

    }

    private static void time(String name, Function<int[], Long> r, int[] arr) {
        long start = System.nanoTime();
        long comparisons = r.apply(arr);
        System.out.println(name + ": " + (comparisons == -1 ? "N/A" : comparisons) + " comparisons in " + ((System.nanoTime() - start) / 1e+9) + " seconds");
    }
}
