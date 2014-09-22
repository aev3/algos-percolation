/**
 * *************************************************************************
 * Compilation: javac Percolation.java Execution: java Percolation input.txt
 * Dependencies: StdDraw.java In.java
 * <p/>
 * This program takes the name of a file as a command-line argument. From that
 * file, it - Reads the grid size N of the percolation system. - Creates an
 * N-by-N grid of sites (initially all blocked) - Reads in a sequence of sites
 * (row i, column j) to open.
 * <p/>
 * After each site is opened, it draws full sites in light blue, open sites
 * (that aren't full) in white, and blocked sites in black, with with site (1,
 * 1) in the upper left-hand corner.
 * **************************************************************************
 */
public class Percolation {

    /**
     * Instance variable that will represent the number of items in each row of
     * the row * column matrix.
     */
    private int row;

    /**
     * Instance variable that will represent the number of items in each column
     * of the row * column matrix.
     */
    private int col;

    /**
     * Instance variable that will contain the row * column matrix of booleans.
     */
    private boolean[][] matrix;

    /**
     * An instance variable that will be used to track the number of opened
     * cells in the matrix.
     */
    private double opened;

    /**
     * An instance variable that will be used to track the number of closed
     * cells in the matrix. It is initialized to a default value in the
     * constructor.
     */
    private double closed;

    /**
     * Virtual top row for tracking percolation.
     */
    private WeightedQuickUnionUF wquTop;

    /**
     * Virtual bottom row for tracking percolation.
     */
    private WeightedQuickUnionUF wquBottom;

    /**
     * Constructor that uses the parameter n to create a 2D matrix of size n*n.
     * N-by-N grid is instantiated with all sites blocked.
     *
     * @param n - int that is used to create a 2D matrix of size n*n
     */
    public Percolation(final int n) {
        this.row = n;
        this.col = n;
        wquTop = new WeightedQuickUnionUF(row * col + 2);
        wquBottom = new WeightedQuickUnionUF(row * col + 2);
        matrix = new boolean[row][col];
        closed = row * col;
        opened = 0.0;
    }

    /**
     * Method that opens a site (row i, column j) if it is not already open.
     *
     * @param i - int that will be used to set row data
     * @param j - int that will be used to set column data
     */
    public final void open(final int i, final int j) {
        // open site (row i, column j) if it is not already
        validate(i, j);
        if (!isOpen(i, j)) {
            matrix[i - 1][j - 1] = true;

            unite(i, j, i - 1, j);
            unite(i, j, i + 1, j);
            unite(i, j, i, j - 1);
            unite(i, j, i, j + 1);

            if (i == 1) { // connect to virtual top site
                wquTop.union(0, xyTo1D(i, j));
                wquBottom.union(0, xyTo1D(i, j));
            }
            if (i == row) { // connect to
                wquTop.union(1, xyTo1D(i, j));
            }
        }
    }

    /**
     * <p>
     * Used to determine if a site with row = i and column = j is open.
     * </p>
     *
     * @param i - int used with j to determine if a site is open
     * @param j - int used with i to determine if a site is open
     * @return - boolean that indicates whether the site at [i,j] is open
     */
    public final boolean isOpen(final int i, final int j) {
        validate(i, j);
        return matrix[i - 1][j - 1];
    }

    /**
     * Method to determine if a site (row i, column j) is full; i.e., is fully
     * connected to all adjacent rows and columns, left, right, above, and
     * below.
     *
     * @param i - int used with j to determine if a site is open
     * @param j - int used with i to determine if a site is open
     * @return boolean that reflects status of adjacent row, column pairs.
     */
    public final boolean isFull(final int i, final int j) {

        validate(i, j);
        return wquBottom.connected(0, xyTo1D(i, j));
    }

    /**
     * Method that uses the connected method in {@link WeightedQuickUnionUF} to
     * determine if the top and bottom rows are connected, or, more succinctly,
     * does the system percolate?
     *
     * @return boolean value that reflects percolation status; i.e., true, if a
     * path exists from the top to the bottom of the matrix. Otherwise,
     * false.
     */
    public final boolean percolates() {

        return wquTop.connected(0, 1);
    }

    /**
     * <p>
     * Method that connects the each open row and column pair with all adjacent
     * rows, column pairs. Connects new site (i, j) to all adjacent open sites.
     * </p>
     *
     * @param i - int used with j to determine if a site is open
     * @param j - int used with i to determine if a site is open
     */
    public void connect(final int i, final int j) {
    }

    /**
     * @param i - int used with j to determine if a site is open
     * @param j - int used with i to determine if a site is open
     * @return boolean that reflects connected status of i,j value pair.
     */
    public final boolean isConnected(final int i, final int j) {
        return false;
    }

    /**
     * 1-based coordinates.
     *
     * @param i - ith element
     * @param j - jth element
     * @param m - m element
     * @param n - n element
     */
    private void unite(final int i, final int j, final int m, final int n) {
        if (m > 0 && n > 0 && m <= col && n <= row && isOpen(m, n)) {
            wquTop.union(xyTo1D(i, j), xyTo1D(m, n));
            wquBottom.union(xyTo1D(i, j), xyTo1D(m, n));
        }
    }

    /**
     * @param i - int used with j to determine if a site is open
     * @param j - int used with i to determine if a site is open
     * @return int value calculated based upon i,j parameters
     */
    private int xyTo1D(final int i, final int j) {
        validate(i, j);
        return (i - 1) * row + j + 1;
    }

    /**
     * Method that determines whether the i,j values are valid; i.e., they are
     * not 'out of bounds'.
     *
     * @param i - int used with j to determine if a site is open
     * @param j - int used with i to determine if a site is open
     */
    private void validate(final int i, final int j) {
        int x = i - 1;
        int y = j - 1;
        if (x < 0 || y < 0 || x >= row || y >= col) {
            throw new IndexOutOfBoundsException(
                String.format("Indexes i=%s and j=%s are out of bounds!", i, j)
            );
        }
    }

    /**
     * Convenience main method that can be used for command line testing.
     *
     * @param args - String array for cli usage.
     */
    public static void main(final String[] args) {
        int n = Integer.parseInt(args[0]);
        int x;
        int y;
        Percolation p = new Percolation(n);
        Stopwatch sw = new Stopwatch();
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                x = StdRandom.uniform(1, n);
                y = StdRandom.uniform(1, n);
                if (!p.isOpen(x, y)) {
                    p.open(x, y);
                    p.opened++;
                    if (p.percolates()) {
                        System.out.println(" PERCOLATES ");
                        break;
                    }
                }
            }
        }
        System.out.println("elapsed time = " + sw.elapsedTime());
        System.out.println("opened/closed = " + p.opened / p.closed);

    }

}
