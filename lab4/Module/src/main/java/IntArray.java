import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class IntArray {

    private int[] arr = new int[3];
    private int activeElementEnd = 0;

    public IntArray() {}

    public IntArray(List<Integer> from) {
        from.forEach(this::addToEnd);
    }

    public void addToStart(int e) {
        var temp = getResizeAwareTemp();
        System.arraycopy(arr, 0, temp, 1, arr.length - 1);
        temp[0] = e;
        arr = temp;
        activeElementEnd++;
    }

    public void addToEnd(int e) {
        if(activeElementEnd + 1 == arr.length) {
            var temp = getResizeAwareTemp();
            System.arraycopy(arr, 0, temp, 0, arr.length - 1);
            arr = temp;
        }
        arr[activeElementEnd++] = e;
    }

    public void insert(int index, int e) {
        var temp = getResizeAwareTemp();
        System.arraycopy(arr, 0, temp, 0, index);
        System.arraycopy(arr, index, temp, index + 1, activeElementEnd - index);
        activeElementEnd++;
        temp[index] = e;
        arr = temp;
    }

    public int remove(int index) {

        if (index < 0 || index >= activeElementEnd) {
            throw new IndexOutOfBoundsException();
        }

        var res = arr[index];
        System.arraycopy(arr, index + 1, arr, index, arr.length - index - 1);

        activeElementEnd--;
        return res;
    }

    public IntStream stream() {
        return Arrays.stream(arr);
    }

    public int length() {
        return activeElementEnd;
    }

    private int[] getResizeAwareTemp() {
        return new int[activeElementEnd + 1 == arr.length ? arr.length * 2 : arr.length];
    }
}
