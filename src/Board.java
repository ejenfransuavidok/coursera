import java.util.Arrays;
import java.util.Iterator;

public class Board {

    private final char [][] tiles;
    private final int hash;
    private final int manhattan;
    private final int hamming;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException();
        this.tiles = new char [tiles.length][tiles[0].length];
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles[i].length; j++)
                this.tiles [i][j] = (char)tiles [i][j];
        hash = Arrays.hashCode(this.tiles);
        manhattan = manhattanPrivate();
        hamming = hammingPrivate();
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tiles.length);
        sb.append('\n');
        for (int i=0; i<tiles.length; i++) {
            for (int j=0; j<tiles[i].length; j++) {
                if (j == 0)
                    sb.append((int)tiles [i][j]);
                else
                    sb.append(" ").append((int)tiles[i][j]);
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
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    private int manhattanPrivate() {
        int d=0;
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles[i].length; j++) {
                int v = tiles [i][j];
                if (v == 0)
                    continue;
                int vi = (v - 1) / tiles[i].length;
                int vj = (v - 1) % tiles[i].length;
                d += (Math.abs(i - vi) + Math.abs(j - vj));
            }
        return d;
    }

    private int hammingPrivate() {
        int d=0;
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles[i].length; j++) {
                int n = 1 + i * tiles[i].length + j;
                if (tiles[i][j] == 0)
                    continue;
                if (tiles[i][j] != n)
                    d += 1;
            }
        return d;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int N = 1;
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles[i].length; j++)
                if (i==tiles.length-1 && j==tiles[i].length-1 && tiles[i][j]==0)
                    return true;
                else if (tiles [i][j]!=N++)
                    return false;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board))
            return false;
        Board other = (Board) y;

        if (tiles.length!=other.tiles.length)
            return false;

        for (int i=0; i<tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++)
                if (tiles[i][j] != other.tiles[i][j])
                    return false;
        }

        return true;//this.hash == other.hash;
    }

    private class BoardIterator implements Iterator<Board> {

        private Board [] boards = new Board [4];
        private int N = 0;
        private int current = 0;

        public BoardIterator() {
            for (int i=0; i<tiles.length; i++)
                for (int j=0; j<tiles[i].length; j++)
                    if (tiles [i][j] == 0) {
                        if (i > 0) {
                            // пусто в верх
                            int [][] array = copyTiles();
                            up(array, i, j);
                            boards [N++] = new Board(array);
                        }
                        if (i < tiles.length - 1) {
                            // пусто вниз
                            int [][] array = copyTiles();
                            down(array, i, j);
                            boards [N++] = new Board(array);
                        }
                        if (j > 0) {
                            // пусто в влево
                            int [][] array = copyTiles();
                            left(array, i, j);
                            boards [N++] = new Board(array);
                        }
                        if (j < tiles[i].length - 1) {
                            // пусто в право
                            int [][] array = copyTiles();
                            right(array, i, j);
                            boards [N++] = new Board(array);
                        }
                    }
        }

        private void right(int[][] array, int i, int j) {
            array[i][j] = array[i][j+1];
            array[i][j+1] = 0;
        }

        private void left(int[][] array, int i, int j) {
            array[i][j] = array[i][j-1];
            array[i][j-1] = 0;
        }

        private void down(int[][] array, int i, int j) {
            array[i][j] = array[i+1][j];
            array[i+1][j] = 0;
        }

        private void up(int[][] array, int i, int j) {
            array[i][j] = array[i-1][j];
            array[i-1][j] = 0;
        }

        private int[][] copyTiles() {
            int[][] array = new int [tiles.length][tiles.length];
            for(int i=0; i<tiles.length; i++)
                for(int j=0; j<tiles[i].length; j++)
                    array [i][j] = tiles [i][j];
            return array;
        }

        @Override
        public boolean hasNext() {
            return current < N;
        }

        @Override
        public Board next() {
            return boards[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class BoardsList implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return new BoardIterator();
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new BoardsList();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int N = 1;
        int i1=-1, j1=-1, i2=-1, j2=-1;
        int [][] t = new int [tiles.length][tiles.length];
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles[i].length; j++)
                t [i][j] = tiles [i][j];
        for (int i=0; i<tiles.length; i++)
            for (int j=0; j<tiles[i].length; j++) {
                if (tiles [i][j] != N && tiles [i][j] != 0) {
                    if (i1==-1) {
                        i1 = i;
                        j1 = j;
                    } else if(i2==-1) {
                        i2 = i;
                        j2 = j;
                    }
                }
                N++;
            }
        if (i1 != -1 && i2 != -1) {
            char temp = (char)t [i1][j1];
            t [i1][j1] = t [i2][j2];
            t [i2][j2] = temp;
            return new Board(t);
        }
        for (int i=0; i<tiles.length; i++) {
            if (i1!=-1 && i2!=-1)
                break;
            for (int j=0; j<tiles[i].length; j++)
                if (tiles[i][j]!=0 && i1==-1) {
                    i1 = i;
                    j1 = j;
                } else if (tiles[i][j]!=0 && i1!=-1 && i2==-1) {
                    i2 = i;
                    j2 = j;
                }
        }
        char temp = (char)t [i1][j1];
        t [i1][j1] = t [i2][j2];
        t [i2][j2] = temp;
        return new Board(t);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
