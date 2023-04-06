package com.example.plswork;

public class Base64Decoder {

    public static long decode (String encodedNumber){
        long sum = 0;
        for (int i = 0; i < encodedNumber.length(); i++){
            sum += decodeADigit(encodedNumber.charAt(i)) * (long) Math.pow(64, encodedNumber.length() - 1 - i);
        }
        return sum;

    }

    private static long decodeADigit(char digit) {
        int asciiValue = digit;
        if (digit >= 'A' && digit <= 'Z') {
            return asciiValue - 65;
        } else if (digit >= 'a' && digit <= 'z') {
            return asciiValue - 71;
        } else if (digit >= '0' && digit <= '9') {
            return asciiValue + 4;
        } else if (digit == '+') {
            return 62;
        } else if( digit == '/'){
            return 63;
        }
        return 0;


    }
}
