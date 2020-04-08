import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arr = new ArrayDequeSolution<>();

        String output = "";

        // addFirst
        for (int i = 0; i < 10; i++) {
            int x = StdRandom.uniform(100);
            stu.addFirst(x);
            arr.addFirst(x);
            output = output + "\naddFirst("+ x +")";
        }

        for (int i = 0; i < 10; i++) {
            int st = stu.get(i);
            int ar = arr.getRecursive(i);
            assertEquals(output, ar, st);
        }

        // removeFirst
        for (int i = 0; i < 10; i++) {
            int student = stu.removeFirst();
            int array = arr.removeFirst();
            output = output + "\nremoveFirst()";
            assertEquals(output, array, student);
        }


        // addLast
        for (int i = 0; i < 10; i++) {
            int x = StdRandom.uniform(100);
            stu.addLast(x);
            arr.addLast(x);
            output = output + "\naddLast("+ x +")";
        }

        for (int i = 0; i < 10; i++) {
            int st = stu.get(i);
            int ar = arr.getRecursive(i);
            assertEquals(output, ar, st);
        }

        // removeLast
        for (int i = 0; i < 10; i++) {
            int student = stu.removeLast();
            int array = arr.removeLast();
            output = output + "\nremoveLast()";
            assertEquals(output, array, student);
        }

    }
}