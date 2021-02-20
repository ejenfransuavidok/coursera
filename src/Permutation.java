import java.util.Arrays;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int n = Integer.parseInt(args [0]);
        for (String s : Arrays.copyOfRange(args, 1, args.length)) {
            queue.enqueue(s);
            n--;
            if (n == 0)
                break;
        }
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }

}
