import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Translator {
    private static final int WORD = 0;
    private static final int OFFSET = 1;
    private static final int LENGTH = 2;
    private final HashMap<String, List<Slice>> indexMap = new HashMap<>();
    private final String dictFileName;

    public Translator(Pair p) throws FileNotFoundException {
        this.dictFileName = p.getDictFileName();
        File indexFile = new File(p.getIndexFileName());
        Scanner scannerForFile = new Scanner(indexFile);
        while (scannerForFile.hasNextLine()) {
            String line = scannerForFile.nextLine();
            String[] parts = line.split("\t");
            List<Slice> slices = indexMap.get(parts[WORD]);
            if(slices == null){
                slices = new ArrayList<>();
                indexMap.put(parts[WORD], slices);
            }

            slices.add(new Slice(Base64Decoder.decode(parts[OFFSET]), (int) Base64Decoder.decode(parts[LENGTH])));
        }
        scannerForFile.close();
    }

    public List<String> getDefinition(String word) throws IOException {
        List<Slice> slices = this.indexMap.get(word);
        List<String> definitions = new ArrayList<>();
        for(Slice slice : slices){
            byte[] bytes = new byte[slice.length];
            RandomAccessFile file = new RandomAccessFile(dictFileName, "r");
            file.seek(slice.offset);
            file.read(bytes);
            file.close();
            definitions.add(new String(bytes, StandardCharsets.UTF_8).trim()) ;
        }
        return definitions;

    }
}
