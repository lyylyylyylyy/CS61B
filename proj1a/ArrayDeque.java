public class ArrayDeque<T>{
    private static int initialCapacity = 8; // The starting size of your array should be 8.
    private int capacity; // The length of array
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    /** Creates an empty linked array deque */
    public ArrayDeque() {
        capacity = initialCapacity;
        items = (T []) new Object[initialCapacity];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    public int goFoward(int index){
        if (index==0){
            return capacity-1;
        } else {
            return index-1;
        }
    }


    public int goBack(int index){
        if (index==capacity-1){
            return 0;
        } else {
            return index+1;
        }
    }
    public void resize(int newcapacity){
        T[] newItems = (T[]) new Object[newcapacity];
        int newFirst = nextFirst;
        int newLast = nextLast;

        if (newFirst < newLast){
            int length = newLast-newFirst+1;
            System.arraycopy(items,newFirst,newItems,0,length);
            nextFirst=length;
            nextLast=newcapacity-1;
        } else {
            int lenFirst = capacity-newFirst;
            int newCurrentFirst = newcapacity-lenFirst;
            int lenLasts = nextLast;
            System.arraycopy(items, newFirst,newItems,newCurrentFirst,lenFirst);
            System.arraycopy(items,0,newItems,0,lenLasts);
            nextFirst = newcapacity-lenFirst-1;
        }
        capacity=newcapacity;
        items = newItems;
    }
    public void expand(){
        if (size==capacity){
            int newcapacity = capacity*2;
            resize(newcapacity);
        }
    }

    public void contract(){
        double ratio = (double) size/capacity;
        if (ratio < 0.25){
            int newCapacity = capacity/2;
            resize(newCapacity);
        }
    }

    /*Adds an item of type T to the front of the deque.*/
    public void addFirst(T item){
        items[nextFirst] = item;
        nextFirst = goFoward(nextFirst);
        size = size + 1;
        expand();
    }

    /*Adds an item of type T to the back of the deque.*/
    public void addLast(T item){
        items[nextLast] = item;
        nextLast = goBack(nextLast);
        size = size + 1;
        expand();
    }

    /*Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty(){
        if (size==0){
            return true;
        } else {
            return false;
        }
    }

    /*Returns the number of items in the deque.*/
    public int size(){
        return size;
    }

    /*Prints the items in the deque from first to last, separated by a space.*/
    public void printDeque(){
        for (int i=0; i<capacity;i++){
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    /*Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
    public T removeFirst(){
        if (isEmpty()){
            return null;
        }
        T removed = items[nextFirst];
        items[nextFirst]=null;
        nextFirst = goFoward(nextFirst);
        size = size-1;
        return removed;
    }

    /*Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    public T removeLast(){
        if (isEmpty()){
            return null;
        }
        T removed = items[nextLast];
        items[nextLast]=null;
        nextLast = goBack(nextLast);
        size = size-1;
        return removed;
    }

    /*Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index){
        if (index >= size){
            return null;
        }
        int currentIndex = nextFirst+1+index;
        if (currentIndex >= capacity){
            currentIndex = currentIndex - capacity;
        }
        return items[currentIndex];
    }
}