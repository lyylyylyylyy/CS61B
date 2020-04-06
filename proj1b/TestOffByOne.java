import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    static CharacterComparator offByOne = (CharacterComparator) new OffByOne();


    @Test
    public void testOffByOne(){
        char s0 = 'a';
        char s1 = 'b';
        char s2 = 'c';

        assertTrue(offByOne.equalChars(s0, s1));
        assertFalse(offByOne.equalChars(s0, s2));
    }
}
