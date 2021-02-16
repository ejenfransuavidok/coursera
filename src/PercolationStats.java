import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

    private int trials;
    private double [] samples;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        check(n);
        check(trials);
        this.trials = trials;
        samples = new double [trials];
        for (int i=0; i<trials; i++) {
            Percolation percolation = new Percolation(n);
            double x = 0;
            while (!percolation.percolates()) {
                int row;
                int col;
                do {
                    int rnd = StdRandom.uniform(n * n) + 1;
                    row = (int) Math.ceil(((double)rnd - 1.0) / (double)n);
                    if (row == 0)
                        row = 1;
                    col = rnd % n;
                    if (col == 0)
                        col = n;
                } while (percolation.isOpen(row, col));
                percolation.open(row, col);
                x++;
            }
            samples [i] = x/(((double)n)*((double)n));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(samples);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(samples);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    private void check(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args [0]);
        int T = Integer.parseInt(args [1]);
        PercolationStats percolationStats = new PercolationStats(n, T);
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = ["
                + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
