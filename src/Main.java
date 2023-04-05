import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    static ArrayList<Translator> translatorsList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // Initilazing the translators
//         Should be executed once when the application starts
        for (Languages source : Languages.values()) {
            for (Languages target : Languages.values()) {
                if (source == target) continue;
                Pair p = new Pair(source, target);
                Translator t = new Translator(p);
                translatorsList.add(t);
            }
        }

            // Using translation
            translate("adam");
    }

    public static void translate(String word) throws IOException {
        for (Translator t : translatorsList) {
            List<String> definitions = t.getDefinition(word);
            Pair p = t.pair;
            System.out.println(p);
            System.out.println(definitions);
            // this function should be written in gui
            // doWhatYouWantWithDefinitionsInGui(definitions)
        }
    }





}
