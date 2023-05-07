package com.example.demo3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Translator {
    private static final int WORD = 0;
    private static final int OFFSET = 1;
    private static final int LENGTH = 2;
    private final LinkedHashMap<String, List<Slice>> indexMap = new LinkedHashMap<>();
    private final String dictFileName;
    public final Pair pair;

    public Translator(Pair pair) {
        this.dictFileName = pair.getDictFileName();
        File indexFile = new File(pair.getIndexFileName());
        Scanner scanner;
        try {
            scanner = new Scanner(indexFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            this.pair = null;
            return;
        }
        this.pair = pair;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\t");
            List<Slice> pairList = indexMap.get(parts[WORD]);
            if (pairList == null) {
                pairList = new ArrayList<>();
                indexMap.put(parts[WORD], pairList);
            }
            pairList.add(new Slice(Base64.decode(parts[OFFSET]), (int) Base64.decode(parts[LENGTH])));
        }
        scanner.close();
    }

    public List<String> getDefinitions(String word) throws IOException {
        List<Slice> slices = indexMap.get(word);
        List<String> definitions = new ArrayList<>();
        if (slices == null) return definitions;
        for (Slice slice : slices) {
            byte[] bytes = new byte[slice.length];
            RandomAccessFile file = new RandomAccessFile(this.dictFileName, "r");
            file.seek(slice.offset);
            file.read(bytes);
            file.close();
            definitions.add(new String(bytes, StandardCharsets.UTF_8).trim());
        }
        return definitions;
    }

    public void addDefinition(String word, String definition) throws IOException {
        List<Slice> slices = indexMap.get(word);
        int length = definition.getBytes(StandardCharsets.UTF_8).length;
        long offset;
        if (slices == null) {
            // new word, append to the end.
            offset = FileUtils.appendToEnd(this.dictFileName, definition);
        } else {
            // word already has a definition, add a new one after the last definition
            Slice last = slices.get(slices.size() - 1);
            offset = last.offset + last.length;
            FileUtils.insertInTheMiddle(this.dictFileName, definition, offset, 0);
            // update definitions after this one
            updateHashmap(word, length);
        }

        List<Slice> pairList = indexMap.get(word);
        if (pairList == null) {
            pairList = new ArrayList<>();
            indexMap.put(word, pairList);
        }
        pairList.add(new Slice(offset, length));
    }

    public void editDefinition(String word, String newDefinition, int sliceIndex) throws IOException {
        List<Slice> slices = indexMap.get(word);
        Slice slice = slices.get(sliceIndex);
        int oldLength = slice.length;
        FileUtils.insertInTheMiddle(this.dictFileName, newDefinition, slice.offset, slice.length);
        int newLength = newDefinition.getBytes(StandardCharsets.UTF_8).length;
        int difference = newLength - oldLength;
        // update the length of the current definition
        slice.length = newLength;
        // update the offset of the next definitions of this word
        for (int i = sliceIndex + 1; i < slices.size(); i++) {
            Slice s = slices.get(i);
            s.offset += difference;
        }
        // update the rest of definitions after this one
        updateHashmap(word, difference);
    }

    private void updateHashmap(String wordToFind, int difference) {
        boolean found = false;
        for (String key : indexMap.keySet()) {
            if (found) {
                List<Slice> slicesToChange = indexMap.get(key);
                for (Slice s : slicesToChange) {
                    s.offset += difference;
                }
            }
            if (key.equals(wordToFind)) {
                found = true;
            }
        }
    }

    public void saveMap() throws IOException {
        PrintWriter writer = new PrintWriter(pair.getIndexFileName(), StandardCharsets.UTF_8);
        for (String key : indexMap.keySet()) {
            List<Slice> slices = indexMap.get(key);
            for (Slice s : slices) {
                writer.printf("%s\t%s\t%s\n", key, Base64.encode(s.offset), Base64.encode(s.length));
            }
        }
        writer.close();
    }
}