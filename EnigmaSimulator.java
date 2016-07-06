/*
 * File: EnigmaSimulator.java
 * --------------------------
 * Enigma simulator that uses the console to enter the rotor order, rotor settings, and plaintext.
 *
 */

import acm.program.*;

public class EnigmaSimulator extends ConsoleProgram {

    public void run() {
        while (true){
            EnigmaModel enigma = new EnigmaModel();
            int rotorOrder = readInt("Enter rotor order: ");
            if (enigma.setRotorOrder(rotorOrder)) {
                while (true){
                    String rotorSetting = readLine("Enter rotor setting: ");
                    if (enigma.setRotorSetting(rotorSetting)) {
                        String plaintext = readLine("Enter a plaintext line: ");
                        String encrypted = enigma.encrypt(plaintext);
                        String decrypted = enigma.encrypt(encrypted);
                        println("Encrypted: " + encrypted);
                        println("Decrypted: " + decrypted);
                        break;
                    }
                    else {
                        println("Rotor setting is invalid. Please enter three uppercase letters.");
                    }
                }
                break;
            }
            else {
                println("Rotor order is invalid. Please enter three unique digits between 1 and 5.");
            }
        }
    }
}