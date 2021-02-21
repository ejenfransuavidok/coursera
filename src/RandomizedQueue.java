import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class Node<Item> {
        Node<Item> next;
        Node<Item> prev;
        Item item;
    }

    private class ListIterator<Item> implements Iterator<Item> {

        private Node current;
        private boolean markers [];
        private int capacity;

        public ListIterator() {
            current = last;
            markers = new boolean[size];
            capacity = size;
        }

        @Override
        public boolean hasNext() {
            return capacity != 0;
        }

        @Override
        public Item next() {
            if (capacity == 0)
                throw new NoSuchElementException();
            int rnd = StdRandom.uniform(size);
            while (markers [rnd])
                rnd = StdRandom.uniform(size);
            markers [rnd] = true;
            capacity--;
            Node current = first;
            for (int i=0; i<rnd; i++) {
                current = current.next;
            }
            return (Item)current.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node<Item> first = null;
    private Node<Item> last = null;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node<Item> oldfirst = first;
        first = new Node<>();
        first.next = oldfirst;
        first.item = item;
        if (oldfirst == null) {
            last = first;
        } else {
            oldfirst.prev = first;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();
        int rnd = StdRandom.uniform(size);
        Node<Item> current = first;
        for (int i=0; i<rnd; i++) {
            current = current.next;
        }
        if (size == 1) {
            first = last = null;
        }
        else if (current == first) {
            first = first.next;
            first.prev = null;
        }
        else if (current == last) {
            last = last.prev;
            last.next = null;
        }
        else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return current.item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        int rnd = StdRandom.uniform(size);
        Node<Item> current = first;
        for (int i=0; i<rnd; i++) {
            current = current.next;
        }
        return current.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator<>();
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}

