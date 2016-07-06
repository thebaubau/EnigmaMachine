/*
 * File: InvertKey.java
 * --------------------
 * This is the starter file for a program that tests the invertKey method.
 *
 * // Remember to correct this comment when you write the actual program
 */

import acm.program.*;

public class InvertKey extends ConsoleProgram {

    public void run() {
        while (true) {
            String key = readLine("Enter a 26-letter key: ").toUpperCase();
            if (validKey(key)){
                String invertKey = invertKey(key);
                String originalKey = invertKey(invertKey);
                println("Key:        : " + key);
                println("Inverted Key: " + invertKey);
                println("Original Key: " + originalKey);
                break;
            }
        }
    }

    public boolean validKey(String key) {
        int maxLength = key.length();
        int count = 0;
        if (maxLength != 26){
            println("This key is illegal.");
            return false;
        }
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            for (int position = 0; position < maxLength; position++) {
                char keyChar = key.charAt(position);
                if (ch == keyChar) {
                    count++;
                    break;
                }
            }
        }
        if (count != maxLength) {
            println("This key is illegal.");
            return false;
        }
        return true;
    }

/**
 * Inverts a key for a letter-substitution cipher, where a key is a
 * 26-letter string that shows how each letter in the alphabet is
 * translated into the encrypted message.  For example, if the key is
 * "LZDRXPEAJYBQWFVIHCTGNOMKSU", that means that 'A' (the first letter
 * in the alphabet) translates to 'L' (the first letter in the key),
 * 'B' translates to 'Z', 'C' translates to 'D', and so on.  The inverse
 * of a key is a 26-letter that translates in the opposite direction.
 * As an example, the inverse of "LZDRXPEAJYBQWFVIHCTGNOMKSU" is
 * "HKRCGNTQPIXAWUVFLDYSZOMEJB".
 *
 * @param key The original key
 * @return The key that translates in the opposite direction
 */

    private String invertKey(String key){
        String result = "";
        key = key.toUpperCase();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            //Get the index of the char in the KEY
            int index = key.indexOf(ch);

            //Get the char at the same index as the KEY from the ALPHABET
            char alphabetChar = alphabet.charAt(index);

            //Add the char found at the index of he alphabet
            result += alphabetChar;
        }
        return result;
    }
}
