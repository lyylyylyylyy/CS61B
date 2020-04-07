import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByN = new OffByN(3);

    @Test
    public void testOffByN() {

        assertTrue(offByN.equalChars('d', 'g'));
        assertFalse(offByN.equalChars('a', 'b'));
    }
}