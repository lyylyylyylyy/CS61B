public class LinkedListDeque<T>{

    private class Node{
        T item;
        Node prev;
        Node next;

        Node(T i, Node p, Node n){
            item = i;
            prev = p;
            next = n;
        }
    }
    private Node sentinel; // circular
    private int size;

    /*Creates an empty linked list deque.*/
    public LinkedListDeque(){
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /*Adds an item of type T to the front of the deque.*/
    public void addFirst(T item){
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size = size+1;
    }

    /*Adds an item of type T to the back of the deque.*/
    public void addLast(T item){
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size = size + 1;
    }

    /*Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty(){
        if (sentinel.next==sentinel && sentinel.prev==sentinel && size==0){
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
        Node head = sentinel;
        while(head.next != sentinel){
            System.out.print(head.next.item + " ");
            head = head.next;
        }
        System.out.println();
    }

    /*Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
    public T removeFirst(){
        if (sentinel.next == sentinel){
            return null;
        }

        T removed = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size = size - 1;
        return removed;
    }

    /*Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    public T removeLast(){
        if (sentinel.next == sentinel){
            return null;
        }

        T removed = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size = size - 1;
        return removed;
    }

    /*Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index){
        if (index >= size){
            return null;
        }
        Node head = sentinel.next;
        while (index!=0){
            head = head.next;
            index = index-1;
        }
        return head.item;
    }

    public T getRecursiveHelper(Node currentNode, int index){
        if (index==0){
            return currentNode.item;
        }
        return getRecursiveHelper(currentNode.next, index-1);
    }


    /*Same as get, but uses recursion.*/
    public T getRecursive(int index){
        if (index>=size){
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }
}