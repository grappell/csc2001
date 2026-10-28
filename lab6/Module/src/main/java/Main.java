import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;

public class Main {
    static void main() {
        var h = new MinHeap();
        h.insert(1);
        h.insert(2);
        h.insert(3);
        h.insert(-1);
        h.insert(-2);

        var h2 = new MinHeap(new int[]{-2, 1, 2, -1, 3});

        out(h.toArray());
        out(h2.toArray());
        printTree(h.toArray());
        printTree(h2.toArray());

        out(" --- Peek --- ");
        out(h.peek());

        out(" --- Remove --- ");
        out(h.removeMin());
        printTree(h.toArray());

        out(" --- Sort --- ");
        printTree(MinHeap.heapSort(new int[]{12, 45, 8, 10, 9, 87, 14, -6, -2}));
    }

    public static void out(int[] o) {
        Arrays.stream(o).forEach(e -> System.out.print(e + " "));
        out(""); // Space after
    }

    public static <T> void out(T o) {
        System.out.println(o);
    }

    public static void printTree(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int[] temp = new int[arr.length + 1];
        System.arraycopy(arr, 0, temp, 1, arr.length);
        arr = temp;

        int n = arr.length - 1;
        int height = 32 - Integer.numberOfLeadingZeros(n);

        int valueWidth = 1;
        for (int i = 1; i <= n; i++) valueWidth = Math.max(valueWidth, String.valueOf(arr[i]).length());

        // Give each leaf enough space for its value.
        int cellWidth = valueWidth + 2;
        int width = (1 << height) * cellWidth;

        String[][] canvas = new String[2 * height - 1][width];

        for (String[] row : canvas) {
            Arrays.fill(row, " ");
        }

        // Calculate the center of a node using its heap index.
        // All nodes and connectors use this same formula.
        IntUnaryOperator getCenter = index -> {
            int level = 31 - Integer.numberOfLeadingZeros(index);
            int position = index - (1 << level);

            return (int) Math.round((position + 0.5) * width / (1 << level) - 0.5);
        };

        for (int i = 1; i <= n; i++) {

            int level = 31 - Integer.numberOfLeadingZeros(i);
            int row = 2 * level;
            int center = getCenter.applyAsInt(i);

            String value = String.valueOf(arr[i]);
            int start = center - (int) Math.ceil((value.length() - 1) / 2.0);

            // Place the value.
            for (int k = 0; k < value.length(); k++) {
                canvas[row][start + k] = String.valueOf(value.charAt(k));
            }

            // Draw left connector.
            if (2 * i <= n) {
                int childCenter = getCenter.applyAsInt(2 * i);

                canvas[row + 1][childCenter] = "┌";
                canvas[row + 1][start] = "┘";

                for (int z = childCenter + 1; z < start; z++) {
                    canvas[row + 1][z] = "─";
                }
            }

            // Draw right connector.
            if (2 * i + 1 <= n) {
                int childCenter = getCenter.applyAsInt(2 * i + 1);
                int parentEdge = start + value.length();

                canvas[row + 1][parentEdge] = "└";
                canvas[row + 1][childCenter] = "┐";

                for (int z = parentEdge + 1; z < childCenter; z++) {
                    canvas[row + 1][z] = "─";
                }
            }
        }

        for (String[] row : canvas) {
            StringBuilder line = new StringBuilder();
            for (String ch : row) line.append(ch);
            System.out.println(line.toString().stripTrailing());
        }
    }


}
