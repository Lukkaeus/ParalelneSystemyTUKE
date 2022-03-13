package Matrix;
import java.util.Date;
import Matrix.MatrixGeneratorUtil;
import Matrix.ParallelThreadsCreator;
/**
 * Normal way to do matrix multiplication.
 *
 * @author JavaProgramTo.com
 *
 */
public class MatrixMultiplicationParallel {

    public static void main(String[] args) {
        int matrixSize = 2000;

        int[][] m1 = MatrixGeneratorUtil.generateMatrix(matrixSize, matrixSize);
        int[][] m2 = MatrixGeneratorUtil.generateMatrix(matrixSize, matrixSize);

        int[][] result = new int[m1.length][m2[0].length];
        Date start = new Date();
        ParallelThreadsCreator.multiply(m1, m2, result);

        Date end = new Date();
        System.out.println("\nTime taken in milli seconds: " + (end.getTime() - start.getTime()));

    }

}
