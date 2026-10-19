import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class AStackTests {

    @Test
    void pushMaintainsSortedOrder() {
        AStack<Integer> s = new AStack<>();
        s.push(30, Integer::compareTo);
        s.push(10, Integer::compareTo);
        s.push(20, Integer::compareTo);

        assertEquals(30, s.pop());
        assertEquals(20, s.pop());
        assertEquals(10, s.pop());
    }

    @Test
    void pushDuplicateValues() {
        AStack<Integer> s = new AStack<>();
        s.push(10, Integer::compareTo);
        s.push(10, Integer::compareTo);

        assertEquals(2, s.size());
        assertEquals(10, s.pop());
        assertEquals(10, s.pop());
    }

    @Test
    void popReturnsLargestElement() {
        AStack<Integer> s = new AStack<>();
        s.push(5, Integer::compareTo);
        s.push(15, Integer::compareTo);

        assertEquals(15, s.pop());
        assertEquals(1, s.size());
    }

    @Test
    void popEmptyStack() {
        AStack<Integer> s = new AStack<>();

        assertThrows(NoSuchElementException.class, s::pop);
    }

    @Test
    void peekReturnsLargestElement() {
        AStack<Integer> s = new AStack<>();
        s.push(5, Integer::compareTo);
        s.push(20, Integer::compareTo);

        assertEquals(20, s.peek());
        assertEquals(2, s.size());
    }

    @Test
    void peekEmptyStack() {
        AStack<Integer> s = new AStack<>();

        assertThrows(NoSuchElementException.class, s::peek);
    }

    @Test
    void newArrayStackIsEmpty() {
        AStack<Integer> s = new AStack<>();

        assertEquals(0, s.size());
        assertTrue(s.isEmpty());
    }

    @Test
    void arrayStackSizeUpdates() {
        AStack<Integer> s = new AStack<>();
        s.push(10, Integer::compareTo);
        s.push(20, Integer::compareTo);

        assertFalse(s.isEmpty());
        assertEquals(2, s.size());

        s.pop();
        assertEquals(1, s.size());
    }

    @Test
    void streamContainsAllElements() {
        AStack<Integer> s = new AStack<>();
        s.push(30, Integer::compareTo);
        s.push(10, Integer::compareTo);
        s.push(20, Integer::compareTo);

        assertEquals(List.of(10, 20, 30), s.stream().toList());
    }

    @Test
    void streamOfEmptyStack() {
        AStack<Integer> s = new AStack<>();

        assertEquals(0, s.stream().count());
    }

    @Test
    void emptyStackCreatesNewStack() {
        assertTrue(AStack.emptyStack().isEmpty());
    }

    @Test
    void emptyStackDoesNotModifyOriginal() {
        AStack<String> s = new AStack<>();
        s.push("asdf", String::compareTo);

        AStack<String> empty = AStack.emptyStack();
        assertEquals(1, s.size());
        assertEquals(0, empty.size());
    }

}
