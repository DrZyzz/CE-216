import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static final int WORD = 0;
    private static final int OFFSET = 1;
    private static final int LENGTH = 2;

    public static void main(String[] args) throws FileNotFoundException {



//        String encodedNumber = "SaR7"; // Base 64 Number
//        long number = Base64Decoder.decode(encodedNumber); // Should return decimal number = 4826235

        File indexFile = new File("eng-tur.index");
        Scanner scannerForFile = new Scanner(indexFile);
        HashMap<String, Slice> indexMap = new HashMap<>();
        while (scannerForFile.hasNextLine()) {
            String line = scannerForFile.nextLine();
            String[] parts = line.split("\t");
            indexMap.put(parts[WORD], new Slice(Base64Decoder.decode(parts[OFFSET]), (int) Base64Decoder.decode(parts[LENGTH])));
        }

        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Enter a word");
        String word= inputScanner.nextLine();
        Slice returnedSlice = indexMap.get(word);
        System.out.println(returnedSlice);


    }




}
