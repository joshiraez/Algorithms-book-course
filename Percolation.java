import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class Percolation {
    
    private boolean[][]             percolationGrid;
    private WeightedQuickUnionUF    percolationModel;
    private WeightedQuickUnionUF    backwashModel;
    private int                     rowLengthOfGrid;
    private int                     numberOfOpenSites;
    private int                     topElementInModel;
    private int                     bottomElemInModel;
    
    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if(n<=0) throw new IllegalArgumentException();
        percolationGrid     = new boolean[n][n];
        int numElements     = n*n;
        percolationModel    = new WeightedQuickUnionUF(numElements + 2);
        backwashModel       = new WeightedQuickUnionUF(numElements + 1);
        rowLengthOfGrid     = n;
        numberOfOpenSites   = 0;
        topElementInModel   = numElements;
        bottomElemInModel   = numElements+1;
    }
    
    public    void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (!correctRowColumn(row,col)) throw new IllegalArgumentException();
        if (isOpen(row,col))            return;
        else
        {
            int     modelRow =   row-1;
            int     modelCol =   col-1;
            int     element =   modelRow*rowLengthOfGrid+modelCol;
            int     toUnion;
            
            //Open
            percolationGrid[modelRow][modelCol] = true;
            numberOfOpenSites++;
            
            //CheckForUnions:
            if(row==1){
                percolationModel.union(element, topElementInModel);
                backwashModel.union(element, topElementInModel);
            }else{
               if(isOpen(row-1, col)){
                    toUnion = (modelRow-1)*rowLengthOfGrid+modelCol; 
                    percolationModel.union(element, toUnion);
                    backwashModel.union(element, toUnion);
               }
            }
            
            if(row==rowLengthOfGrid){
                percolationModel.union(element, bottomElemInModel);
            }else{
               if(isOpen(row+1, col)){
                    toUnion = (modelRow+1)*rowLengthOfGrid+modelCol; 
                    percolationModel.union(element, toUnion);
                    backwashModel.union(element, toUnion);
               }
            }
            
            if(col!=1 && isOpen(row, col-1)){
                toUnion = modelRow*rowLengthOfGrid+modelCol-1;
                percolationModel.union(element, toUnion);
                backwashModel.union(element, toUnion);
            }
            
            if(col!=rowLengthOfGrid && isOpen(row, col+1)){
                toUnion = modelRow*rowLengthOfGrid+modelCol+1;
                percolationModel.union(element, toUnion);
                backwashModel.union(element, toUnion);
            }
        }
    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (!correctRowColumn(row,col)) throw new IllegalArgumentException();
        
        int     modelRow =   row-1;
        int     modelCol =   col-1;
        
        return percolationGrid[modelRow][modelCol];
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (!correctRowColumn(row,col)) throw new IllegalArgumentException();
        if (!isOpen(row,col))           return false;
        else{
            int     modelRow =   row-1;
            int     modelCol =   col-1;
            int     element  =   modelRow*rowLengthOfGrid+modelCol;
            
            return backwashModel.connected(element, topElementInModel);
        }
    }
    public     int numberOfOpenSites()       // number of open sites
    {
        return numberOfOpenSites;
    }
    public boolean percolates()              // does the system percolate?
    {
        return percolationModel.connected(topElementInModel, bottomElemInModel);
    }

    private boolean correctRowColumn(int row, int col) //Checks if row/col inside range
    {
        return row >= 1 && row <=rowLengthOfGrid && col >=1 && col <=rowLengthOfGrid;
    }
    
    public static void main(String[] args)   // test client (optional)
    {
        //=(
    }
}
