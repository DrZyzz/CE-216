package com.example.demo3;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    public static long appendToEnd(String filename, String text) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filename, "rw");
        long offset = file.length();
        file.seek(offset);
        file.write(text.getBytes(StandardCharsets.UTF_8));
        file.close();
        return offset;
    }

    public static void insertInTheMiddle(String filename, String text, long offset, long length) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filename, "rw");
        long fileLength = file.length();
        long startPosition = offset;
        long remainingLength = fileLength - startPosition - length;
        byte[] bytesToInsert = text.getBytes(StandardCharsets.UTF_8);
        file.seek(startPosition + length);
        byte[] remainder = new byte[(int) remainingLength];
        file.readFully(remainder);
        file.seek(startPosition);
        file.write(bytesToInsert);
        file.write(remainder);
        file.close();
    }
}
