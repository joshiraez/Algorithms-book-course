import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
public class Board {
    private int[][]     board;
    private int         hamming;
    private int         manhattan;
    private int         rowWith0;
    private int         colWith0;
    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        if ( blocks == null ) throw new IllegalArgumentException();
        board       = new int[blocks.length][blocks[0].length];
        hamming     = 0;
        manhattan   = 0;
        for (int i=0; i<board.length; i++){
            for (int j=0; j< board.length; j++){
                board[i][j] = blocks[i][j];
                if( board[i][j] == 0 ){
                    rowWith0 = i;
                    colWith0 = j;
                }else{
                    if(board[i][j] != i*board.length+j+1){
                        hamming++;
                        manhattan += Math.abs((( board[i][j] - 1 ) % board.length ) - j ) +
                                     Math.abs((( board[i][j] - 1 ) / board.length ) - i );
                    }
                }
            }
        }
    }
    public int dimension()                 // board dimension n
    {
        return board.length;
    }
    public int hamming()                   // number of blocks out of place
    {
        return hamming; 
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return manhattan;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return hamming == 0;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        Board twin;
        if( board[0][0] != 0 ){
            if( board[0][1] != 0 ){
                twin = new Board(swap(board, 0, 0, Direction.RIGHT));
            }else{
                twin = new Board(swap(board, 0, 0, Direction.DOWN));
            }
        }else{
            twin = new Board(swap(board, 0, 1, Direction.DOWN));
        }
        return twin;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == null)                              return false;
        if (!(y instanceof Board))                  return false;
        Board other = (Board)y;
        if ( other.hamming != this.hamming )        return false;
        if ( other.manhattan != this.manhattan )    return false;
        for (int i=0; i<board.length; i++){
            for (int j=0; j< board[0].length; j++){
                if ( this.board[i][j] != other.board[i][j] ) return false;
            }
        }
        return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        LinkedList<Board> neighborsBoards = new LinkedList<>();
        if(rowWith0 != 0){
            neighborsBoards.add(new Board(swap(board, rowWith0, colWith0, Direction.UP)));
        }
        if(rowWith0 != board.length-1){
            neighborsBoards.add(new Board(swap(board, rowWith0, colWith0, Direction.DOWN)));
        }
        if(colWith0 != 0){
            neighborsBoards.add(new Board(swap(board, rowWith0, colWith0, Direction.LEFT)));
        }
        if(colWith0 != board[0].length-1){
            neighborsBoards.add(new Board(swap(board, rowWith0, colWith0, Direction.RIGHT)));
        }
        
        return neighborsBoards;
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder result = new StringBuilder();
        result.append(board.length);
        result.append('\n');
        for (int i=0; i<board.length; i++){
            for (int j=0; j< board.length; j++){
                if(board[i][j]<10){
                    result.append(' ');
                }
                result.append(board[i][j]);
                result.append(' ');
            }
            result.append('\n');
        }
        return result.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {}
    
    private enum Direction{
        UP, DOWN, LEFT, RIGHT
    }
    
    private int[][] swap(int[][] original, int rowPositionToSwap, int colPositionToSwap, Direction directionToSwap){
        int[][] swapped = new int[original.length][original[0].length];
        for (int i = 0; i<original.length; i++){
            for (int j = 0; j< original[0].length; j++){
                swapped[i][j] = original[i][j];
            }
        }
        
        int buffer;
        switch(directionToSwap){
            case UP:
                buffer = swapped[rowPositionToSwap-1][colPositionToSwap];
                swapped[rowPositionToSwap-1][colPositionToSwap] =
                        swapped[rowPositionToSwap][colPositionToSwap];
                swapped[rowPositionToSwap][colPositionToSwap] = buffer;
                break;
            case DOWN:
                buffer = swapped[rowPositionToSwap+1][colPositionToSwap];
                swapped[rowPositionToSwap+1][colPositionToSwap] =
                        swapped[rowPositionToSwap][colPositionToSwap];
                swapped[rowPositionToSwap][colPositionToSwap] = buffer;
                break;
            case LEFT:
                buffer = swapped[rowPositionToSwap][colPositionToSwap-1];
                swapped[rowPositionToSwap][colPositionToSwap-1] =
                        swapped[rowPositionToSwap][colPositionToSwap];
                swapped[rowPositionToSwap][colPositionToSwap] = buffer;
                break;
            case RIGHT:
                buffer = swapped[rowPositionToSwap][colPositionToSwap+1];
                swapped[rowPositionToSwap][colPositionToSwap+1] =
                        swapped[rowPositionToSwap][colPositionToSwap];
                swapped[rowPositionToSwap][colPositionToSwap] = buffer;
                break;
        }
        
        return swapped;
    }
    
}