package StringsParallel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RowAnalyzeWorker implements Runnable {
    private final String[][] matrix;
    private final int row;
    public Map<String,Integer> map;

    public RowAnalyzeWorker(String[][] matrix, int row, Map map) {
        // inicializačne premenne v konštruktore
        this.matrix = matrix;
        this.row = row;
        this.map = map;
    }

    // inicializačna metoda run, ktora spúšta sort, rowdata.... tato trieda už beží v osobitnom vlákne
    @Override
    public void run() {
            sort(matrix[row]);
        try {
            rowData(matrix[row]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // metoda na triedenie typu selection sort, ktorá triedy slova podla dlžky v riadku
    void sort(String arr[])
    {
        int n = arr.length;

        for (int i = 0; i < n-1; i++)
        {
            // najdeme minimalnu hodnotu v neutriedenom poli
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j].length() < arr[min_idx].length())
                    min_idx = j;

            // vymenime najdenu hodnotu s prvym prvkom
            String temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }

    // metoda na ratanie priemerneho počtu znakov pre slova v riadku
    public void rowData(String arr[]) throws InterruptedException {
        int wordsCount = 0;
        int charsInRow = 0;
        int average = 0;
        Thread.sleep(10);
        for (int i = 0; i < 608; i++)
        {
            charsInRow = charsInRow + arr[i].length();
            wordsCount++;
        }
        average = charsInRow/wordsCount;
       // System.out.println("row: " + row +"Number of characters in row is: "+  charsInRow+ "     Number of words in a row is: " + wordsCount + "      Average char length in a row: " + average) ;
       // zavolanie metody checkAndAct ktorá sa stará o kritickú sekciu aby sa nestalo že viacere vlakna chcu hodnotu do mapy zapísať naraz
        checkAndAct(charsInRow, average);

    }
    // metoda checkAndActsa stará o kritickú sekciu aby sa nestalo že viacere vlakna chcu hodnotu do mapy zapísať naraz
    // podstatna je hodnota kluča "isFree" ktorá hovorí o tom či práve prebieha zápis do mapy, vlaknu si tuto hodnotu na začiatku nastaví
    // na 0 čo hovorí že niekto práve zapisuje do mapy, ked sa zápis ukonči nastavíme hodnotu "isFree" na 1 a môže iné vlákno začať zapisovať
    public void checkAndAct(int charsInRow, int average) throws InterruptedException {
        while (true){
            if(map.get("isFree") == 1){
                map.put("isFree", 0);
                map.put("sumOfAllChars", map.get("sumOfAllChars")+ charsInRow);
                map.put("averageNumberOfCharsInRow", (map.get("averageNumberOfCharsInRow")+charsInRow)/2);
                map.put("averageNumberOfCharsInWord", (map.get("averageNumberOfCharsInWord")+average)/2);
                map.put("isFree", 1);
                break;
            } else {
                System.out.println("Waiting......");
            }
        }
    }
}
