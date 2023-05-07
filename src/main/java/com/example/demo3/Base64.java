package com.example.demo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Base64 {

    public static long decode(String encodedNumber) {
        long sum = 0;
        for (int i = 0; i < encodedNumber.length(); i++) {
            sum += decodeADigit(encodedNumber.charAt(i)) * (long) Math.pow(64, encodedNumber.length() - 1 - i);
        }
        return sum;
    }

    public static String encode(long number){
        List<Integer> digits = new ArrayList<>();
        while(number > 0){
            int remainder = (int) (number % 64);
            digits.add(remainder);
            number /= 64;
        }
        Collections.reverse(digits);
        StringBuilder encoded = new StringBuilder();
        for(int a : digits){
            encoded.append(encodeADigit(a));
        }
        return encoded.toString();
    }
    private static String encodeADigit(int digit) {
        String base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        return Character.toString(base64Chars.charAt(digit));
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
        } else if (digit == '/') {
            return 63;
        }
        return 0;
    }
}
