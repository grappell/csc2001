import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class AQueueTests {
    @Test
    void enqueueOneElement() {
        AQueue<Integer> q = new AQueue<>();
        q.enqueue(10);

        assertEquals(1, q.size());
        assertEquals(10, q.peek());
    }

    @Test
    void enqueueBeyondInitialCapacity() {
        AQueue<Integer> q = new AQueue<>();

        for (int i = 0; i < 10; i++) {
            q.enqueue(i);
        }

        assertEquals(10, q.size());

        for (int i = 0; i < 10; i++) {
            assertEquals(i, q.dequeue());
        }
    }

    @Test
    void dequeuePreservesOrder() {
        AQueue<String> q = new AQueue<>();
        q.enqueue("A");
        q.enqueue("B");

        assertEquals("A", q.dequeue());
        assertEquals("B", q.dequeue());
        assertTrue(q.isEmpty());
    }

    @Test
    void dequeueEmptyQueue() {
        AQueue<Integer> q = new AQueue<>();
        assertThrows(NoSuchElementException.class, q::dequeue);
    }

    @Test
    void peekDoesNotRemoveElement() {
        AQueue<String> q = new AQueue<>();
        q.enqueue("A");

        assertEquals("A", q.peek());
        assertEquals("A", q.peek());
        assertEquals(1, q.size());
    }

    @Test
    void peekEmptyQueue() {
        AQueue<String> q = new AQueue<>();
        assertThrows(NoSuchElementException.class, q::peek);
    }

    @Test
    void sizeOfEmptyQueue() {
        assertEquals(0, new AQueue<Integer>().size());
    }

    @Test
    void sizeAfterOperations() {
        AQueue<Integer> q = new AQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.dequeue();

        assertEquals(1, q.size());
    }

    @Test
    void newQueueIsEmpty() {
        assertTrue(new AQueue<Integer>().isEmpty());
    }

    @Test
    void queueBecomesEmptyAfterRemoval() {
        AQueue<Integer> q = new AQueue<>();
        q.enqueue(10);
        assertFalse(q.isEmpty());

        q.dequeue();
        assertTrue(q.isEmpty());
    }

    @Test
    void emptyQueueProducesEmptyInstance() {
        AQueue<String> q = new AQueue<>();
        AQueue<String> empty = AQueue.emptyQueue();
        assertTrue(empty.isEmpty());
    }

    @Test
    void emptyQueueIsIndependent() {
        AQueue<String> original = new AQueue<>();
        original.enqueue("asdf");

        AQueue<String> empty = AQueue.emptyQueue();
        assertEquals(1, original.size());
        assertEquals(0, empty.size());
    }

    @Test
    void circularQueueWraparound() {
        AQueue<Integer> q = new AQueue<>();

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
