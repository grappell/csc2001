import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntArrayTests {
    @Test
    public void addToEnd() {
        var a = new IntArray(List.of(0, 1, 2, 3));
        a.addToEnd(4);
        assertEquals(a, new IntArray(List.of(0, 1, 2, 3, 4)));
    }

    @Test
    public void addToStart() {
        var a = new IntArray(List.of(0, 1, 2, 3));
        a.addToStart(4);
        assertEquals(a, new IntArray(List.of(4, 0, 1, 2, 3)));
    }

    @Test
    public void remove() {
        var a = new IntArray(List.of(0, 1, 2, 3));
        a.remove(3);
        assertEquals(a, new IntArray(List.of(0, 1, 2)));
    }

    @Test
    public void insert() {
        var a = new IntArray(List.of(0, 1, 2, 3));
        a.insert(2, 999);
        assertEquals(a, new IntArray(List.of(0, 1, 999, 2, 3)));
    }

    @Test
    public void set() {
        var a = new IntArray(List.of(0, 1, 2, 3));
        a.set(0, 1100);
        assertEquals(a, new IntArray(List.of(1100, 1, 2, 3)));
    }

    @Test
    public void get() {
        var a = new IntArray(List.of(0, 1, 2, 3));
        assertEquals(3, a.get(3));
    }

    @Test
    public void length() {
        var a = new IntArray(List.of(0, 1, 2, 3));
        assertEquals(4, a.length());
    }

    @Test
    public void empty() {
        assertTrue(new IntArray().empty());
    }

    @Test
    public void elts() {
        var a = new IntArray(List.of(5, 2, 80085, 3));
        var b = new IntArray(List.of(5, 2, 80085, 3, 4));
        assertTrue(a.equalElts(b));
    }
}
