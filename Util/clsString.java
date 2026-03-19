package Util;
import java.util.ArrayList;
import java.util.List;

public class clsString {

    // Enum for what to count
    public enum WhatToCount {
        SMALL_LETTERS, CAPITAL_LETTERS, ALL_LETTERS
    }

    private String name;

    // Constructors
    public clsString() {
        this.name = "";
    }

    public clsString(String value) {
        this.name = value;
    }

    // Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    // ------------------- String splitting -------------------
    public static List<String> split(String str, String delim) {
    List<String> result = new ArrayList<>();
    int pos;

    while ((pos = str.indexOf(delim)) != -1) {
        result.add(str.substring(0, pos)); // KEEP empty
        str = str.substring(pos + delim.length());
    }

    result.add(str); // KEEP empty last field
    return result;
}


    public List<String> split(String delim) {
        return split(this.name, delim);
    }

    // ------------------- Count words -------------------
    public static int countWords(String str) {
        String[] parts = str.trim().split("\\s+");
        return parts.length;
    }

    public int countWords() {
        return countWords(this.name);
    }

    // ------------------- Trim functions -------------------
    public static String trimLeft(String str) {
        return str.replaceAll("^\\s+", "");
    }

    public static String trimRight(String str) {
        return str.replaceAll("\\s+$", "");
    }

    public static String trim(String str) {
        return trimLeft(trimRight(str));
    }

    public void trimLeft() {
        this.name = trimLeft(this.name);
    }

    public void trimRight() {
        this.name = trimRight(this.name);
    }

    public void trim() {
        this.name = trim(this.name);
    }

    // ------------------- Reverse words -------------------
    public static String reverseWords(String str) {
        List<String> words = split(str, " ");
        StringBuilder reversed = new StringBuilder();
        for (int i = words.size() - 1; i >= 0; i--) {
            reversed.append(words.get(i)).append(" ");
        }
        return reversed.toString().trim();
    }

    public void reverseWords() {
        this.name = reverseWords(this.name);
    }

    // ------------------- Replace word -------------------
    public static String replaceWord(String str, String wordToReplace, String wordReplaceTo) {
        return str.replace(wordToReplace, wordReplaceTo);
    }

    public String replaceWord(String wordToReplace, String wordReplaceTo) {
        return replaceWord(this.name, wordToReplace, wordReplaceTo);
    }

    // ------------------- Uppercase / Lowercase -------------------
    public static String lowerAll(String str) {
        return str.toLowerCase();
    }

    public void lowerAll() {
        this.name = lowerAll(this.name);
    }

    public static String upperAll(String str) {
        return str.toUpperCase();
    }

    public void upperAll() {
        this.name = upperAll(this.name);
    }

    // ------------------- Count letters -------------------
    public static int countLetter(String str, char letter, boolean matchCase) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (matchCase) {
                if (c == letter) count++;
            } else {
                if (Character.toLowerCase(c) == Character.toLowerCase(letter)) count++;
            }
        }
        return count;
    }

    public int countLetter(char letter) {
        return countLetter(this.name, letter, true);
    }

    // Count capital / small letters
    public static int countLetterType(String str, WhatToCount type) {
        if (type == WhatToCount.ALL_LETTERS) return str.length();
        int count = 0;
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c) && type == WhatToCount.CAPITAL_LETTERS) count++;
            if (Character.isLowerCase(c) && type == WhatToCount.SMALL_LETTERS) count++;
        }
        return count;
    }

    public int countLetterType(WhatToCount type) {
        return countLetterType(this.name, type);
    }

    public int countCapitalLetters() {
        return countLetterType(this.name, WhatToCount.CAPITAL_LETTERS);
    }

    public int countSmallLetters() {
        return countLetterType(this.name, WhatToCount.SMALL_LETTERS);
    }

    // ------------------- Remove punctuations -------------------
    public static String removePunctuations(String str) {
        return str.replaceAll("\\p{Punct}", "");
    }

    public void removePunctuations() {
        this.name = removePunctuations(this.name);
    }

    // ------------------- Upper first letter of each word -------------------
    public static String upperFirstLetterOfEachWord(String str) {
        String[] words = str.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) sb.append(word.substring(1));
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public void upperFirstLetterOfEachWord() {
        this.name = upperFirstLetterOfEachWord(this.name);
    }

    // ------------------- Print each word / first letter -------------------
    public static void printEachWord(String str) {
        String[] words = str.split("\\s+");
        for (String word : words) {
            System.out.println(word);
        }
    }

    public void printEachWord() {
        printEachWord(this.name);
    }

    public static void printEachFirstLetter(String str) {
        String[] words = str.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) System.out.println(word.charAt(0));
        }
    }

    public void printEachFirstLetter() {
        printEachFirstLetter(this.name);
    }
}
