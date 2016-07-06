/*
 * File: EnigmaModel.java
 * ----------------------
 * German Enigma machine from World War II - CS106A Assignment 4
 */


public class EnigmaModel {

    /* Private instance variables */
    private String fastRotor;
    private String mediumRotor;
    private String slowRotor;
    private char fastRotorSetting;
    private char mediumRotorSetting;
    private char slowRotorSetting;

    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /* Private constants */

    /**
     * The German Enigma machines were supplied with a stock of five rotors,
     */
    private static final String STOCK_ROTOR_1 = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
    private static final String STOCK_ROTOR_2 = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
    private static final String STOCK_ROTOR_3 = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
    private static final String STOCK_ROTOR_4 = "ESOVPZJAYQUIRHXLNFTGKDCMWB";
    private static final String STOCK_ROTOR_5 = "VZBRGITYUPSDNHLXAWMJQOFECK";

    /**
     * The Enigma reflector is also a 26-character string that works just like
     * the rotors except for the fact that it stays in one position and never
     * advances.
     */
    private static final String REFLECTOR = "IXUHFEZDAOMTKQJWNSRLCYPBVG";

    /**
     * Creates a new object that models the operation of an Enigma machine.
     */
    public EnigmaModel() {

    }

    /**
     * Sets the rotor order for the Enigma machine.  The rotor order is
     * specified as a three-digit integer giving the numbers of the stock
     * rotors to use.  For example, calling setRotorOrder(513) uses stock
     * rotor 5 as the slow rotor, stock rotor 1 as the medium rotor, and
     * stock rotor 3 as the fast rotor.  This method returns true if the
     * argument specifies a legal rotor order (three digits in the range
     * 1 to 5 with no duplication) and false otherwise.
     *
     * @param order A three-digit integer specifying the rotor order
     * @return A Boolean value indicating whether the rotor order is legal
     */

    private String rotorSetting = "";
    private String rotorOrder = "";


    public boolean setRotorOrder(int order) {
        String ord = Integer.toString(order);
        for (int pos = 0; pos < ord.length(); pos++){
            char ch = ord.charAt(pos);
            if (ch >= '1' && ch <= '5'){
                rotorOrder += ch;
            }
            else return false;
        }
        if (rotorOrder.length() < 3 || rotorOrder.length() > 3){
            return false;
        }

        if (rotorOrder.charAt(0) == rotorOrder.charAt(1) ||
                rotorOrder.charAt(1) == rotorOrder.charAt(2) ||
                rotorOrder.charAt(0) == rotorOrder.charAt(2))
        {
            rotorOrder = "";
            return false;
        }
        return true;
    }

    /**
     * Establishes the rotor setting for the Enigma machine. A legal rotor
     * setting must be a string of three uppercase letters. This method
     * returns true if the argument is a legal setting and false otherwise.
     *
     * @param str The rotor settings
     * @return A Boolean value indicating whether the rotor setting is legal
     */

    public boolean setRotorSetting(String str) {
        str = str.toUpperCase();
        for (int pos = 0; pos < str.length(); pos++) {
            char ch = str.charAt(pos);
            if (ch >= 'A' && ch <= 'Z') {
                rotorSetting += ch;
            }
            else return false;
        }
        if (rotorSetting.length() != 3){
            rotorSetting = "";
            return false;
        }
        return true;
    }

    // Gets the order of the rotors
    private String getRotorOrder(){
        return rotorOrder;
    }

    /**
     * Encrypts a string by passing each letter through the various rotors
     * of the Enigma machine. All letters in the string are converted to
     * uppercase, and the rotors of the Enigma machine are advanced before
     * translating the letter. If a character in the plaintext string is
     * not a letter, the rotors do not advance and the character is simply
     * copied to the output unchanged.
     *
     * @param plaintext The input plaintext string
     * @return The output ciphertext string
     */

    /** Main method of encryption */
    public String encrypt(String plaintext) {
        String result = "";

        // Set each rotor to be used
        setRotor();

        for (int i = 0; i < plaintext.length(); i++){
            char ch = plaintext.charAt(i);
            ch = encryptChar(ch);
            result += ch;
            if (Character.isLetter(ch)){
                advance();
            }
        }
        return result;
    }

    /** This method assigns a stock rotor to each setting */
    private String setRotorPos(int pos){
        String rOrder = getRotorOrder();
        String rotor;

        char ch = rOrder.charAt(pos);
        switch (ch){
            case '1': rotor = STOCK_ROTOR_1; return rotor;
            case '2': rotor = STOCK_ROTOR_2; return rotor;
            case '3': rotor = STOCK_ROTOR_3; return rotor;
            case '4': rotor = STOCK_ROTOR_4; return rotor;
            case '5': rotor = STOCK_ROTOR_5; return rotor;
        }
        return "Failed!";
    }

