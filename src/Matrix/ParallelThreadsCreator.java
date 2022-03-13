package Matrix;
import java.util.ArrayList;
import java.util.List;
import Matrix.RowMultiplyWorker;
public class ParallelThreadsCreator {

    // creating 10 threads and waiting for them to complete then again repeat steps.
    public static void multiply(int[][] matrix1, int[][] matrix2, int[][] result) {
//        int numberOfThreads = Runtime.getRuntime().availableProcessors();
//        int numberOfThreads = 2;
        int numberOfThreads = 4;
//        int numberOfThreads = 8;

        List<Thread> threads = new ArrayList<>();
        int rows1 = matrix1.length;
        for (int i = 0; i < rows1; i++) {
            RowMultiplyWorker task = new RowMultiplyWorker(result, matrix1, matrix2, i);
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
//            System.out.println(threads.size());
            if (threads.size() % numberOfThreads == 0) {
                waitForThreads(threads);
            }
        }
    }

    private static void waitForThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threads.clear();
    }
}
