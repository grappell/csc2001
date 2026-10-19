import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LLTests {
    @Test
    void nodeStoresData() {
        LLStack<Integer> stack = new LLStack<>();
        var node = stack.new Node<>(10, null);

        assertEquals(10, node.getData());
        assertNull(node.getNext());
    }

    @Test
    void nodeStoresNextReference() {
        LLStack<Integer> stack = new LLStack<>();
        var next = stack.new Node<>(20);
        var first = stack.new Node<>(10, next);

        assertSame(next, first.getNext());
    }

    @Test
    void singleArgumentConstructorStoresValue() {
        LLStack<Integer> stack = new LLStack<>();
        var node = stack.new Node<>(42);

        assertEquals(42, node.getData());
    }

    @Test
    void singleArgumentConstructorHasNullNext() {
        LLStack<Integer> stack = new LLStack<>();
        var node = stack.new Node<>(42);

        assertNull(node.getNext());
    }

    @Test
    void nodeDataAccessors() {
        LLStack<Integer> stack = new LLStack<>();
        var node = stack.new Node<>(10);

        assertEquals(10, node.getData());

        node.setData(20);

        assertEquals(20, node.getData());
    }

    @Test
    void nodeLinkAccessors() {
        LLStack<Integer> stack = new LLStack<>();
        var first = stack.new Node<>(10);
        var second = stack.new Node<>(20);

        assertFalse(first.hasNext());

        first.setNext(second);

        assertTrue(first.hasNext());
        assertSame(second, first.getNext());
    }

    @Test
    void equalNodes() {
        LLStack<Integer> stack = new LLStack<>();

        var a = stack.new Node<>(10);
        var b = stack.new Node<>(10);

        assertEquals(a, b);
    }

    @Test
    void unequalNodes() {
        LLStack<Integer> stack = new LLStack<>();

        var a = stack.new Node<>(10);
        var b = stack.new Node<>(20);

        assertNotEquals(a, b);
    }

    @Test
    void equalNodesHaveEqualHashes() {
        LLStack<Integer> stack = new LLStack<>();

        var a = stack.new Node<>(10);
        var b = stack.new Node<>(10);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void hashCodeRemainsConsistent() {
        LLStack<Integer> stack = new LLStack<>();
        var node = stack.new Node<>(10);

        assertEquals(node.hashCode(), node.hashCode());
    }

    @Test
    void copyPreservesAllElements() {
        LLStack<Integer> s = new LLStack<>();
        s.push(10, Integer::compareTo);
        s.push(20, Integer::compareTo);

        LLStack<Integer> copy = s.copyList();

        assertEquals(List.of(10, 20), copy.stream().toList());
    }

    @Test
    void copyPreservesSingleElement() {
        LLStack<Integer> s = new LLStack<>();
        s.push(10, Integer::compareTo);

        assertEquals(1, s.copyList().size());
    }

    @Test
    void appendToOneElementList() {
        LLStack<Integer> a = new LLStack<>();
        LLStack<Integer> b = new LLStack<>();

        a.push(10, Integer::compareTo);
        b.push(20, Integer::compareTo);

        a.append(b);

        assertEquals(List.of(10, 20), a.stream().toList());
    }

    @Test
    void appendMultipleNodes() {
        LLStack<Integer> a = new LLStack<>();
        LLStack<Integer> b = new LLStack<>();

        a.push(10, Integer::compareTo);
        a.push(20, Integer::compareTo);
        b.push(30, Integer::compareTo);

        a.append(b);

        assertEquals(3, a.size());
    }

    @Test
    void pushSortedElements() {
        LLStack<Integer> s = new LLStack<>();
        s.push(30, Integer::compareTo);
        s.push(10, Integer::compareTo);
        s.push(20, Integer::compareTo);

        assertEquals(List.of(10, 20, 30), s.stream().toList());
    }

    @Test
    void pushAtBeginning() {
        LLStack<Integer> s = new LLStack<>();
        s.push(20, Integer::compareTo);
        s.push(10, Integer::compareTo);

        assertEquals(10, s.peek());
    }

    @Test
    void popRemovesHead() {
        LLStack<Integer> s = new LLStack<>();
        s.push(10, Integer::compareTo);
        s.push(20, Integer::compareTo);

        assertEquals(10, s.pop());
        assertEquals(1, s.size());
    }

    @Test
    void popEmptyStack() {
        LLStack<Integer> s = new LLStack<>();
        assertThrows(NoSuchElementException.class, s::pop);
    }

    @Test
    void peekDoesNotRemove() {
        LLStack<Integer> s = new LLStack<>();
        s.push(10, Integer::compareTo);

        assertEquals(10, s.peek());
        assertEquals(1, s.size());
    }

    @Test
    void peekEmptyStack() {
        LLStack<Integer> s = new LLStack<>();

        assertThrows(NoSuchElementException.class, s::peek);
    }

    @Test
    void emptyLinkedStack() {
        LLStack<Integer> s = new LLStack<>();

        assertEquals(0, s.size());
        assertTrue(s.isEmpty());
    }

    @Test
    void linkedStackSizeAfterPop() {
        LLStack<Integer> s = new LLStack<>();
        s.push(10, Integer::compareTo);
        s.push(20, Integer::compareTo);

        s.pop();

        assertEquals(1, s.size());
        assertFalse(s.isEmpty());
    }

    @Test
    void appendedListHasCorrectLength() {
        LLStack<Integer> a = new LLStack<>();
        LLStack<Integer> b = new LLStack<>();

        a.push(10, Integer::compareTo);
        b.push(20, Integer::compareTo);
        b.push(30, Integer::compareTo);

        a.append(b);

        assertEquals(3, a.size());
    }

    @Test
    void appendSingleNodeUpdatesLength() {
        LLStack<Integer> a = new LLStack<>();
        LLStack<Integer> b = new LLStack<>();

        a.push(10, Integer::compareTo);
        b.push(20, Integer::compareTo);

        a.append(b);

        assertEquals(2, a.size());
    }

    @Test
    void streamIncludesAllElements() {
        LLStack<Integer> s = new LLStack<>();

        s.push(30, Integer::compareTo);
        s.push(10, Integer::compareTo);
        s.push(20, Integer::compareTo);

        assertEquals(List.of(10, 20, 30), s.stream().toList());
    }

    @Test
    void copyPreservesOrder() {
        LLStack<Integer> s = new LLStack<>();
        s.push(10, Integer::compareTo);
        s.push(20, Integer::compareTo);

        LLStack<Integer> copy = s.copyList();

        assertEquals(s.stream().toList(), copy.stream().toList());
    }

    @Test
    void appendTwoLists() {
        LLStack<Integer> a = new LLStack<>();
        LLStack<Integer> b = new LLStack<>();

        a.push(10, Integer::compareTo);
        b.push(20, Integer::compareTo);

        a.append(b);

        assertEquals(List.of(10, 20), a.stream().toList());
    }

    @Test
    void appendPreservesOriginalPrefix() {
        LLStack<Integer> a = new LLStack<>();
        LLStack<Integer> b = new LLStack<>();

        a.push(1, Integer::compareTo);
        a.push(2, Integer::compareTo);
        b.push(3, Integer::compareTo);

        a.append(b);

        assertEquals(1, a.peek());
        assertEquals(3, a.size());
    }

    @Test
    void newEmptyStack() {
        assertTrue(LLStack.emptyStack().isEmpty());
    }

    @Test
    void originalStackRemainsUnchanged() {
        LLStack<Integer> s = new LLStack<>();
        s.push(10, Integer::compareTo);

        LLStack<String> empty = LLStack.emptyStack();

        assertEquals(1, s.size());
        assertEquals(0, empty.size());
    }
}
