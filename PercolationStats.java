import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private double[]    trialsResults;
    private int         numberOfTrials;
    private int         gridSize;
    private double      meanOfExperiments;
    private double      stddevOfExperiments;
    private final double CONFIDENCE_CONSTANT     = 1.96;
    
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n<=0 || trials <= 0) throw new IllegalArgumentException();
        
        gridSize      =          n;
        trialsResults =          new double[trials];
        numberOfTrials=          trials;

        for(int i=0; i<trials; i++){
            trialsResults[i] = runPercolationExperiment();
        }
        
        meanOfExperiments   = StdStats.mean(trialsResults);
        stddevOfExperiments = StdStats.stddev(trialsResults);
        
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return meanOfExperiments;
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return stddevOfExperiments;
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return meanOfExperiments - (CONFIDENCE_CONSTANT * stddevOfExperiments / Math.sqrt(numberOfTrials));
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return meanOfExperiments + (CONFIDENCE_CONSTANT * stddevOfExperiments / Math.sqrt(numberOfTrials));
    }
    
    private double runPercolationExperiment(){    //runsExperiment;
        Percolation experiment = new Percolation(gridSize);
        int randomRow;
        int randomCol;
        
        do{
            randomRow = StdRandom.uniform(gridSize)+1;
            randomCol = StdRandom.uniform(gridSize)+1;
            
            experiment.open(randomRow, randomCol);
        }while(!experiment.percolates());
        
        return experiment.numberOfOpenSites()/(double)(gridSize*gridSize);
    }
            
    public static void main(String[] args)        // test client (described below)
    {
        int argGridSize = Integer.parseInt(args[0]);
        int argNumTrial = Integer.parseInt(args[1]);
        
        PercolationStats experiment = new PercolationStats(argGridSize, argNumTrial);
        String[]         VALUENAMES = new String[] {"mean","stddev","95% confidence interval"};
        
        StdOut.printf("%-23s : %f\n", VALUENAMES[0], experiment.mean());
        StdOut.printf("%-23s : %f\n", VALUENAMES[1], experiment.stddev());
        StdOut.printf("%-23s : [%f, %f]\n", VALUENAMES[1], experiment.confidenceLo(), experiment.confidenceHi());
        
    }

}
