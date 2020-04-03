import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayDequeTest{

    @Test
    public void testAdd() {
        ArrayDeque<Integer> temp = new ArrayDeque<>();
        temp.addFirst(0);
        temp.addLast(1);
        int a = temp.get(0);
        int b = temp.get(1);
        assertEquals(0,a);
        assertEquals(1,b);
    }

}