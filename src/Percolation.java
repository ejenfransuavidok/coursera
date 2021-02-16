import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int BLOCKED = 0;
    private final int OPENED = 1;
    private final int mostTop;
    private final int mostBottom;
    private int opened = 0;
    private int size = Integer.MAX_VALUE;
    private final WeightedQuickUnionUF data;
    private final char [] lattice;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        check(n);
        size = n;
        lattice = new char [n * n];
        for (int i=0; i<(n * n); i++) {
            lattice [i] = BLOCKED;
        }
        mostTop = n * n;
        mostBottom = n * n + 1;
        data = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        check(row);
        check(col);
        int position = getPosition(row, col);
        lattice [position] = OPENED;
        opened++;
        int top = getPosition(row - 1, col);
        int bottom = getPosition(row + 1, col);
        int left = getPosition(row, col - 1);
        int right = getPosition(row, col + 1);
        if (top != -1 && lattice [top] == OPENED) {
            data.union(position, top);
        }
        if (bottom != -1 && lattice [bottom] == OPENED) {
            data.union(position, bottom);
        }
        if (left != -1 && lattice [left] == OPENED) {
            data.union(position, left);
        }
        if (right != -1 && lattice [right] == OPENED) {
            data.union(position, right);
        }
        if (row == 1) {
            data.union(position, mostTop);
        }
        if (row == size) {
            data.union(position, mostBottom);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        check(row);
        check(col);
        int position = getPosition(row, col);
        return lattice [position] == OPENED;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row);
        check(col);
        int position = getPosition(row, col);
        return data.find(position) == data.find(mostTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        return data.find(mostTop) == data.find(mostBottom);
    }

    private int getPosition(int row, int col) {
        if (col > size || col == 0 || row > size || row == 0)
            return -1;
        return size * (row - 1) + col - 1;
    }

    private void check(int n) {
        if (n <= 0 || n > size)
            throw new IllegalArgumentException();
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        percolation.open(5, 1);
        if (percolation.percolates()) {
            System.out.println("opened: " + percolation.numberOfOpenSites());
            System.out.println("Percolates");
        }
    }

}
