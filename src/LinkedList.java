public class LinkedList<T> {

    private Node first = null;

    private class Node {
        T item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(T item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public T pop() {
        T item = first.item;
        first = first.next;
        return item;
    }

}
