import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStackTest {
    @Test
    public void testInsertIntegers() {
        var li = new ArrayStack<>();
        li.push(0);
        li.push(2);
        li.push(4);
        li.push(6);

        var listOutput = li.stream().map(Object::toString).collect(Collectors.joining(", "));
        assertEquals("6, 4, 2, 0", listOutput);
    }

    @Test
    public void testInsertLetters() {
        var li = new ArrayStack<>();
        li.push("A");
        li.push("B");
        li.push("C");
        li.push("D");

        var listOutput = li.stream().map(Object::toString).collect(Collectors.joining(", "));
        assertEquals("D, C, B, A", listOutput);
    }

    @Test
    public void testInsertDoubles() {
        var li = new ArrayStack<>();
        li.push(20.3);
        li.push(40.5);
        li.push(60.027);
        li.push(38784923.348367);

        var listOutput = li.stream().map(Object::toString).collect(Collectors.joining(", "));
        assertEquals("3.8784923348367E7, 60.027, 40.5, 20.3", listOutput);
    }

    @Test
    public void testPop() {
        var li = new ArrayStack<>();
        li.push(0);
        li.push(2);
        li.push(4);
        li.push(6);

        li.pop();

        var listOutput = li.stream().map(Object::toString).collect(Collectors.joining(", "));
        assertEquals("4, 2, 0", listOutput);
    }

    @Test
    public void testPopValue() {
        var li = new ArrayStack<>();
        li.push(42);
        assertEquals(42, li.pop());
    }

    @Test
    public void testPopError() {
        var li = new ArrayStack<>();
        assertThrows(NoSuchElementException.class, li::pop);
    }

    @Test
    public void testPeekInteger() {
        var li = new ArrayStack<>();
        li.push(10);
        assertEquals(10, li.peek());
    }

    @Test
    public void testPeekDouble() {
        var li = new ArrayStack<>();
        li.push(193.1294);
        assertEquals(193.1294, li.peek());
    }

    @Test
    public void testPeekError() {
        assertThrows(NoSuchElementException.class, () -> new ArrayStack<>().peek());
    }

    @Test
    public void testIsEmptyTrue() {
        assertTrue(new ArrayStack<>().isEmpty());
    }

    @Test
    public void testIsEmptyBoolean() {
        var li = new ArrayStack<>();
        li.push(false);
        assertFalse(li.isEmpty());
    }

    @Test
    public void testIsEmptyObject() {
        var li = new ArrayStack<>();
        li.push(new Object());
        assertFalse(li.isEmpty());
    }

    @Test
    public void testSizeEmpty() {
        assertEquals(0, new ArrayStack<>().size());
    }

    @Test
    public void testSizeBooleans() {
        var li = new ArrayStack<>();
        li.push(false);
        li.push(true);
        li.push(false);
        li.push(true);
        li.push(true);

        assertEquals(5, li.size());
    }

    @Test
    public void testSizeObjects() {
        var li = new ArrayStack<>();
        li.push(new Object());
        li.push(new Object());
        li.push(new Object());
        li.push(new Object());
        li.push(new Object());
        li.push(new Object());
        li.push(new Object());

        assertEquals(7, li.size());
    }

}

