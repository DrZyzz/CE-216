package com.example.demo3;

public class Slice {
    public long offset;
    public int length;

    public Slice(long offset, int length) {
        this.offset = offset;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Slice{" +
                "offset=" + offset +
                ", length=" + length +
                '}';
    }
}
