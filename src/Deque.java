import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node<Item> {
        Item item;
        Node next;
        Node prev;
    }

    private Node first = null, last = null;
    private int size = 0;

    private class ListIterator<Item> implements Iterator<Item> {

        private Node<Item> current = last;

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

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (isEmpty()) {
            first = new Node<Item>();
            last = first;
        } else {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        first.item = item;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (isEmpty()) {
            first = new Node<Item>();
            last = first;
        } else {
            Node<Item> oldlast = last;
            last = new Node<Item>();
            oldlast.next = last;
            last.prev = oldlast;
        }
        last.item = item;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Node<Item> oldfirst = first;
        first = first.next;
        if (first == null)
            last = null;
        size--;
        return oldfirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Node<Item> oldlast = last;
        last = last.prev;
        if (last == null)
            first = null;
        size--;
        return oldlast.item;
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
        while (!deque.isEmpty()) {
            System.out.println(deque.removeFirst());
        }
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");
        while (!deque.isEmpty()) {
            System.out.println(deque.removeLast());
        }
    }

}
