public class OffByN implements CharacterComparator {
    private int diff;

    public OffByN(int n) {
        diff = n;
    }


    @Override
    public boolean equalChars(char x, char y) {
        int difference = x - y;
        if (Math.abs(difference) == diff){
            return true;
        }
        return false;
    }
}