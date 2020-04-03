import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayDequeTest{

    @Test
    public void testAdd() {
        ArrayDeque<Integer> temp = new ArrayDeque<>();
        temp.addLast(0);
        temp.addLast(1);
        temp.addLast(2);
        temp.addLast(3);
        temp.addLast(4);
        temp.addLast(5);
        temp.addLast(6);
        temp.addLast(7);

        int j = temp.get(0);
        int a = temp.get(1);
        int b = temp.get(2);
        int c = temp.get(3);
        int d = temp.get(4);
        int e = temp.get(5);
        int f = temp.get(6);
        int g = temp.get(7);


        assertEquals(7,g);
    }

    @Test
    public void testRemove() {
        ArrayDeque<Integer> temp = new ArrayDeque<>();
        for (int i=0;i<50;i++){
            temp.addFirst(i);
        }
        for (int j=0;j<50;j++){
            int a = temp.removeFirst();
            assertEquals(49-j, a);
        }
    }
}