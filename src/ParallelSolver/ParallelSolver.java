package ParallelSolver;

import SequentialSolver.Sudoku;
import java.util.*;

public class ParallelSolver extends Thread {

    private static final int GRID_SIZE = 9;
    static Stack<SequentialSolver.Sudoku> toSolve = new Stack<>();


    public static void main(String[] args) {

//        byte[][] grid = {
//                {0, 0, 0, 8, 0, 0, 0, 0, 0},
//                {0, 1, 9, 0, 0, 0, 8, 3, 0},
//                {0, 4, 3, 0, 1, 0, 0, 0, 0},
//                {4, 0, 0, 1, 5, 0, 0, 0, 3},
//                {0, 0, 2, 7, 0, 4, 0, 1, 0},
//                {0, 0, 0, 0, 0, 0, 6, 0, 0},
//                {0, 7, 0, 0, 0, 6, 3, 0, 0},
//                {0, 3, 0, 0, 7, 0, 0, 8, 0},
//                {0, 0, 4, 0, 0, 0, 0, 0, 1}
//        };
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

        SequentialSolver.Sudoku startingGrid = new SequentialSolver.Sudoku(grid,0,0);

        toSolve.push(startingGrid);
        Date start = new Date();
        LinkedList<SequentialSolver.Sudoku> allSolutions = solveBoard(8);
        Date end = new Date();
        System.out.println("Time in milli seconds:" + (end.getTime()-start.getTime()));
        System.out.println("Solutions:" +allSolutions.size());

    }
    private SudokuStack stack;

    public ParallelSolver(SudokuStack stack){
        this.stack = stack;
    }

    public void run(){
        while(!stack.isFinished()){
            Sudoku sudoku;
            try{
                sudoku = stack.pop();
            }catch(EmptyStackException ignore){
                sudoku = null;
            }
            if(sudoku != null){
                if (isSolved(sudoku.grid)){
                    stack.solutions.add(sudoku);
                    continue;
                }
                if (sudoku.nextRow == GRID_SIZE){
                    sudoku.nextRow = 0;
                    sudoku.nextCol++;
                }
                if (sudoku.grid[sudoku.nextRow][sudoku.nextCol] != 0){
                    sudoku.nextRow++;
                    stack.push(sudoku);
                    continue;
                }
                for(byte i = 1; i <= 9; i++){
                    if(isValidPlacement(sudoku.grid, i, sudoku.nextRow, sudoku.nextCol)){
                        Sudoku modifiedCopy = copyModify(sudoku,i, sudoku.nextRow, sudoku.nextCol);
                        stack.push(modifiedCopy);
                    }
                }
            }

        }
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
    private boolean isSolved (byte[][] grid){
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (grid[row][column] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private SequentialSolver.Sudoku copyModify(SequentialSolver.Sudoku s, byte number, int row, int column){
        byte[][] copiedBoard = clone(s.grid);
        copiedBoard[row][column] = number;
        row++;
        SequentialSolver.Sudoku clone = new SequentialSolver.Sudoku(copiedBoard,row,column);
        return clone;
    }


    public static LinkedList<SequentialSolver.Sudoku> solveBoard(int threadNumber) {
        ParallelSolver parallelSolvers[] = new ParallelSolver[threadNumber];
        SudokuStack stack = new SudokuStack();
        Sudoku sudoku = toSolve.pop();
        stack.push(sudoku);
        for(int i =0; i < threadNumber; i++){
            parallelSolvers[i] = new ParallelSolver(stack);
        }
        for(ParallelSolver parallelSolver : parallelSolvers) {
            parallelSolver.start();
        }
        for(ParallelSolver parallelSolver : parallelSolvers) {
            try {
                parallelSolver.join();

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
        return stack.solutions;
    }
}