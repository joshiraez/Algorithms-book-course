import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;


public class Solver {
    
    private class BoardNode implements Comparable<BoardNode>{
        private BoardNode   previousBoard;
        private Board       actualBoard;
        private int         numMoves;
        private int         manhattan;
        
        public BoardNode(Board board){
            actualBoard = board;
            manhattan   = board.manhattan();
        }
        
        public BoardNode(Board board, BoardNode previous){
            this(board);
            previousBoard = previous;
            numMoves = previous.numMoves + 1;
        }
        
        private int priority(){
            return manhattan + numMoves;
        }
        
        public int compareTo(BoardNode other){
            return this.priority() - other.priority();
        }
        
    }
    
    private BoardNode solution;
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if ( initial == null ) throw new IllegalArgumentException();
        
        MinPQ<BoardNode> aStarSearch        = new MinPQ<>();
        MinPQ<BoardNode> aStarUnsolvable    = new MinPQ<>();
        aStarSearch.insert(new BoardNode(initial));
        aStarUnsolvable.insert(new BoardNode(initial.twin()));
        
        BoardNode aStarNextMove             = aStarSearch.delMin();
        BoardNode aStarUnsolvableNextMove   = aStarUnsolvable.delMin();
        while(!aStarNextMove.actualBoard.isGoal() &&
              !aStarUnsolvableNextMove.actualBoard.isGoal()){
            for (Board neighbor : aStarNextMove.actualBoard.neighbors()){
                if( aStarNextMove.previousBoard == null ||
                    !neighbor.equals(aStarNextMove.previousBoard.actualBoard ))
                    aStarSearch.insert(new BoardNode(neighbor, aStarNextMove));
            }
            for (Board neighbor : aStarUnsolvableNextMove.actualBoard.neighbors()){
                if( aStarUnsolvableNextMove.previousBoard == null ||
                    !neighbor.equals(aStarUnsolvableNextMove.previousBoard.actualBoard ))
                    aStarUnsolvable.insert(new BoardNode(neighbor, aStarUnsolvableNextMove));
            }
            aStarNextMove                   = aStarSearch.delMin();
            aStarUnsolvableNextMove         = aStarUnsolvable.delMin();
        }
        if(aStarNextMove.actualBoard.isGoal()){
            solution = aStarNextMove;
        }
    }
    public boolean isSolvable()            // is the initial board solvable?
    { return solution != null; }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    { return isSolvable() ? solution.numMoves : -1; }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!isSolvable()) return null;
        LinkedList<Board> solutionPath   = new LinkedList<>();
        BoardNode previousMove      = solution;
        while(previousMove != null){
            solutionPath.addFirst(previousMove.actualBoard);
            previousMove = previousMove.previousBoard;
        }
        
        return solutionPath;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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