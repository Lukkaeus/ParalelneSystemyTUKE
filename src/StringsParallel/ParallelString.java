package StringsParallel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class ParallelString {

    // metoda na načítanie slov zo súboru a naplnanie 2D pola s týmito slovami
    public String[][] readStringsFromFile(String path) throws IOException {
        // načítavanie zo suboru a naplnanie 2D pola v cykle
        FileInputStream inputStream = null;
        Scanner sc = null;
        String[][] words = new String[608][608];
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");

            for (int i = 0; i < 608; i++) {
                for (int j = 0; j < 608; j++) {
                    words[i][j] = sc.next();
                }
            }
            // note that Scanner suppresses exceptions

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

    public static void main(String[] args) throws IOException {
        // načítame si subor zo slovami a uložíme do matice "dic"
        Date start2 = new Date();
        ParallelString g = new ParallelString();
        String[][] dic = g.readStringsFromFile("C:\\Users\\Lukašenko\\IdeaProjects\\Paralelne\\src\\words.txt");
        Date end2 = new Date();

        System.out.println("\nTime taken in milli seconds to load words to array: " + (end2.getTime() - start2.getTime()));

        // zavolame metódu analyze nad triedou ThreadsParallelCreator strings ktorá je jadrom tohto programu
        Date start = new Date();
        ThreadsParallelCreator.analyze(dic);
        Date end = new Date();
        System.out.println("\nTime taken in milli seconds for analyzing strings: " + (end.getTime() - start.getTime()));
    }
}
