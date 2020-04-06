import org.junit.Test;

import java.util.Deque;

import static org.junit.Assert.*;

public class TestPalindrome {

    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome("aa"));
        assertFalse(palindrome.isPalindrome("xpp"));
        assertTrue(palindrome.isPalindrome("racecar"));
    }

    @Test
    public void testisPalindromeCc() {
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("ab", obo));
        assertTrue(palindrome.isPalindrome("yyxz", obo));
        assertFalse(palindrome.isPalindrome("zxzx", obo));
        assertFalse(palindrome.isPalindrome("aa", obo));
    }
}
