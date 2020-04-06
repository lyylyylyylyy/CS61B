import java.util.Deque;
import java.util.LinkedList;

public class Palindrome{
    public Deque<Character> wordToDeque(String word){
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < word.length(); i++){
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String w){
        Deque<Character> word = wordToDeque(w);

        if (word.size() == 0 || word.size() == 1){
            return true;
        }


        return word.removeFirst() == word.removeLast();
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        if (word.length() == 0 || word.length() == 1){
            return true;
        }

        int len = word.length();
        int x = (int) Math.floor(word.length() / 2);

        for (int idx = 0; idx <= x; idx++){
            if (!cc.equalChars(word.charAt(idx), word.charAt(len-idx-1))){
                return false;
            }
        }
        return true;

    }
}

