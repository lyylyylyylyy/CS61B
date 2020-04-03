import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayDequeTest{

    @Test
    public void testAdd() {
        ArrayDeque<Integer> temp = new ArrayDeque<>();
        temp.addLast(1);
        temp.addLast(2);
        temp.addLast(3);
        temp.addLast(4);
        temp.addLast(5);
        temp.addLast(6);
        temp.addLast(7);
        temp.addLast(8);

        int j = temp.get(0);
        int a = temp.get(1);
        int b = temp.get(2);
        int c = temp.get(3);
        int d = temp.get(4);
        int e = temp.get(5);
        int f = temp.get(6);
        int g = temp.get(7);

        assertEquals(2,a);
        assertEquals(3,b);
        assertEquals(4,c);
    }

    @Test
    public void testRemove() {
        ArrayDeque<Integer> temp = new ArrayDeque<>();
        temp.addFirst(1);
        temp.addFirst(2);
        temp.addFirst(3);
        temp.addFirst(4);
        temp.addFirst(5);
        temp.addFirst(6);

        int a = temp.removeFirst();
        assertEquals(6, a);
    }
}