import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LLQueueTests {

    @Test
    void enqueueIntoEmptyQueue() {
        LLQueue<String> q = new LLQueue<>();
        q.enqueue("A");

        assertEquals("A", q.peek());
    }

    @Test
    void enqueueMultipleElements() {
        LLQueue<String> q = new LLQueue<>();
        q.enqueue("A");
        q.enqueue("B");
        q.enqueue("C");

        assertEquals(3, q.size());
        assertEquals("A", q.dequeue());
    }

    @Test
    void dequeueAcrossListTransfer() {
        LLQueue<Integer> q = new LLQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        assertEquals(1, q.dequeue());

        q.enqueue(3);
        assertEquals(2, q.dequeue());
        assertEquals(3, q.dequeue());
    }

    @Test
    void dequeueEmptyQueue() {
        LLQueue<Integer> q = new LLQueue<>();
        assertThrows(NoSuchElementException.class, q::dequeue);
    }

    @Test
    void peekAfterEnqueue() {
        LLQueue<String> q = new LLQueue<>();
        q.enqueue("A");
        q.enqueue("B");

        assertEquals("A", q.peek());
        assertEquals(2, q.size());
    }

    @Test
    void peekEmptyQueue() {
        LLQueue<String> q = new LLQueue<>();
        assertThrows(NoSuchElementException.class, q::peek);
    }

    @Test
    void emptyLinkedQueueHasZeroSize() {
        LLQueue<Integer> q = new LLQueue<>();
        assertEquals(0, q.size());
        assertTrue(q.isEmpty());
    }

    @Test
    void linkedQueueSizeUpdates() {
        LLQueue<Integer> q = new LLQueue<>();
        q.enqueue(10);
        q.enqueue(20);

        assertEquals(2, q.size());
        assertFalse(q.isEmpty());

        q.dequeue();
        assertEquals(1, q.size());
    }

    @Test
    void createsEmptyLinkedQueue() {
        assertTrue(LLQueue.emptyQueue().isEmpty());
    }

    @Test
    void newLinkedQueueIsIndependent() {
        LLQueue<String> q = new LLQueue<>();
        q.enqueue("asdf");

        LLQueue<String> another = LLQueue.emptyQueue();
        assertEquals(0, another.size());
        assertEquals(1, q.size());
    }

    @Test
    void circularQueueWraparound() {
        LLQueue<Integer> q = new LLQueue<>();

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        assertEquals(1, q.dequeue());
        assertEquals(2, q.dequeue());

        q.enqueue(4);
        q.enqueue(5);

        assertEquals(3, q.dequeue());
        assertEquals(4, q.dequeue());
        assertEquals(5, q.dequeue());

        assertTrue(q.isEmpty());
    }
}
