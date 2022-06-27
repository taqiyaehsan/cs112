/**
 * Percolation
 * 
 * @author Ana Paula Centeno
 * @author Haolin (Daniel) Jin
 */

public class Percolation {

	private boolean[][] grid;          // gridSize by gridSize grid of sites; true = open site, false = closed or blocked site
	private WeightedQuickUnionFind wquFind; 
	private int 		gridSize;      // gridSize by gridSize is the size of the grid/system 
	private int         gridSquared;   // number of sites
	private int         virtualTop;    // virtual top index on WeightedQuckUnionFind array
	private int         virtualBottom; // virtual bottom index on WeightedQuckUnionFind array

	/**
	* Constructor.
	* Initializes all instance variables
	*/
	public Percolation ( int n ){
		gridSize 	  = n;
		gridSquared   = gridSize * gridSize;
		wquFind       = new WeightedQuickUnionFind(gridSquared + 2);
		grid          = new boolean[gridSize][gridSize];   // every site is initialized to closed/blocked
		virtualTop    = gridSquared;					// top index in ARRAY
		virtualBottom = gridSquared + 1;				// bottom index in ARRAY
	} 

	/**
	* Getter method for GridSize 
	* @return integer representing the size of the grid.
	*/
	public int getGridSize () {
		return gridSize;
	}

	/**
	 * Returns the grid array
	 * @return grid array
	 */
	public boolean[][] getGridArray () {
		return grid;
	}

	/**
	* Open the site at postion (x,y) on the grid to true and add an edge
	* to any open neighbor (left, right, top, bottom) and/or top/bottom virtual sites
	* Note: diagonal sites are not neighbors
	*
	* @param row grid row
	* @param col grid column
	* @return void
	*/
	public void openSite (int row, int col) {

		if(grid[row][col]!=true) grid[row][col] = true;

		int siteIn1D = TwoDto1D(row, col); 	// what this site would be in the 1D qf array

		// union of adjacent sites
		int t1 = row - 1;
		int t2 = row + 1;
		int t3 = col - 1;
		int t4 = col + 1;

		if(0 <= t1 && t1 <= gridSize-1) connectToAdjacentSite(t1, col, siteIn1D);
		if(0 <= t2 && t2 <= gridSize-1) connectToAdjacentSite(t2, col, siteIn1D);
		if(0 <= t3 && t3 <= gridSize-1) connectToAdjacentSite(row, t3, siteIn1D);
		if(0 <= t4 && t4 <= gridSize-1) connectToAdjacentSite(row, t4, siteIn1D);

		// connect to virtual top or bottom
        if (row == 0){
			wquFind.union(siteIn1D, virtualTop);}

        if (row == gridSize-1) {
            wquFind.union(virtualBottom, siteIn1D);} 

		return;

	}

	/**
	* Maps site from a 2-dimensional (row, col) pair to the 1-dimensional union find array index
	* @param row grid row
	* @param col grid column
	* @return index of site in 1-dimensional union find object
	*/
	private int TwoDto1D(int row, int col) {
        return (gridSize * row) + col;
    }

	/**
     * Connects newly opened site to its adjacent open site
     * @param row index of row in grid
     * @param col index of column in grid
     * @param siteIn1D index of site in 1-dimensional union-find object
     */
    private void connectToAdjacentSite(int row, int col, int siteIn1D) {
		if (grid[row][col] == true) {
			wquFind.union(siteIn1D, TwoDto1D(row, col)); //connection in 1D qf array
		}
		return; 
    }

	/**
	* Check if the system percolates (any top and bottom sites are connected by open sites)
	* @return true if grid percolates, false otherwise
	*/
	public boolean percolationCheck () {
		
        if (gridSize == 1) {
            return grid[0][0];
        }
        else {
           return wquFind.find(virtualTop) == wquFind.find(virtualBottom);
		}
	} 


	/**
	 * Iterates over the grid array opening every site. 
	 * Starts at [0][0] and moves row wise 
	 * @param probability
	 * @param seed
	 */
	public void openAllSites (double probability, long seed) {

		// Setting the same seed before generating random numbers ensure that
		// the same numbers are generated between different runs
		StdRandom.setSeed(seed); // DO NOT remove this line

		for(int i = 0; i < gridSize; i++){
			for(int j = 0; j < gridSize; j++){
				double p = StdRandom.uniform();
				if (p <= probability) {openSite(i, j);}
			}
		}
	}

	/**
	* Open up a new window and display the current grid using StdDraw library.
	* The output will be colored based on the grid array. Blue for open site, black for closed site.
	* @return: void 
	*/
	public void displayGrid () {
		double blockSize = 0.9 / gridSize;
		double zeroPt =  0.05+(blockSize/2), x = zeroPt, y = zeroPt;

		for ( int i = gridSize-1; i >= 0; i-- ) {
			x = zeroPt;
			for ( int j = 0; j < gridSize; j++) {
				if ( grid[i][j] ) {
					StdDraw.setPenColor( StdDraw.BOOK_LIGHT_BLUE );
					StdDraw.filledSquare( x, y ,blockSize/2);
					StdDraw.setPenColor( StdDraw.BLACK);
					StdDraw.square( x, y ,blockSize/2);		
				} else {
					StdDraw.filledSquare( x, y ,blockSize/2);
				}
				x += blockSize; 
			}
			y += blockSize;
		}
	}

	/**
	* Main method, for testing only, feel free to change it.
	*/
	public static void main ( String[] args ) {

		double p = 0.47;
		Percolation pl = new Percolation(4);

		/* 
		 * Setting a seed before generating random numbers ensure that
		 * the same numbers are generated between runs.
		 *
		 * If you would like to reproduce Autolab's output, update
		 * the seed variable to the value Autolab has used.
		 */
		long seed = System.currentTimeMillis();
		pl.openAllSites(p, seed);

		System.out.println("The system percolates: " + pl.percolationCheck());
		pl.displayGrid();
	}
}