    /** Sets up the machine with the user selected order, setting and stock rotors */
    private void setRotor(){
        // Assigning stock rotors for each position
        fastRotor   = setRotorPos(2);
        mediumRotor = setRotorPos(1);
        slowRotor   = setRotorPos(0);

        // Getting the corresponding letter for each setting from the user input
        fastRotorSetting   = rotorSetting.charAt(2);
        mediumRotorSetting = rotorSetting.charAt(1);
        slowRotorSetting   = rotorSetting.charAt(0);

        /**
         * Getting the index of each letter to be used in the next part which consists of doing the permutations
         * of the rotors to the user settings before actually encrypting
         */
        int fastIndex   = alphabet.indexOf(fastRotorSetting);
        int mediumIndex = alphabet.indexOf(mediumRotorSetting);
        int slowIndex   = alphabet.indexOf(slowRotorSetting);

        //Setting the fast rotor (Letter and stock setting)
        for (int i = 0; i < fastIndex; i++){
            fastRotor = permutationPhaseOne(fastRotor);
            fastRotor = permutationPhaseTwo(fastRotor);
        }

        //Setting the medium rotor (Letter and stock setting)
        for (int i = 0; i < mediumIndex; i++){
            mediumRotor = permutationPhaseOne(mediumRotor);
            mediumRotor = permutationPhaseTwo(mediumRotor);
        }

        //Setting the slow rotor (Letter and stock setting)
        for (int i = 0; i < slowIndex; i++){
            slowRotor = permutationPhaseOne(slowRotor);
            slowRotor = permutationPhaseTwo(slowRotor);
        }
    }

    /** Encrypting each char. If it's a letter then it's going to be encrypted, if not, it's returned as is. */
    private char encryptChar(char ch){
        if (Character.isLetter(ch)){
            ch = throughRotor(Character.toUpperCase(ch));
            return ch;
        }
        else {
            return ch;
        }
    }

    /** This method runs the character to be encrypted, through the enigma machine rotors */
    private char throughRotor(char ch){
        int index = alphabet.indexOf(ch);

        ch    = fastRotor.charAt(index);

        index = alphabet.indexOf(ch);
        ch    = mediumRotor.charAt(index);

        index = alphabet.indexOf(ch);
        ch    = slowRotor.charAt(index);

        index = alphabet.indexOf(ch);
        ch    = REFLECTOR.charAt(index);

        index = alphabet.indexOf(ch);
        ch    = invertKey(slowRotor).charAt(index);

        index = alphabet.indexOf(ch);
        ch    = invertKey(mediumRotor).charAt(index);

        index = alphabet.indexOf(ch);
        ch    = invertKey(fastRotor).charAt(index);

        return Character.toUpperCase(ch);
    }

    /**
     * Inverts the key for when the letter is send backwards through the rotor
     */
    private String invertKey(String key){
        String result = "";
        key = key.toUpperCase();

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

    /**
     * Removes the first letter of a rotor stock setting and adds it at the end of it.
     * As an example: for stock rotor 1 "EKMFLGDQVZNTOWYHXUSPAIBRCJ" the method will move the E
     * to the end and the result would be "KMFLGDQVZNTOWYHXUSPAIBRCJE"
     */
    private String permutationPhaseOne(String str){
        String result = "";
        char ch = str.charAt(0);
        if (Character.isLetter(ch)){
            str = str.substring(1, str.length());
            result = str + ch;
        }
        return result;
    }

    /**
     * Takes the permutation from phase one and subtracts 1 from each letter.
     * Eg. If the letter is a "D" it will make it a "C" or if the letter is a 'A' it will make it a 'Z'
     */
    private String permutationPhaseTwo(String str){
        String result = "";
        for (int pos = 0; pos < str.length(); pos++){
            char ch = str.charAt(pos);
            if (ch == 'A'){
                ch = 'Z';
            }
            else {
                ch = (char) ('A' + ((ch - 'A' - 1) % 26));
            }
            result += ch;

        }
        return result;
    }

    /**
     * Advances the rotors 1 by one from A to Z.
     * When it reaches Z in any of the 3 rotors, it changes to A and advances the next rotor.
     * For example if the rotor setting is AAZ, next time a letter is encrypted,
     * the fast rotor(the one on the left) is set to A and the medium one is set to B.
     */
    private void advance(){
        fastRotor = permutationPhaseOne(fastRotor);
        fastRotor = permutationPhaseTwo(fastRotor);
        fastRotorSetting += 1;

        if (fastRotorSetting == '['){
            fastRotorSetting = 'A';
            mediumRotorSetting += 1;
            mediumRotor = permutationPhaseOne(mediumRotor);
            mediumRotor = permutationPhaseTwo(mediumRotor);
        }

        if (mediumRotorSetting == '['){
            mediumRotorSetting = 'A';
            slowRotorSetting += 1;
            slowRotor = permutationPhaseOne(slowRotor);
            slowRotor = permutationPhaseTwo(slowRotor);
        }
    }
}