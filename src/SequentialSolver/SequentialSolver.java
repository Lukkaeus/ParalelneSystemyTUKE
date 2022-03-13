package SequentialSolver;
import java.util.*;

public class SequentialSolver {

    private static final int GRID_SIZE = 9;
    static Stack<Sudoku> toSolve = new Stack<>();
    static ArrayList<byte[][]> solutions = new ArrayList<>();

    public static void main(String[] args) {

        byte[][] grid = {
                {0, 0, 0, 8, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 8, 3, 0},
                {0, 4, 3, 0, 1, 0, 0, 0, 0},
                {4, 0, 0, 1, 5, 0, 0, 0, 3},
                {0, 0, 2, 7, 0, 4, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 6, 0, 0},
                {0, 0, 0, 0, 0, 6, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 8, 0},
                {0, 0, 4, 0, 0, 0, 0, 0, 1}
        };

        Sudoku startingGrid = new Sudoku(grid,0,0);
        toSolve.push(startingGrid);
        Date start = new Date();
        solveBoard();
        Date end = new Date();
        System.out.println("Time in milli seconds:" + (end.getTime()-start.getTime()));
        System.out.println("Number of solutions:" + solutions.size());
//        for (int i=0;i< solutions.size();i++){
//            printBoard(solutions.get(i));
//            System.out.println("---------------------");
//        }

    }


    private static void printBoard(byte[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                System.out.print(" | ");
                if (column % 3 == 0 && column != 0) {
                    System.out.print("| ");
                }
                System.out.print(board[row][column]);
            }
            System.out.println();

        }
    }

    private static boolean isNumberInRow(byte[][] board, int number, int row) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumberInColumn(byte[][] board, int number, int column) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][column] == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumberInBox(byte[][] board, int number, int row, int column) {
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValidPlacement(byte[][] board, int number, int row, int column) {
        return !isNumberInRow(board, number, row) &&
                !isNumberInColumn(board, number, column) &&
                !isNumberInBox(board, number, row, column);
    }

    static byte[][] clone(byte[][] a) {
        byte[][] b = new byte[a[0].length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                b[i][j] = a[i][j];
            }
        }
        return b;
    }
    private static boolean isSolved (byte[][] grid){
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (grid[row][column] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static Sudoku copyModify(Sudoku s, byte number, int row, int column){
        byte[][] copiedBoard = clone(s.grid);
        copiedBoard[row][column] = number;
        row++;
        Sudoku clone = new Sudoku(copiedBoard,row,column);
        return clone;
    }

    private static void solveBoard() {
        while (toSolve.size() != 0){
            Sudoku sudoku = toSolve.pop();
            if (isSolved(sudoku.grid)){
                solutions.add(sudoku.grid);
                continue;
            }
            if (sudoku.nextRow == 9){
                sudoku.nextRow = 0;
                sudoku.nextCol++;
            }
            if (sudoku.grid[sudoku.nextRow][sudoku.nextCol] != 0){
                sudoku.nextRow++;
                toSolve.push(sudoku);
                continue;
            }
            for(byte i = 1; i <= 9; i++){
                if(isValidPlacement(sudoku.grid, i, sudoku.nextRow, sudoku.nextCol)){
                    Sudoku modifiedCopy = copyModify(sudoku,i, sudoku.nextRow, sudoku.nextCol);
                    toSolve.push(modifiedCopy);
                }
            }
        }

    }

}