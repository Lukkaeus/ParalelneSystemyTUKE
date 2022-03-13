package StringsSequence;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class GenerateStrings {
    // premenná x určite rozmery matice
    // premenná Map, uchovava mapu potrebných údajov
    public int x= 608;
    Map<String,Integer> map= new HashMap();

    // metoda na načítanie slov zo súboru a naplnanie 2D pola s týmito slovami
    public String[][] readStringsFromFile(String path) throws IOException {
        // vytvorenie zakladnych parov key -> value pre mapu
        map.put("sumOfAllChars", 0);
        map.put("averageNumberOfCharsInRow", 0 );
        map.put("averageNumberOfCharsInWord", 0 );

        // načítavanie zo suboru a naplnanie 2D pola v cykle
        FileInputStream inputStream = null;
        Scanner sc = null;
        String[][] words = new String[x][x];
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < x; j++) {
                    words[i][j] = sc.next();
                }
            }

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
        return words;
    }

    // metoda ktorá pre kazdy riadok zavolá metody sort a rowdata
    public void analyzeStrings(String[][] dic) throws IOException, InterruptedException {
        String[][] strings = dic;
        for (int i = 0; i < x; i++) {
                sort(strings[i]);
                rowData(strings[i]);
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
    public double rowData(String arr[]) throws InterruptedException {
        int wordsCount = 0;
        int charsInRow = 0;
        Thread.sleep(10);
        for (int i = 0; i < x; i++)
        {
            charsInRow = charsInRow + arr[i].length();
            wordsCount++;
        }
       // System.out.println("Number of characters in row is: "+  charsInRow+ "     Number of words in a row is: " + wordsCount + "      Average char length in a row: " + charsInRow/wordsCount) ;
        map.put("sumOfAllChars", map.get("sumOfAllChars")+ charsInRow);
        map.put("averageNumberOfCharsInRow", (map.get("averageNumberOfCharsInRow")+charsInRow)/2);
        map.put("averageNumberOfCharsInWord", (map.get("averageNumberOfCharsInWord")+(charsInRow/wordsCount))/2);
        return charsInRow/wordsCount;
    }

    // metóda na vypísanie výslednych hodnôt programu
    public void printMap(){
        System.out.println(map.get("sumOfAllChars"));
        System.out.println(map.get("averageNumberOfCharsInRow"));
        System.out.println(map.get("averageNumberOfCharsInWord"));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // načítame si subor zo slovami a uložíme do matice "dic"
        Date start2 = new Date();
        GenerateStrings g = new GenerateStrings();
        String[][] dic = g.readStringsFromFile("C:\\Users\\Lukašenko\\IdeaProjects\\Paralelne\\src\\words.txt");
        Date end2 = new Date();
        System.out.println("\nTime taken in milli seconds to load words to array: " + (end2.getTime() - start2.getTime()));

        // zavolame metódu analyze strings ktorá je jadrom tohto programu
        Date start = new Date();
        g.analyzeStrings(dic);
        Date end = new Date();
        System.out.println("\nTime taken in milli seconds for analyzing strings: " + (end.getTime() - start.getTime()));

        // vypíšeme si výsledne hodnoty z mapy
        g.printMap();
    }

}
