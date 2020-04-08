import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arr = new ArrayDequeSolution<>();

        // addFirst
        for (int i = 0; i < 10; i++) {
            int x = StdRandom.uniform(100);
            stu.addFirst(x);
            arr.add(x);
        }

        for (int i = 0; i < 10; i++) {
            int st = stu.get(i);
            int ar = arr.getRecursive(i);
            assertEquals("Oh noooo!\nThis is bad:\n   Random number " + st
                            + " not equal to " + ar + "!",
                    ar, st);
        }

        // removeFirst
        for (int i = 0; i < 10; i++) {
            int student = stu.removeFirst();
            int array = arr.removeFirst();
            assertEquals("Oh noooo!\nThis is bad:\n   Random number " + stu
                            + " not equal to " + arr + "!",
                    arr, stu);
        }


        // addLast
        for (int i = 0; i < 10; i++) {
            int x = StdRandom.uniform(100);
            stu.addLast(x);
            arr.addLast(x);
        }

        for (int i = 0; i < 10; i++) {
            int st = stu.get(i);
            int ar = arr.getRecursive(i);
            assertEquals("Oh noooo!\nThis is bad:\n   Random number " + st
                            + " not equal to " + ar + "!",
                    ar, st);
        }

        // removeLast
        for (int i = 0; i < 10; i++) {
            int student = stu.removeLast();
            int array = arr.removeLast();
            assertEquals("Oh noooo!\nThis is bad:\n   Random number " + stu
                            + " not equal to " + arr + "!",
                    arr, stu);
        }

    }
}