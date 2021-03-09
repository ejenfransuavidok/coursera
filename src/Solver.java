import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.Comparator;
import java.util.Arrays;

public class Solver {

    private final int PRIME = 7621;//(1721 - 75%)//(5279-77%)//(9973 - 76%)//(997 - 75%)27449;//173099;

    private class SolutionsIterator implements Iterator<Board> {

        private int current = 0;

        @Override
        public boolean hasNext() {
            return current <= moves;
        }

        @Override
        public Board next() {
            return solutions [current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class SolutionsList implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return new SolutionsIterator();
        }
    }

    private class Solution {
        Board board;
        double priority;
        int manhattan;
        int moves;
        Solution prev;
        Solution(Board b, int m, Solution prev) {
            this.board = b;
            this.moves = m;
            this.manhattan = b.manhattan();
            this.priority = this.manhattan + 1.01*m;
            this.prev = prev;
        }
    }

    private final Comparator<Solution> comparator = (s, t) -> {
        if (s.priority < t.priority)
            return -1;
        else if (s.priority > t.priority)
            return 1;
        return 0;
    };

    private Board initialBoard;
    private MinPQ<Solution> queue = new MinPQ<>(comparator);
    private int moves = -1;
    private Solution tail = null;
    private Board [] solutions;
    private Board [][] doubles = new Board [PRIME][10];
    private int [] doublesCounters = new int [PRIME];
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        initialBoard = initial;
        if (!isSolvable())
            return;
        queue.insert(new Solution(initial, 0, null));
        isDouble(initial);
        while (!queue.isEmpty()) {
            Solution solution = queue.delMin();
            if (solution.board.isGoal()) {
                tail = solution;
                Solution current = tail;
                moves = tail.moves;
                int n = moves + 1;
                solutions = new Board[tail.moves + 1];
                while (current.prev != null) {
                    solutions [--n] = current.board;
                    current = current.prev;
                }
                solutions [0] = current.board;
                return;
            }
            for (Board value : solution.board.neighbors()) {
                if (!isDouble(value)) {
                    queue.insert(new Solution(value, solution.moves + 1, solution));
                }
            }
        }
    }

    private boolean isDouble(Board board) {
        int n = (Math.abs(board.toString().hashCode())) % PRIME;
        int counter = doublesCounters [n];
        for (int i=0; i<counter; i++) {
            Board b = doubles [n][i];
            if (b.equals(board))
                return true;
        }
        doubles [n][counter++] = board;
        doublesCounters [n] = counter;
        if (doubles [n].length == counter)
            doubles [n] = Arrays.copyOf(doubles [n], doubles [n].length*2);
        return false;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !initialBoard.twin().isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (moves == -1)
            return moves;
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        return new SolutionsList();
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}