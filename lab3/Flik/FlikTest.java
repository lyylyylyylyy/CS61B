import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void Fliktest(){
        int one = 128;
        int two = 128;
        int three = 129;

        boolean same = Flik.isSameNumber(one,two);
        boolean diff = Flik.isSameNumber(one,three);
        assertTrue(same);
        assertFalse(diff);
    }
}