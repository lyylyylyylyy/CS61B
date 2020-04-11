
package synthesizer;
import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {

        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;

        rb = (T[]) new Object[capacity];
    }

    private int addOne(int index) {
        if (index == capacity - 1) {
            return 0;
        } else {
            index = index + 1;
            return index;
        }
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {

        if (!isFull()) {
            rb[last] = x;
            fillCount = fillCount + 1;
            last = addOne(last);
        } else {
            throw new RuntimeException("Ring buffer overflow");
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {

        if (!isEmpty()) {
            T remove = rb[first];
            fillCount = fillCount - 1;
            first = addOne(first);
            return remove;
        } else {
            throw new RuntimeException("Ring buffer overflow");
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {

        return rb[first];
    }


    private class QueueIterator implements Iterator<T> {

        int numRemain;
        int current;

        QueueIterator() {
            numRemain = fillCount();
            current = first;
        }

        @Override
        public boolean hasNext() {
            return numRemain > 0;
        }

        @Override
        public T next() {
            T item = rb[current];
            numRemain -= 1;
            current = addOne(current);
            return item;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }

}
