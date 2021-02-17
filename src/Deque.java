import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class LinkedList<Item> {

        private Node first = null, last = null;
        private int size = 0;

        private class Node {
            Item item;
            Node next;
            Node prev;
        }

        public void addFirst(Item item) {
            if (item == null)
                throw new IllegalArgumentException();
            if (isEmpty()) {
                first = new Node();
                last = first;
            } else {
                Node oldfirst = first;
                first = new Node();
                first.next = oldfirst;
                oldfirst.prev = first;
            }
            first.item = item;
            size++;
        }

        public void addLast(Item item) {
            if (item == null)
                throw new IllegalArgumentException();
            if (isEmpty()) {
                first = new Node();
                last = first;
            } else {
                Node oldlast = last;
                last = new Node();
                oldlast.next = last;
                last.prev = oldlast;
            }
            last.item = item;
            size++;
        }

        public Item removeFirst() {
            if (isEmpty())
                throw new NoSuchElementException();
            Node oldfirst = first;
            first = first.next;
            if (first == null)
                last = null;
            size--;
            return oldfirst.item;
        }

        public Item removeLast() {
            if (isEmpty())
                throw new NoSuchElementException();
            Node oldlast = last;
            last = last.prev;
            if (last == null)
                first = null;
            size--;
            return oldlast.item;
        }

        public Boolean isEmpty() {
            return first == null;
        }

        public int size() {
            return size;
        }

    }

    private class ListIterator<Item> implements Iterator<Item> {

        private Deque.LinkedList.Node current = list.last;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null)
                throw new NoSuchElementException();
            Item value = (Item) current.item;
            current = current.prev;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private LinkedList<Item> list;

    // construct an empty deque
    public Deque() {
        list = new LinkedList<>();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // return the number of items on the deque
    public int size() {
        return list.size();
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        list.addFirst(item);
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        list.addLast(item);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        return list.removeFirst();
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        return list.removeLast();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator<>();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("A");
        deque.addFirst("B");
        deque.addFirst("C");
        Iterator<String> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
