package StringsParallel;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadsParallelCreator {

    // vytváramé si dany počet vlákien a čakame na ich splnenie... následne opakujeme
    public static void analyze(String[][] matrix) {
        // vytvorenie zakladnych parov key -> value pre mapu
        Map<String,Integer> map= new HashMap();
        map.put("sumOfAllChars", 0);
        map.put("averageNumberOfCharsInRow", 0 );
        map.put("averageNumberOfCharsInWord", 0 );
        map.put("isFree", 1);

        //zadavame počadovaný počet vlakien programu

//        int numberOfThreads = Runtime.getRuntime().availableProcessors();
 //       System.out.println(Runtime.getRuntime().availableProcessors());
 //     int numberOfThreads = 2;
//      int numberOfThreads = 4;
     int numberOfThreads = 8;


        // vytvárame si list pre vlákna
        List<Thread> threads = new ArrayList<>();
        int rows = matrix.length;
        // v cykle vláknam priradujeme počitanie riadku
        for (int i = 0; i < rows; i++) {
            RowAnalyzeWorker task = new RowAnalyzeWorker(matrix, i, map);
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
            if (threads.size() % numberOfThreads == 0) {
                waitForThreads(threads);
            }
        }
        // konečný výpis programu
        System.out.println(map.get("sumOfAllChars"));
        System.out.println(map.get("averageNumberOfCharsInRow"));
        System.out.println(map.get("averageNumberOfCharsInWord"));
    }

    // metoda ktorá zabezpečuje aby sa vlákna dalej nevytvarali ked sme dosiahli maximalny vytvorený počet vlakien
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
