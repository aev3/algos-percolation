/**
 * Percolation package info.
 *
 * <p>
 * <b>Model</b>: We model a percolation system using an N-by-N grid of sites.
 * Each site is either open or blocked. A full site is an open site that can be
 * connected to an open site in the top row via a chain of neighboring (left,
 * right, up, down) open sites. We say the system percolates if there is a full
 * site in the bottom row. In other words, a system percolates if we fill all
 * open sites connected to the top row and that process fills some open site on
 * the bottom row. (For the insulating/metallic materials example, the open
 * sites correspond to metallic materials, so that a system that percolates has
 * a metallic path from top to bottom, with full sites conducting. For the
 * porous substance example, the open sites correspond to empty space through
 * which water might flow, so that a system that percolates lets water fill open
 * sites, flowing from top to bottom.)
 * </p>
 *
 * <p>
 * The problem: In a famous scientific problem, researchers are interested in
 * the following question: if sites are independently set to be open with
 * probability p (and therefore blocked with probability 1 minus p), what is the
 * probability that the system percolates? When p equals 0, the system does not
 * percolate; when p equals 1, the system percolates. The plots below show the
 * site vacancy probability p versus the percolation probability for 20-by-20
 * random grid (left) and 100-by-100 random grid (right). When N is sufficiently
 * large, there is a threshold value p* such that when p
 * < p* a random N-by-N grid almost never percolates, and * when p >
 * p*, a random N-by-N grid almost always percolates. No mathematical solution
 * for determining the percolation threshold p* has yet been derived. Your task
 * is to write a computer program to estimate p*.
 * </p>
 *
 * <p>
 * Simulate a system to see its Percolation Threshold, but use a UnionFind
 * implementation to determine whether simulation occurs. The main idea is that
 * initially all cells of a simulated WeightedQuickUnionFunction are each part
 * of their own set so that there will be n^2 sets in an n X n simulated wquf.
 * Finding an open cell will connect the cell being marked to its neighbors ---
 * this means that the set in which the open cell is 'found' will be unioned
 * with the sets of each neighboring cell. The union/find implementation
 * supports the 'find' and 'union' typical of UF algorithms
 * </p>
 *
 * <p>
 * Interface:
 * </p>
 *
 * <pre>
 * {@code
 * public class Percolation {
 * // create N-by-N grid, with all sites blocked
 *  public Percolation(int N)
 *  // open site (row i, column j) if it is not already
 *  public void open(int i, int j)
 *  // is site (row i, column j) open?
 *  public boolean isOpen(int i, int j)
 *  // is site (row i, column j) full?
 *  public boolean isFull(int i, int j)
 *  // does the system percolate?
 *  public boolean percolates()
 *  }
 * </pre>
 */
