package SequentialSolver;

public class Sudoku {
    public byte [][] grid;
    public int nextRow, nextCol;

    public Sudoku(byte [][] grid, int nextRow, int nextCol){
        this.grid = grid;
        this.nextRow = nextRow;
        this.nextCol = nextCol;
    }

}
