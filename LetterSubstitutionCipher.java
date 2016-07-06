/*
 * File: LetterSubstitutionCipher.java
 * -----------------------------------
 * This program translates a line of text using a letter-substitution cipher.
 * This code is equivalent to what we wrote in lecture.  All you need to do
 * is add code to check that the key is legal, which means that it is
 * 26 letters long and contains every uppercase letter.
 *
 * // Remember to correct this comment when you write the actual code
 */

import acm.program.*;

public class LetterSubstitutionCipher extends ConsoleProgram {

    public void run() {
        println("Letter-substitution cipher.");
        while (true){
            String key = readLine("Enter 26-letter key: ").toUpperCase();
            if (validKey(key)){
                String plaintext = readLine("Plaintext:  ");
//                String permut = permutationPhaseTwo(plaintext);
                String ciphertext = encrypt(plaintext, key);
                println("Ciphertext: " + ciphertext);
//                println("Ciphertext: " + permut);
                break;
            }
        }
    }

//    private String permutation(String str){
//        String result = "";
//            char ch = str.charAt(0);
//            if (Character.isLetter(ch)){
//                str = str.substring(1, str.length());
//                result = str + ch;
//            }
//        return result;
//    }

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
 * Encrypts a string according to the key.  All letters in the string
 * are converted to uppercase.  Any character that is not a letter is
 * copied to the output unchanged.
 *
 * @param str The string to be encrypted
 * @param key The encryption key
 * @return The encrypted string
 */

    private String encrypt(String str, String key) {
        String result = "";
        str = str.toUpperCase();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isLetter(ch)) {
               ch = key.charAt(ch - 'A');
            }
            result += ch;
        }
        return result;
    }




}
