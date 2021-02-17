public class Queue<T> {

    private LinkedList<T> first;
    private LinkedList<T> second;

    public void enqueue(T item) {
        first.push(item);
    }

    public T dequeue() {
        if (!second.isEmpty()) {
            return second.pop();
        }
        while (!first.isEmpty()) {
            second.push(first.pop());
        }
        return second.pop();
    }

}
