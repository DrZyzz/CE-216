import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {




//        String encodedNumber = "SaR7"; // Base 64 Number
//        long number = Base64Decoder.decode(encodedNumber); // Should return decimal number = 4826235

//        ArrayList<Translator> langList = new ArrayList<>();
//        for(Languages source : Languages.values()){
//            for(Languages target : Languages.values()){
//                if(source == target) continue;
//                Pair pair = new Pair(source, target);
//                Translator translator = new Translator(pair);
//                langList.add(translator);
//            }
//        }

        Scanner inputScanner = new Scanner(System.in);

        Pair pair = new Pair(Languages.ENG, Languages.TUR);
        Translator translator = new Translator(pair);


        System.out.println("Enter a word");
        String word= inputScanner.nextLine();
        List<String> definition = translator.getDefinition(word);
        System.out.println(definition);
        System.out.println(pair);


    }





}
