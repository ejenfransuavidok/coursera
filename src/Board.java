public class Board {

    private int [][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int [tiles.length][tiles.length];
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles.length; j++)
                this.tiles [i][j] = tiles [i][j];
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tiles.length);
        sb.append('\n');
        for (int i=0; i<tiles.length; i++) {
            for (int j=0; j<tiles.length; j++) {
                if (j == 0)
                    sb.append(tiles [i][j]);
                else
                    sb.append(" ").append(tiles[i][j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int d=0;
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles.length; j++) {
                int n = 1 + i * tiles.length + j;
                if (tiles [i] [j] == n)
                    d += 1;
            }
        return d;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int d=0;
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles.length; j++) {
                int v = tiles [i][j];
                int vi = v / tiles.length;
                int vj = v % tiles.length;
                d += (Math.abs(i - vi) + Math.abs(j - vj));
            }
    }

    // is this board the goal board?
    public boolean isGoal() {

    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board other = (Board) y;
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles.length; j++)
                if (tiles [i][j] != other.tiles [i][j])
                    return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
