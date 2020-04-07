
public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++){
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordList = wordToDeque(word);

        if (wordList.size() == 0 || wordList.size() == 1) {
            return true;
        }


        return wordList.removeFirst() == wordList.removeLast();
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1){
            return true;
        }

        int len = word.length();
        int x = (int) Math.floor(word.length() / 2);

        for (int idx = 0; idx < x; idx++) {
            if (!cc.equalChars(word.charAt(idx), word.charAt(len-idx-1))) {
                return false;
            }
        }
        return true;

    }


}

