package ParallelSolver;

import SequentialSolver.Sudoku;

import java.util.LinkedList;

public class SudokuStack {

    public volatile java.util.Stack<SequentialSolver.Sudoku> stack = new java.util.Stack<>();
    public volatile LinkedList<SequentialSolver.Sudoku> solutions = new LinkedList<>();

    public SudokuStack(){}

    public synchronized void push(SequentialSolver.Sudoku s){
        stack.push(s);
    }

    public synchronized SequentialSolver.Sudoku pop(){
        Sudoku s = stack.pop();
        return s;
    }

    public synchronized int size(){
        return stack.size();
    }

    public synchronized boolean isFinished(){
        if(stack.size() == 0) {
            return true;
        }else {
            return false;
        }
    }
}


